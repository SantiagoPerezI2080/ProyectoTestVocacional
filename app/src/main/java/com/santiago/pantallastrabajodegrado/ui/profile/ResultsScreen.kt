package com.santiago.pantallastrabajodegrado.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar

@Composable
fun ResultsScreen(onBack: () -> Unit) {
    ScreenWithTopBar(
        showLogo = false,
        title = "Resultados",
        navBack = onBack,
        onNotification = {}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Aquí se mostrarán tus resultados")
            }
        }
    }
}
