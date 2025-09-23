package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Este layout debe existir
        val btnIniciarSesion = findViewById<Button>(R.id.btnRegistrarse)
        val btnRegistrarse = findViewById<Button>(R.id.btnLogin)
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}

