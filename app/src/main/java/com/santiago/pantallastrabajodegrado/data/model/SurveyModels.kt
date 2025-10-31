package com.santiago.pantallastrabajodegrado.data.model

// Clases de datos de la encuesta (puedes importarlas desde Compose o desde la lógica basada en Views)
data class OpcionRespuesta(
    val texto: String,
    val idOpcion: Int,
    var esSeleccionada: Boolean = false
)

data class Pregunta(
    val idPregunta: Int,
    val textoPregunta: String,
    val opciones: List<OpcionRespuesta>
)
