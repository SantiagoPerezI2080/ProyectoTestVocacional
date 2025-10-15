package com.santiago.pantallastrabajodegrado

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usamos setContent para cargar nuestra pantalla de Jetpack Compose
        setContent {
            // Envolvemos la pantalla principal con nuestro tema personalizado
            PantallasTrabajoDeGradoTheme {
                WelcomeScreen()
            }
        }
    }
}
