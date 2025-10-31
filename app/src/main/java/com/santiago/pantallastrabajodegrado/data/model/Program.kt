package com.santiago.pantallastrabajodegrado.data.model

import androidx.compose.ui.graphics.Color
import com.santiago.pantallastrabajodegrado.R

data class Program(
    val titulo: String,
    val duracion: String,
    val creditos: Int,
    val imagenRes: Int,
    val colorTituloHex: Long = 0xFFFFFFFF // si usas Color en la UI, conviértelo ahí
)

// Datos de ejemplo (nombre igual al que usabas)
val programasDeEjemplo = listOf(
    Program("Ingeniería de Sistemas", "9 semestres", 157, R.drawable.ing_software, 0xFFFFFFFF),
    Program("Administración de Empresas", "9 semestres", 157, R.drawable.admin_empr, 0xFFFF7F2A),
    Program("Derecho", "10 semestres", 169, R.drawable.derecho, 0xFF39E27D),
    Program("Entrenamiento Deportivo", "9 semestres", 168, R.drawable.deporte, 0xFFD243F0),
    Program("Psicología", "10 semestres", 158, R.drawable.psicologia, 0xFFFFFFFF),
    Program("Medicina", "12 semestres", 280, R.drawable.medicina, 0xFF4FC3F7),
    Program("Diseño Gráfico", "8 semestres", 140, R.drawable.diseno_grafica, 0xFFFFEB3B),
    Program("Arquitectura", "10 semestres", 175, R.drawable.arquitectura, 0xFFFFFFFF),
    Program("Comunicación Social", "9 semestres", 155, R.drawable.com_social, 0xFFF06292),
    Program("Contaduría Pública", "9 semestres", 150, R.drawable.con_publica, 0xFFFFFFFF)
)
