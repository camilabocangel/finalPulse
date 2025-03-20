package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.debuggers.databinding.ActivityFelicidadesBinding

class ActivityFelicidades : AppCompatActivity() {
    private lateinit var binding: ActivityFelicidadesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFelicidadesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pantallaFelicidadess.setOnClickListener {
            val intentAtrasMusculos = Intent (this, ActivityPantallaInicio::class.java)
            startActivity(intentAtrasMusculos)
        }

    }
}