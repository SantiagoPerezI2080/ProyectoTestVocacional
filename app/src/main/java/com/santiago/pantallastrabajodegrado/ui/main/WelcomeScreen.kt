package com.santiago.pantallastrabajodegrado.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.santiago.pantallastrabajodegrado.ui.components.AppBottomNavigation
import com.santiago.pantallastrabajodegrado.navigation.AppNavGraph

@Composable
fun WelcomeScreen() {
    // Creamos aquí el NavController para que tanto la barra inferior como el NavHost
    // usen el mismo controlador de navegación (antes se rompía esa relación).
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) { innerPadding ->
        // Llamamos al AppNavGraph con el navController ya creado.
        // (Nota: AppNavGraph actualmente no aplica innerPadding dentro de las pantallas;
        //  lo dejamos así por ahora para restaurar la botonera rápidamente.)
        AppNavGraph(
            navController = navController,
            startDestination = "home"
        )
    }
}
