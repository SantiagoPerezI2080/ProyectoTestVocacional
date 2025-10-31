package com.santiago.pantallastrabajodegrado.ui.carreras

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.santiago.pantallastrabajodegrado.data.model.programasDeEjemplo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar

@Composable
fun CarrerasScreen(navController: NavHostController, onBack: () -> Unit) {
    ScreenWithTopBar(
        showLogo = false,
        title = "PROGRAMAS DE PREGRADO",
        navBack = null,
        onNotification = null
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(0xFF0A1E3D))
                .padding(innerPadding), // <= aplica innerPadding aquí
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(programasDeEjemplo) { index, programa ->
                ProgramaCard(program = programa, onVerMas = { navController.navigate("program/$index") })
            }
            item { androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(bottom = 80.dp)) }
        }
    }
}
