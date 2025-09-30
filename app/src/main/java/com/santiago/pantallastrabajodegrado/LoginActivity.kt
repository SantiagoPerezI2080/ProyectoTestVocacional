package com.santiago.pantallastrabajodegrado

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns // Importar Patterns para validación de email
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.santiago.pantallastrabajodegrado.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.etEmail.requestFocus()
        binding.etEmail.post {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etEmail, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etContrasena.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Ingrese su correo electrónico."
                binding.etEmail.requestFocus()
                Toast.makeText(this, "Por favor, ingrese su correo electrónico.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etEmail.error = null

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Formato de correo inválido."
                binding.etEmail.requestFocus()
                Toast.makeText(this, "Por favor, ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etEmail.error = null


            if (password.isEmpty()) {
                binding.etContrasena.error = "Ingrese su contraseña."
                binding.etContrasena.requestFocus()
                Toast.makeText(this, "Por favor, ingrese su contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etContrasena.error = null

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("LoginActivity", "signInWithEmail:success")
                        Toast.makeText(baseContext, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                        var errorMessage = "Fallo de autenticación."
                        task.exception?.message?.let {
                            if (it.contains("INVALID_LOGIN_CREDENTIALS") || it.contains("INVALID_CREDENTIAL")) {
                                errorMessage = "Credenciales incorrectas. Verifique su correo y contraseña."
                            } else if (it.contains("USER_NOT_FOUND") || it.contains("ERROR_USER_NOT_FOUND")) {
                                errorMessage = "Usuario no encontrado."
                            }
                        }
                        Toast.makeText(
                            baseContext,
                            errorMessage,
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
        }

        binding.btnIrARegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Listener para "Olvidó su contraseña"
        binding.tvOlvidoContrasena.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmail.error = "Ingrese su email para restablecer la contraseña."
                binding.etEmail.requestFocus()
                Toast.makeText(this, "Por favor, ingrese su email primero.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etEmail.error = null // Limpiar error si el email no está vacío

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Formato de correo inválido para restablecer."
                binding.etEmail.requestFocus()
                Toast.makeText(this, "Por favor, ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etEmail.error = null // Limpiar error si el email es válido


            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("LoginActivity", "PasswordResetEmailSent")
                        Toast.makeText(this, "Correo de restablecimiento enviado a $email", Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("LoginActivity", "ErrorSendingPasswordResetEmail", task.exception)
                        Toast.makeText(this, "Error al enviar correo: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
