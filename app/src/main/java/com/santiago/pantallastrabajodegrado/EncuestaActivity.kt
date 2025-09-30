package com.santiago.pantallastrabajodegrado // Revisa tu nombre de paquete

import android.os.Bundle
import android.util.Log // Importar Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.santiago.pantallastrabajodegrado.databinding.ActivityEncuestaBinding

class EncuestaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEncuestaBinding
    private lateinit var logicaEncuesta: Encuesta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEncuestaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("EncuestaActivityLog", "onCreate - View Binding realizado, ContentView seteado.")


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            Log.d("EncuestaActivityLog", "onCreate - Aplicando WindowInsets.")
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("EncuestaActivityLog", "onCreate - Antes de crear instancia de Encuesta (lógica).")
        logicaEncuesta = Encuesta(
            context = this,
            contenedorPreguntasLayout = binding.contenedorPreguntas,
            btnEnviar = binding.btnEnviar
        )
        Log.d("EncuestaActivityLog", "onCreate - Después de crear instancia de Encuesta (lógica).")


        logicaEncuesta.mostrarEncuestaEnUI()
        Log.d("EncuestaActivityLog", "onCreate - Después de llamar a logicaEncuesta.mostrarEncuestaEnUI().")
    }
}
