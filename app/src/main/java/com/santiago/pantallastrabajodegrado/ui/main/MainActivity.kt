package com.santiago.pantallastrabajodegrado.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.santiago.pantallastrabajodegrado.R
import com.santiago.pantallastrabajodegrado.ui.main.WelcomeScreen
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val composeHost = findViewById<ComposeView>(R.id.compose_host)
        composeHost?.setContent {
            PantallasTrabajoDeGradoTheme {
                WelcomeScreen()
            }
        }
    }
}
