package com.santiago.pantallastrabajodegrado // Asegúrate de que el paquete es correcto

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_Test_Vocacional : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_test_vocacional) // Asegúrate que este layout existe

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Asumiendo que R.id.main es el ID del layout raíz en activity_test_vocacional.xml
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Redirigir inmediatamente a EncuestaActivity
        val intent = Intent(this, EncuestaActivity::class.java)
        startActivity(intent)


        finish()
    }
}

