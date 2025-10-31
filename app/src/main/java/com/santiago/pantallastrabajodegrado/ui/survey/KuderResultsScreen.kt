@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.santiago.pantallastrabajodegrado.ui.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santiago.pantallastrabajodegrado.viewmodel.KuderViewModel
import kotlin.math.roundToInt
import android.util.Log
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun KuderResultsScreen(
    onBack: () -> Unit = {},
    vm: KuderViewModel = viewModel()
) {
    // Debug log con las selecciones
    LaunchedEffect(Unit) {
        Log.d("KuderResultsScreen", "Selecciones en results (vm): ${vm.selecciones.value}")
    }

    val counts = vm.calcularConteoPorArea()
    val total = counts.values.sum().takeIf { it > 0 } ?: 1

    // Mapeo áreas -> carreras (ajústalo si quieres)
    val areaToCarrera = mapOf(
        "EXTERIOR" to listOf(
            "INGENIERÍA AMBIENTAL Y DE SANEAMIENTO",
            "INGENIERÍA CIVIL",
            "ENTRENAMIENTO DEPORTIVO"
        ),
        "MECANICA" to listOf(
            "INGENIERÍA ELECTRÓNICA",
            "INGENIERÍA ENERGÉTICA"
        ),
        "CALCULO" to listOf(
            "MATEMÁTICAS APLICADAS EN CIENCIA DE DATOS",
            "INGENIERÍA DE SOFTWARE Y COMPUTACIÓN",
            "INGENIERÍA AMBIENTAL Y DE SANEAMIENTO",
            "INGENIERÍA CIVIL"
        ),
        "CIENTIFICA" to listOf(
            "INGENIERÍA DE SOFTWARE Y COMPUTACIÓN",
            "INGENIERÍA AMBIENTAL Y DE SANEAMIENTO"
        ),
        "PERSUASIVA" to listOf(
            "ADMINISTRACIÓN DE EMPRESAS",
            "FINANZAS Y NEGOCIOS INTERNACIONALES",
            "GOBIERNO Y RELACIONES INTERNACIONALES",
            "DERECHO"
        ),
        "ARTISTICA" to listOf(
            "INGENIERÍA DE SOFTWARE Y COMPUTACIÓN",
            "INGENIERÍA ELECTRÓNICA"
        ),
        "LITERARIA" to listOf(
            "LICENCIATURA EN EDUCACIÓN INFANTIL",
            "DERECHO"
        ),
        "MUSICAL" to listOf(
            "ENTRENAMIENTO DEPORTIVO"
        ),
        "SERVICIO" to listOf(
            "LICENCIATURA EN EDUCACIÓN INFANTIL",
            "ENTRENAMIENTO DEPORTIVO",
            "DERECHO"
        ),
        "OFICINA" to listOf(
            "CONTADURÍA PÚBLICA",
            "ADMINISTRACIÓN DE EMPRESAS",
            "DERECHO"
        )
    )

    // score por carrera
    val careerScores = mutableMapOf<String, Int>()
    counts.forEach { (area, cnt) ->
        areaToCarrera[area]?.forEach { carrera ->
            careerScores[carrera] = careerScores.getOrDefault(carrera, 0) + cnt
        }
    }

    // porcentajes (sobre el total de respuestas)
    val careerPercents = careerScores.mapValues { (_, v) ->
        (v.toDouble() / total.toDouble()) * 100.0
    }

    // Top 3 y normalización para que sumen 100% entre ellas
    val ordered = careerPercents.toList().sortedByDescending { it.second }
    val top3 = ordered.take(3)
    val totalTop = top3.sumOf { it.second }.takeIf { it > 0 } ?: 1.0
    val normalizedTop3 = top3.associate { it.first to (it.second / totalTop * 100.0) }

    ScreenWithTopBar(
        showLogo = false,
        title = "TEST VOCACIONAL",
        navBack = { onBack() },
        //onNotification = { /* nada por ahora */ }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "¡Has completado el test! \n¡Felicidades! Según tus respuestas, te recomendamos los siguientes programas:",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                color = Color.White
            )
            Spacer(Modifier.height(18.dp))

            if (normalizedTop3.isEmpty()) {
                Text(
                    "Aún no hay resultados (responde el test o guarda las respuestas localmente).",
                    color = Color(0xFFBFD7E6)
                )
            } else {
                normalizedTop3.entries.forEachIndexed { idx, (career, percent) ->
                    Text(
                        "${idx + 1}. $career",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(22.dp)
                            .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                    ) {
                        val fraction = (percent / 100.0).coerceIn(0.0, 1.0).toFloat()
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(fraction)
                                .background(Color(0xFFFFC225), RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${percent.roundToInt()}%",
                        color = Color(0xFFBFD7E6),
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
