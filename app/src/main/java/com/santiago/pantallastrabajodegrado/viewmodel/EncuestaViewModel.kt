package com.santiago.pantallastrabajodegrado.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santiago.pantallastrabajodegrado.data.ApiPregunta
import com.santiago.pantallastrabajodegrado.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface EncuestaUiState {
    object Loading : EncuestaUiState
    data class Success(val preguntas: List<ApiPregunta>) : EncuestaUiState
    data class Error(val message: String) : EncuestaUiState
}

class EncuestaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<EncuestaUiState>(EncuestaUiState.Loading)
    val uiState: StateFlow<EncuestaUiState> = _uiState

    private val _seleccion = MutableStateFlow<Map<Int, String>>(emptyMap())
    val seleccion: StateFlow<Map<Int, String>> = _seleccion

    fun load() {
        viewModelScope.launch {
            _uiState.value = EncuestaUiState.Loading
            try {
                val preguntas = RetrofitClient.api.getPreguntas()
                _uiState.value = EncuestaUiState.Success(preguntas)
            } catch (e: Exception) {
                _uiState.value = EncuestaUiState.Error("No se pudieron cargar las preguntas. Revisa la conexión a internet.")
            }
        }
    }

    fun seleccionar(idPregunta: Int, idOpcion: String) {
        _seleccion.value = _seleccion.value.toMutableMap().apply {
            put(idPregunta, idOpcion)
        }
    }

    fun todoRespondido(): Boolean {
        val preguntas = (uiState.value as? EncuestaUiState.Success)?.preguntas ?: return false
        return _seleccion.value.size == preguntas.size
    }

    fun respuestasDebug(): String {
        val preguntas = (uiState.value as? EncuestaUiState.Success)?.preguntas.orEmpty()
        val map = _seleccion.value
        val sb = StringBuilder("Respuestas Seleccionadas:\n")
        preguntas.forEach { p ->
            val opId = map[p.id]
            val op = p.opciones.firstOrNull { it.id == opId }
            sb.append("P${p.id}: ${p.texto.take(30)}...\n")
            sb.append("-> ${op?.texto?.take(30)}...\n\n")
        }
        return sb.toString()
    }
}
