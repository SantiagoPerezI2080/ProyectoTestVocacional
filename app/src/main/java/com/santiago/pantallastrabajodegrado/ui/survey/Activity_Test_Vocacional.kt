package com.santiago.pantallastrabajodegrado.ui.survey

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.santiago.pantallastrabajodegrado.R

class Activity_Test_Vocacional : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_test_vocacional)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Redirigir inmediatamente a EncuestaActivity (misma carpeta ui.survey)
        val intent = Intent(this, EncuestaActivity::class.java)
        startActivity(intent)

        finish()
    }
}
