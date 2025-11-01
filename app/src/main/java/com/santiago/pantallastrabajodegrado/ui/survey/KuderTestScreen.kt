@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.santiago.pantallastrabajodegrado.ui.survey

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santiago.pantallastrabajodegrado.data.Diada
import com.santiago.pantallastrabajodegrado.viewmodel.KuderViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar

@Composable
fun KuderTestScreen(navController: NavController, vm: KuderViewModel? = null) {
    val activity = LocalContext.current as ComponentActivity
    val viewModelInstance: KuderViewModel = vm ?: viewModel(activity)
    val diadas by viewModelInstance.diadas.collectAsState()
    val selecciones by viewModelInstance.selecciones.collectAsState()

    val total = diadas.size
    val respondidas = selecciones.size
    val allAnswered = total > 0 && respondidas == total

    // 🎨 Fondo general (vertical gradient)
    val screenGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF054594), Color(0xFF02152E))
    )

    ScreenWithTopBar(
        showLogo = false,
        title = "TEST VOCACIONAL",
        navBack = { navController.popBackStack() },
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(screenGradient)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header superior
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Respondidas: $respondidas / $total", color = Color.White)
                    Text(
                        text = if (allAnswered) "Listo para enviar" else "Completa todas las respuestas",
                        color = Color(0xFFBFD7E6),
                        fontSize = 12.sp
                    )
                }

                // Contenido scrollable
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 12.dp)
                ) {
                    items(diadas) { d ->
                        DiadaCard(
                            diada = d,
                            selected = selecciones[d.id],
                            onSelect = { item -> viewModelInstance.seleccionar(d.id, item) }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(6.dp)) }

                    // ======= BOTÓN FINAL =======
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(bottom = 96.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val buttonGradient = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFFC225), Color(0xFFE27131))
                            )

                            Button(
                                onClick = {
                                    viewModelInstance.guardarRespuestasLocal()
                                    Log.d(
                                        "KuderTestScreen",
                                        "Selecciones antes de navegar: ${viewModelInstance.selecciones.value}"
                                    )
                                    navController.navigate("kuder_results")
                                },
                                enabled = allAnswered,
                                shape = RoundedCornerShape(28.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = if (allAnswered) Color.Black else Color.White,
                                    disabledContainerColor = Color(0xFF3A4658),
                                    disabledContentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .padding(horizontal = 16.dp)
                                    .background(
                                        if (allAnswered) buttonGradient else Brush.linearGradient(
                                            listOf(Color(0xFF3A4658), Color(0xFF3A4658))
                                        ),
                                        shape = RoundedCornerShape(28.dp)
                                    )
                            ) {
                                Text(
                                    if (allAnswered) "Enviar y ver resultados"
                                    else "Completa todas las respuestas",
                                    color = if (allAnswered) Color.White else Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ===================== DIADA CARD =====================
@Composable
fun DiadaCard(diada: Diada, selected: Int?, onSelect: (Int) -> Unit) {
    val cardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF059DDE),
            Color(0xFF0571B9),
            Color(0xFF059DDE)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardGradient, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(cardGradient, shape = RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Diada ${diada.id}",
                    color = Color(0xFFFFC225),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "(${diada.leftItem} • ${diada.rightItem})",
                    color = Color(0xB3FFFFFF),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = diada.leftText, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    val isSelectedLeft = selected == diada.leftItem
                    Button(
                        onClick = { onSelect(diada.leftItem) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelectedLeft) Color(0xFFFFC225) else Color.White,
                            contentColor = if (isSelectedLeft) Color.Black else Color(0xFF0B2447)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isSelectedLeft) "Seleccionado" else "Elegir")
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = diada.rightText, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    val isSelectedRight = selected == diada.rightItem
                    Button(
                        onClick = { onSelect(diada.rightItem) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelectedRight) Color(0xFFFFC225) else Color.White,
                            contentColor = if (isSelectedRight) Color.Black else Color(0xFF0B2447)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isSelectedRight) "Seleccionado" else "Elegir")
                    }
                }
            }
        }
    }
}
