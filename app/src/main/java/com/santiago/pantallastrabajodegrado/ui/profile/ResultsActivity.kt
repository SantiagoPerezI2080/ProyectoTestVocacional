package com.santiago.pantallastrabajodegrado.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.santiago.pantallastrabajodegrado.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
