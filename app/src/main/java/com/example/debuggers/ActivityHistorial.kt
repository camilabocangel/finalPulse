package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debuggers.Helper.DatabaseHelper
import com.example.debuggers.adapters.PantallaDeEntrenamientoAdapter.PantallaDeEntrenamientoAdapter
import com.example.debuggers.databinding.ActivityHistorialBinding
import com.example.debuggers.databinding.ActivityPantallaDeEntrenamientoBinding
import com.example.debuggers.dataclasses.Registro
import com.example.debuggers.dataclasses.ejercicios
import com.example.debuggers.dataclasses.ejerciciosPredeterminados
import com.example.debuggers.model.Ejercicio

class ActivityHistorial : AppCompatActivity() {
    private lateinit var binding: ActivityHistorialBinding
    private lateinit var registros: List<Registro>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = DatabaseHelper.getInstance(this)
        registros = dbHelper.getHistorial()
        binding.recyclerHistorial.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistorial.adapter = com.example.debuggers.Helper.HistorialAdapter(this, registros)
        binding.botonAtrasAPantallaInicio.setOnClickListener {
            val intentPantallaInicio = Intent (this, ActivityPantallaInicio::class.java)
            startActivity(intentPantallaInicio)
        }
    }
}