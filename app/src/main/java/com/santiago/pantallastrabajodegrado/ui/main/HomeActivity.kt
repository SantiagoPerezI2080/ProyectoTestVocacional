package com.santiago.pantallastrabajodegrado.ui.main

import android.widget.TextView
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.santiago.pantallastrabajodegrado.R
import com.santiago.pantallastrabajodegrado.ui.auth.LoginActivity
import com.santiago.pantallastrabajodegrado.ui.auth.RegisterActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnIrALogin = findViewById<Button>(R.id.btnIrALogin)
        btnIrALogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val tvIrARegistro = findViewById<TextView>(R.id.tvIrARegistro)
        tvIrARegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
