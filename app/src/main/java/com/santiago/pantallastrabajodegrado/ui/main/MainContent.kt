package com.santiago.pantallastrabajodegrado.ui.main

import android.view.LayoutInflater
import android.util.Log
import android.widget.Button as XmlButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santiago.pantallastrabajodegrado.R
import com.santiago.pantallastrabajodegrado.ui.components.BottomNavItem
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalDensity

@Composable
fun MainContent(navController: NavController) {
    ScreenWithTopBar(
        showLogo = false,
        title = "INICIO",
        navBack = null,
        onNotification = null
    ) { innerPadding ->
        // Aplicar innerPadding al AndroidView para que no quede detrás de la topBar
        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(R.layout.activity_welcome2, null, false).apply {
                    findViewById<XmlButton>(R.id.btn_iniciar_test).setOnClickListener {
                        Log.d("MainContent", "btn_iniciar_test clicked - navigating to test")
                        navController.navigate(BottomNavItem.Test.route)
                    }
                    findViewById<XmlButton>(R.id.btn_ver_resultados).setOnClickListener {
                        Log.d("MainContent", "btn_ver_resultados clicked - navigating to results")
                        navController.navigate("results")
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // <= aquí
        )
    }
}
