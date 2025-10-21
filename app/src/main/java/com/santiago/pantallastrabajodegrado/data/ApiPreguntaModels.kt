package com.santiago.pantallastrabajodegrado.data

import com.google.gson.annotations.SerializedName

data class ApiPregunta(
    @SerializedName("id") val id: Int,
    @SerializedName("texto") val texto: String,
    @SerializedName("opciones") val opciones: List<ApiOpcion>
)

data class ApiOpcion(
    @SerializedName("id") val id: String,
    @SerializedName("texto") val texto: String,
    @SerializedName("carrera_afinidad") val carreraAfinidad: String
)
