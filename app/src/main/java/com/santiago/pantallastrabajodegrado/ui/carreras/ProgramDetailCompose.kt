@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.santiago.pantallastrabajodegrado.ui.carreras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.santiago.pantallastrabajodegrado.data.model.Program

@Composable
fun ProgramDetailScreenCompose(program: Program, navController: NavController) {
    // Scaffold con TopAppBar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        program.titulo,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = com.santiago.pantallastrabajodegrado.R.drawable.icon_backofi),
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* notificaciones */ }) {
                        Icon(
                            painter = painterResource(id = com.santiago.pantallastrabajodegrado.R.drawable.icon_notificacion),
                            contentDescription = "noti",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF073552),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF071428)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row superior: imagen + título grande a la derecha
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = program.imagenRes),
                    contentDescription = program.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = program.titulo,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = program.duracion,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFBFD7E6)
                    )
                }
            }

            // Tarjeta amarilla con descripción (puedes adaptar el texto)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFDE6B)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Descripción del programa: aquí puedes poner la descripción real del programa.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF102028)
                    )
                }
            }

            // Cuadrícula con información
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    InfoCard(title = "DURACIÓN", value = program.duracion, bg = Color(0xFFFFC1A1))
                    InfoCard(title = "CRÉDITOS", value = "${program.creditos}", bg = Color(0xFFD2B8FF))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    InfoCard(title = "TÍTULO", value = program.titulo, bg = Color(0xFF9EE8FF))
                    InfoCard(title = "TIPO", value = "Pregrado", bg = Color(0xFF80E6B8))
                }
            }

            // Asignaturas destacadas (ejemplo)
            Text(text = "Asignaturas destacadas", style = MaterialTheme.typography.titleMedium, color = Color.White)
            val sampleModules = listOf("Programación I","Estructuras de Datos","Bases de Datos")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sampleModules) { module -> ModuleCard(moduleName = module) }
            }

            // Objetivos (ejemplo)
            Text(text = "Objetivos del programa", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SmallBullet(text = "Formar profesionales competentes en desarrollo de software.")
                SmallBullet(text = "Fomentar pensamiento crítico y ético.")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ModuleCard(moduleName: String) { /* reused from your original code */
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(110.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3A57))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = moduleName, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(text = "Ver más", style = MaterialTheme.typography.labelSmall, color = Color(0xFF9ED8FF))
        }
    }
}

@Composable
private fun SmallBullet(text: String) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = Color(0xFF40E0D0), shape = RoundedCornerShape(3.dp))
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = Color(0xFFBFD7E6))
    }
}

@Composable
private fun RowScope.InfoCard(title: String, value: String, bg: Color) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(110.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = Color(0xFF102028))
            Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color(0xFF102028))
        }
    }
}
