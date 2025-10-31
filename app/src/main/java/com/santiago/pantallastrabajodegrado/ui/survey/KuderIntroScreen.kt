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
        //onNotification = { /* acciones si las hay */ }
    ) { innerPadding ->

        // Gradiente de fondo
        val grad = Brush.verticalGradient(listOf(Color(0xFF071428), Color(0xFF0A2A50)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // importante: respeta la top bar
                .background(grad)
        ) {
            // 1) Contenido scrollable: ocupa todo el espacio disponible excepto el requerido por el botón
            Column(
                modifier = Modifier
                    .weight(1f) // ocupa espacio disponible (deja al botón su propio espacio)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3A57))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Este test está diseñado para ser sencillo y directo.",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFFFC225),
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

                // opcional: más contenido explicativo
                Spacer(modifier = Modifier.height(12.dp))

                // agrega espacio al final del scroll para que no quede pegado a la parte inferior interna
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2) Botón fijo en la parte inferior (fuera del scroll),
            //    con navigationBarsPadding + padding(bottom = 96.dp) para estar seguro de que quede visible
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding() // evita solaparse con la nav bar del sistema o la botonera
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    // Nota: si tu BottomNavigation personalizada es más alta aumenta el bottom extra aquí
                    .padding(bottom = 96.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate("kuder_test") },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC225),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("SIGUIENTE", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
