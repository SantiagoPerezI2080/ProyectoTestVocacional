package com.santiago.pantallastrabajodegrado.ui.profile

import android.content.Intent
import android.os.Bundle
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
        binding.optionMessages.setOnClickListener {
            // Navegar a mensajes (implementar)
        }

        binding.optionResults.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }

        binding.optionOther.setOnClickListener {
            // Navegar a otra pantalla (implementar)
        }

        binding.btnUpdate.setOnClickListener {
            // Actualizar datos (implementar)
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
        val i = Intent(this, com.santiago.pantallastrabajodegrado.ui.auth.LoginActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }
}
