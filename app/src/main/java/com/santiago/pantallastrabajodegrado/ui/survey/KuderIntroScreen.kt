@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.santiago.pantallastrabajodegrado.ui.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.text.style.TextAlign

@Composable
fun KuderIntroScreen(navController: NavController) {
    ScreenWithTopBar(
        showLogo = false,
        title = "TEST VOCACIONAL",
        navBack = { navController.popBackStack() },
    ) { innerPadding ->

        // 🔹 Gradiente de fondo general
        val gradBackground = Brush.verticalGradient(
            listOf(Color(0xFF054594), Color(0xFF02152E))
        )

        // 🔹 Gradiente interno del Card
        val gradCard = Brush.verticalGradient(
            listOf(Color(0xFF059DDE), Color(0xFF0571B9), Color(0xFF059DDE))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(gradBackground)
        ) {
            // 1️⃣ Contenido principal scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                // 🔹 Card con gradiente
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(gradCard, shape = RoundedCornerShape(12.dp))
                            .padding(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Este test está diseñado para ser sencillo y directo.",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Te haremos pares de actividades. Elige la actividad que más te guste en cada par. No hay respuestas buenas ni malas; responde con sinceridad.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Tómate tu tiempo y responde con honestidad. Cuando termines tendrás una recomendación de carreras.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFBFD7E6)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "El test tiene 45 pares de preguntas. ¡Responde con calma y sinceridad!",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFBFD7E6),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2️⃣ Botón fijo inferior (idéntico comportamiento al de 'Enviar respuestas')
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 96.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("kuder_test") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, // dejamos transparente para mostrar el gradiente
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    // Fondo con gradiente
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFFFC225), Color(0xFFE27131))
                                ),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SIGUIENTE",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
