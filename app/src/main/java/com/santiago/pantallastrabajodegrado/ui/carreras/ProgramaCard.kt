package com.santiago.pantallastrabajodegrado.ui.carreras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.santiago.pantallastrabajodegrado.data.model.Program

@Composable
fun ProgramaCard(
    program: Program,
    modifier: Modifier = Modifier,
    onVerMas: () -> Unit = {}
) {
    val radius = 16.dp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(radius),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF123258)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = program.imagenRes),
                contentDescription = program.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = radius, bottomStart = radius,
                            topEnd = 0.dp, bottomEnd = 0.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFF0B75C8),
                        shape = RoundedCornerShape(
                            topStart = 0.dp, bottomStart = 0.dp,
                            topEnd = radius, bottomEnd = radius
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                ) {
                    Text(
                        text = program.titulo,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(text = "Duración: ${program.duracion}", color = Color(0xE6FFFFFF), fontSize = 12.sp)
                    Text(text = "Total de créditos: ${program.creditos}", color = Color(0xE6FFFFFF), fontSize = 12.sp)
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = onVerMas,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF0B2447)),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(36.dp)
                    ) { Text("Ver más") }
                }
            }
        }
    }
}
