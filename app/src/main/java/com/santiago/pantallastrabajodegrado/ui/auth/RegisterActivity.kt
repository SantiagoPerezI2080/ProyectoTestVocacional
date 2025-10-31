package com.santiago.pantallastrabajodegrado.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.santiago.pantallastrabajodegrado.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegistrarse.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etContrasena.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese correo y contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.etContrasena.error = "Mínimo 6 caracteres."
                binding.etContrasena.requestFocus()
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!password.any { it.isLetter() }) {
                binding.etContrasena.error = "Debe contener al menos una letra."
                binding.etContrasena.requestFocus()
                Toast.makeText(this, "La contraseña debe contener al menos una letra.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!password.any { it.isDigit() }) {
                binding.etContrasena.error = "Debe contener al menos un número."
                binding.etContrasena.requestFocus()
                Toast.makeText(this, "La contraseña debe contener al menos un número.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etContrasena.error = null

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("RegisterActivity", "createUserWithEmail:success")
                        Toast.makeText(baseContext, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Fallo el registro: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
