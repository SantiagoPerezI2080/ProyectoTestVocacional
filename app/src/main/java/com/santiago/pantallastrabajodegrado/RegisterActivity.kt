package com.santiago.pantallastrabajodegrado

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aquí le decimos qué layout usar
        setContentView(R.layout.activity_register)
    }
}
