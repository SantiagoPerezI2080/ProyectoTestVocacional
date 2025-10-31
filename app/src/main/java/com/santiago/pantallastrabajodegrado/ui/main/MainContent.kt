package com.santiago.pantallastrabajodegrado.ui.main

import android.view.LayoutInflater
import android.util.Log
import android.widget.Button as XmlButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.santiago.pantallastrabajodegrado.R
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar

@Composable
fun MainContent(navController: NavController) {
    ScreenWithTopBar(
        showLogo = false,
        title = "INICIO",
        //onNotification = { /* sin notificaciones por ahora */ }
    ) { innerPadding ->

        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(R.layout.activity_welcome2, null, false).apply {
                    findViewById<XmlButton>(R.id.btn_iniciar_test).setOnClickListener {
                        Log.d("MainContent", "btn_iniciar_test clicked - navigating to Kuder intro")
                        navController.navigate("kuder_intro")
                    }
                    findViewById<XmlButton>(R.id.btn_ver_resultados).setOnClickListener {
                        Log.d("MainContent", "btn_ver_resultados clicked - navigating to Kuder results")
                        navController.navigate("kuder_results")
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // ✅ Esto ya está correcto ahora que importamos
        )
    }
}
