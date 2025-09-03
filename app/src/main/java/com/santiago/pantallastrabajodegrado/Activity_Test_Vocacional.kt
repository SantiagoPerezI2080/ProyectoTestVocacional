package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // Botón back
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish() // Opcional: cierra esta Activity para no acumular en el stack
        }

    }
}
