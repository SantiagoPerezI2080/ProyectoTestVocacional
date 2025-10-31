package com.santiago.pantallastrabajodegrado.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class TestRepository(private val context: Context) {

    private val gson = Gson()

    fun loadDiadasFromAssets(filename: String = "diadas.json"): List<Diada> {
        context.assets.open(filename).use { stream ->
            InputStreamReader(stream).use { reader ->
                val type = object : TypeToken<List<Diada>>() {}.type
                return gson.fromJson(reader, type) ?: emptyList()
            }
        }
    }

    // devuelve Map<String,String> donde la key es número de ítem ("1","2",..)
    fun loadMappingFromAssets(filename: String = "mapping_item_area.json"): Map<String, String> {
        context.assets.open(filename).use { stream ->
            InputStreamReader(stream).use { reader ->
                val type = object : TypeToken<Map<String, String>>() {}.type
                return gson.fromJson(reader, type) ?: emptyMap()
            }
        }
    }
}
