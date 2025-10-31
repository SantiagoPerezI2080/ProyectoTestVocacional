package com.santiago.pantallastrabajodegrado.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PantallasTrabajoDeGradoTheme {
                WelcomeScreen()
            }
        }
    }
}
