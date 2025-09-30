package com.santiago.pantallastrabajodegrado // Revisa tu nombre de paquete

import android.content.Context
import android.util.Log // Importar Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.santiago.pantallastrabajodegrado.databinding.ItemPreguntaBinding

// Clases de datos
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

class Encuesta(
    private val context: Context,
    private val contenedorPreguntasLayout: LinearLayout,
    private val btnEnviar: Button
) {

    private val listaDePreguntas = mutableListOf<Pregunta>()
    private val respuestasSeleccionadas = mutableMapOf<Int, Int>()

    init {
        Log.d("EncuestaLog", "Encuesta.init() llamado")
        cargarPreguntasDeEjemplo()
        btnEnviar.setOnClickListener {
            procesarRespuestas()
        }
        Log.d("EncuestaLog", "Encuesta.init() finalizado. Listener de btnEnviar configurado.")
    }

    private fun cargarPreguntasDeEjemplo() {
        Log.d("EncuestaLog", "cargarPreguntasDeEjemplo() INICIO")
        listaDePreguntas.clear() // Asegurar que está vacía si se llama múltiples veces (aunque aquí solo en init)

        listaDePreguntas.add(
            Pregunta(
                idPregunta = 1,
                textoPregunta = "¿Cuál es tu pasatiempo favorito en tu tiempo libre?",
                opciones = listOf(
                    OpcionRespuesta("Leer libros o artículos", 101),
                    OpcionRespuesta("Practicar deportes o hacer ejercicio", 102),
                    OpcionRespuesta("Ver películas o series", 103),
                    OpcionRespuesta("Jugar videojuegos", 104)
                )
            )
        )
        Log.d("EncuestaLog", "Pregunta 1 añadida. Total: ${listaDePreguntas.size}")

        listaDePreguntas.add(
            Pregunta(
                idPregunta = 2,
                textoPregunta = "¿Qué tipo de ambiente de trabajo prefieres?",
                opciones = listOf(
                    OpcionRespuesta("Un ambiente colaborativo y en equipo", 201),
                    OpcionRespuesta("Un ambiente independiente donde pueda concentrarme solo", 202),
                    OpcionRespuesta("Un ambiente dinámico y con mucha interacción social", 203),
                    OpcionRespuesta("Un ambiente tranquilo y estructurado", 204)
                )
            )
        )
        Log.d("EncuestaLog", "Pregunta 2 añadida. Total: ${listaDePreguntas.size}")

        listaDePreguntas.add(
            Pregunta(
                idPregunta = 3,
                textoPregunta = "¿Qué te motiva más en un proyecto?",
                opciones = listOf(
                    OpcionRespuesta("Resolver problemas complejos y encontrar soluciones innovadoras.", 301),
                    OpcionRespuesta("Ayudar a otras personas y ver el impacto positivo de mi trabajo.", 302),
                    OpcionRespuesta("La oportunidad de aprender cosas nuevas y desarrollar mis habilidades.", 303),
                    OpcionRespuesta("Alcanzar metas y obtener reconocimiento por mis logros.", 304)
                )
            )
        )
        Log.d("EncuestaLog", "Pregunta 3 añadida. Total: ${listaDePreguntas.size}")
        Log.d("EncuestaLog", "cargarPreguntasDeEjemplo() FIN. Total preguntas: ${listaDePreguntas.size}")
    }

    fun mostrarEncuestaEnUI() {
        Log.d("EncuestaLog", "Encuesta.mostrarEncuestaEnUI() llamado. Total preguntas al renderizar: ${listaDePreguntas.size}")
        if (listaDePreguntas.isEmpty()) {
            Log.e("EncuestaLog", "¡Lista de preguntas está VACÍA al intentar renderizar!")
            Toast.makeText(context, "No hay preguntas para mostrar.", Toast.LENGTH_LONG).show() // Feedback al usuario
            return
        }
        renderizarPreguntas()
    }

    private fun renderizarPreguntas() {
        Log.d("EncuestaLog", "renderizarPreguntas() INICIO. Hijos actuales del contenedor: ${contenedorPreguntasLayout.childCount}")
        val inflater = LayoutInflater.from(context)

        // Estrategia para manejar el botón "Enviar" que está DENTRO del contenedor de preguntas
        // y debe permanecer al final.
        // 1. Guarda una referencia al botón si existe.
        // 2. Limpia TODAS las vistas del contenedor.
        // 3. Añade las preguntas.
        // 4. Vuelve a añadir el botón al final.

        var vistaBotonEnviar: View? = null
        // Buscar el botón por su ID dentro del contenedor
        for (i in 0 until contenedorPreguntasLayout.childCount) {
            val child = contenedorPreguntasLayout.getChildAt(i)
            if (child.id == R.id.btnEnviar) { // Asumiendo que el botón tiene el ID btnEnviar
                vistaBotonEnviar = child
                Log.d("EncuestaLog", "Botón Enviar encontrado en el contenedor.")
                break
            }
        }
        // Si se encontró, quitarlo temporalmente para poder limpiar y re-añadir
        if (vistaBotonEnviar != null) {
            contenedorPreguntasLayout.removeView(vistaBotonEnviar)
            Log.d("EncuestaLog", "Botón Enviar quitado temporalmente.")
        }

        // Limpiar todas las vistas que podrían ser preguntas anteriores
        contenedorPreguntasLayout.removeAllViews()
        Log.d("EncuestaLog", "Contenedor limpiado. Hijos: ${contenedorPreguntasLayout.childCount}")


        for (pregunta in listaDePreguntas) {
            Log.d("EncuestaLog", "Renderizando pregunta ID: ${pregunta.idPregunta} - ${pregunta.textoPregunta.take(30)}...")
            val itemBinding = ItemPreguntaBinding.inflate(inflater, contenedorPreguntasLayout, false)
            itemBinding.tvPreguntaTexto.text = pregunta.textoPregunta
            itemBinding.radioGroupOpciones.id = View.generateViewId()

            for (opcion in pregunta.opciones) {
                val radioButton = RadioButton(context)
                radioButton.text = opcion.texto
                val radioButtonId = (pregunta.idPregunta * 10000) + opcion.idOpcion
                radioButton.id = radioButtonId

                val layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                if (pregunta.opciones.indexOf(opcion) > 0) {
                    val marginTopDp = 8
                    layoutParams.topMargin = (marginTopDp * context.resources.displayMetrics.density).toInt()
                }
                radioButton.layoutParams = layoutParams
                radioButton.minHeight = (48 * context.resources.displayMetrics.density).toInt()
                radioButton.gravity = Gravity.CENTER_VERTICAL
                radioButton.setBackgroundResource(R.drawable.answer_background_selector)
                radioButton.setButtonDrawable(R.drawable.radio_selector)
                val paddingDp = 12
                val paddingPx = (paddingDp * context.resources.displayMetrics.density).toInt()
                radioButton.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
                radioButton.setTextColor(ContextCompat.getColor(context, android.R.color.black))

                itemBinding.radioGroupOpciones.addView(radioButton)

                if (respuestasSeleccionadas[pregunta.idPregunta] == radioButtonId) {
                    radioButton.isChecked = true
                }
            }

            itemBinding.radioGroupOpciones.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId != -1) {
                    respuestasSeleccionadas[pregunta.idPregunta] = checkedId
                } else {
                    respuestasSeleccionadas.remove(pregunta.idPregunta)
                }
            }
            contenedorPreguntasLayout.addView(itemBinding.root)
            Log.d("EncuestaLog", "Vista de pregunta '${pregunta.textoPregunta.take(15)}...' añadida. Hijos del contenedor: ${contenedorPreguntasLayout.childCount}")
        }

        // Volver a añadir el botón "Enviar" al final si existía
        if (vistaBotonEnviar != null) {
            contenedorPreguntasLayout.addView(vistaBotonEnviar)
            Log.d("EncuestaLog", "Botón Enviar re-añadido al final. Hijos: ${contenedorPreguntasLayout.childCount}")
        } else {
            Log.w("EncuestaLog", "El botón Enviar no se encontró originalmente en el contenedor para re-añadirlo.")
        }
        Log.d("EncuestaLog", "renderizarPreguntas() FIN. Hijos totales del contenedor: ${contenedorPreguntasLayout.childCount}")
    }

    private fun procesarRespuestas() {
        Log.d("EncuestaLog", "procesarRespuestas() llamado. Respuestas seleccionadas: ${respuestasSeleccionadas.size}, Total preguntas: ${listaDePreguntas.size}")
        if (respuestasSeleccionadas.size < listaDePreguntas.size) {
            Toast.makeText(context, "Por favor, responde todas las preguntas.", Toast.LENGTH_SHORT).show()
            return
        }

        val sb = StringBuilder()
        sb.append("Respuestas Seleccionadas (Encuesta Lógica):\n")
        for ((idPregunta, idOpcionSeleccionada) in respuestasSeleccionadas) {
            val pregunta = listaDePreguntas.find { it.idPregunta == idPregunta }
            val opcionSeleccionada = pregunta?.opciones?.find { ((pregunta.idPregunta * 10000) + it.idOpcion) == idOpcionSeleccionada }
            sb.append("Pregunta ID ${pregunta?.idPregunta}: ${pregunta?.textoPregunta?.take(30)}...\n")
            sb.append("Opción: ${opcionSeleccionada?.texto?.take(30)}...\n\n")
        }
        Log.d("EncuestaLog", "Respuestas procesadas:\n$sb")
        Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show() // Muestra un Toast largo con las respuestas
    }
}
