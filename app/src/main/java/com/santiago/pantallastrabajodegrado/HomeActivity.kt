package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos el layout que ya tienes
        setContentView(R.layout.activity_welcome)

        // Encontramos el botón por su ID
        val btnIrALogin = findViewById<Button>(R.id.btnIrALogin)

        // Configuramos el click para abrir LoginActivity
        btnIrALogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
