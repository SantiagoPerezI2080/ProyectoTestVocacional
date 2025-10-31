package com.santiago.pantallastrabajodegrado.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.santiago.pantallastrabajodegrado.data.Diada
import com.santiago.pantallastrabajodegrado.data.TestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KuderViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = TestRepository(app.applicationContext)
    private val prefs = app.getSharedPreferences("kuder_prefs", Context.MODE_PRIVATE)

    private val _diadas = MutableStateFlow<List<Diada>>(emptyList())
    val diadas: StateFlow<List<Diada>> = _diadas

    // diadaId -> itemNumber seleccionado
    private val _selecciones = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selecciones: StateFlow<Map<Int, Int>> = _selecciones

    // mapping itemNumber(int) -> areaCode(String)
    private var mappingItemArea: Map<Int, String> = emptyMap()

    init {
        viewModelScope.launch {
            try {
                val d = repo.loadDiadasFromAssets()
                _diadas.value = d
                Log.d("KuderViewModel", "Diadas cargadas: ${d.size}")

                // cargar mapping y convertir keys a Int
                val raw = repo.loadMappingFromAssets()
                mappingItemArea = raw.mapNotNull { (k, v) ->
                    k.toIntOrNull()?.let { it to v }
                }.toMap()
                Log.d("KuderViewModel", "MappingItemArea cargado: ${mappingItemArea.size} entradas")

                loadSavedAnswers()
            } catch (e: Exception) {
                Log.e("KuderViewModel", "Error init: ${e.message}", e)
            }
        }
    }

    fun seleccionar(diadaId: Int, itemSeleccionado: Int) {
        _selecciones.value = _selecciones.value.toMutableMap().apply {
            put(diadaId, itemSeleccionado)
        }
        Log.d("KuderViewModel", "Seleccionar -> diada=$diadaId item=$itemSeleccionado totalSeleccionadas=${_selecciones.value.size}")
    }

    fun guardarRespuestasLocal() {
        viewModelScope.launch {
            try {
                val gson = com.google.gson.Gson()
                val json = gson.toJson(_selecciones.value)
                prefs.edit().putString("last_answers", json).apply()
                Log.d("KuderViewModel", "guardarRespuestasLocal -> persistidas ${_selecciones.value.size} respuestas")
            } catch (e: Exception) {
                Log.e("KuderViewModel", "Error guardando respuestas: ${e.message}", e)
            }
        }
    }

    private fun loadSavedAnswers() {
        viewModelScope.launch {
            try {
                val gson = com.google.gson.Gson()
                val raw = prefs.getString("last_answers", null) ?: return@launch
                val type = com.google.gson.reflect.TypeToken.getParameterized(
                    Map::class.java,
                    Int::class.javaObjectType,
                    Int::class.javaObjectType
                ).type
                @Suppress("UNCHECKED_CAST")
                val map: Map<Int, Int> = gson.fromJson(raw, type) as? Map<Int, Int> ?: return@launch
                _selecciones.value = map
                Log.d("KuderViewModel", "loadSavedAnswers -> cargadas ${map.size} respuestas desde prefs")
            } catch (e: Exception) {
                Log.e("KuderViewModel", "Error loadSavedAnswers: ${e.message}", e)
            }
        }
    }

    /** devuelve mapa area -> count */
    fun calcularConteoPorArea(): Map<String, Int> {
        val counts = mutableMapOf<String, Int>()
        val selections = _selecciones.value
        if (mappingItemArea.isEmpty()) {
            Log.w("KuderViewModel", "calcularConteoPorArea -> mappingItemArea está vacío")
        }
        for (item in selections.values) {
            val area = mappingItemArea[item] ?: "OTRA"
            counts[area] = counts.getOrDefault(area, 0) + 1
        }
        Log.d("KuderViewModel", "calcularConteoPorArea -> counts: $counts (from ${selections.size} selections)")
        return counts
    }
}
