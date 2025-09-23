package com.santiago.pantallastrabajodegrado


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2) // Nombre del archivo XML de layout

        val btnIniciarTest = findViewById<Button>(R.id.btn_iniciar_test)

        btnIniciarTest.setOnClickListener {
            val intent = Intent(this, Activity_Test_Vocacional::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Abrir Home
                    true
                }
                R.id.nav_test -> {
                    // Abrir Test
                    true
                }
                R.id.nav_carreras -> {
                    // Abrir Carreras
                    true
                }
                R.id.nav_perfil -> {
                    // Abrir Perfil
                    true
                }
                else -> false
            }
        }

    }
}