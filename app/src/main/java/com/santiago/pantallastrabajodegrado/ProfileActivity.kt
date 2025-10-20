package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.santiago.pantallastrabajodegrado.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
        loadUserInfo()
    }

    private fun setupUi() {
        // Clicks de las tarjetas
        binding.optionMessages.setOnClickListener {
            // Navegar a mensajes
        }

        binding.optionResults.setOnClickListener {
            // Navegar a resultados
        }

        binding.optionOther.setOnClickListener {
            // Navegar a otra pantalla
        }

        binding.btnUpdate.setOnClickListener {
            // Actualizar datos
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }



    private fun loadUserInfo() {
        val user = auth.currentUser
        if (user != null) {
            binding.tvName.text = user.displayName ?: "Usuario"
            binding.tvEmail.text = user.email ?: "Sin correo"
        } else {
            redirectToLogin()
        }
    }

    private fun signOut() {
        auth.signOut()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }
}

