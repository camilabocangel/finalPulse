package com.example.debuggers.PatronEleccionEjercicios.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.databinding.ActivityEleccionEjerciciosBinding
import com.example.debuggers.ActivityEleccionMusculos
import com.example.debuggers.ActivityPantallaDeEntrenamiento
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.adapter.EjerciciosAdapter
import com.example.debuggers.PatronEleccionEjercicios.controller.EjercicioController
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioObservable
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioObserver
import com.example.debuggers.model.Ejercicio

class ActivityEleccionEjercicios : AppCompatActivity(), EjercicioObserver {

    private lateinit var binding: ActivityEleccionEjerciciosBinding
    private lateinit var recyclerEjercicios: RecyclerView
    private lateinit var ejercicioController: EjercicioController
    private val ejercicioObservable = EjercicioObservable() // Instancia única y compartida
    private val selectedEjercicios = mutableListOf<Ejercicio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEleccionEjerciciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerEjercicios = binding.recyclerEjercicios
        ejercicioController = EjercicioController(this, ejercicioObservable) // Pasamos la misma instancia

        ejercicioObservable.agregarObservador(this) // Nos registramos como observadores

        val selectedItems: List<Musculo>? = intent.getParcelableArrayListExtra("musculosSeleccionados")
        if (selectedItems != null) {
            val ejercicios = ejercicioController.obtenerEjercicios(selectedItems)
            val ejerciciosAdapter = EjerciciosAdapter(this, ejercicios) { listaSeleccionada ->
                listaSeleccionada.forEach { ejercicioController.seleccionarEjercicio(it) }
            }
            recyclerEjercicios.layoutManager = LinearLayoutManager(this)
            recyclerEjercicios.adapter = ejerciciosAdapter
        }

        binding.siguienteAEntrenamiento.setOnClickListener {
            val intentEntrenamiento = Intent(this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(selectedEjercicios))
            intentEntrenamiento.putParcelableArrayListExtra("musculosSeleccionados", ArrayList(selectedItems))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEleccionEjercicios")
            startActivity(intentEntrenamiento)
        }

        binding.botonAtrasAEleccionMusculo.setOnClickListener {
            val intentAtrasMusculos = Intent(this, ActivityEleccionMusculos::class.java)
            startActivity(intentAtrasMusculos)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ejercicioObservable.eliminarObservador(this)
    }

    override fun onEjercicioSeleccionadoCambiado(ejercicios: List<Ejercicio>) {
        selectedEjercicios.clear()
        selectedEjercicios.addAll(ejercicios)
    }
}

