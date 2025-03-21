package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debuggers.PantallaDeEntrenamientoAdapter.PredeterminadosEjerciciosAdapter
import com.example.debuggers.databinding.ActivityEjerciciosPredeterminadosBinding
import com.example.debuggers.databinding.ActivityEleccionEjerciciosBinding
import com.example.debuggers.dataclasses.ejercicios
import com.example.debuggers.dataclasses.ejerciciosPredeterminados
import com.example.debuggers.model.Ejercicio
import com.example.debuggers.model.EjercicioFactory

class ActivityEjerciciosPredeterminados : AppCompatActivity() {

    private lateinit var binding: ActivityEjerciciosPredeterminadosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjerciciosPredeterminadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creación de rutinas con el Factory
        val rutina1 = EjercicioFactory.createRutina1()
        val rutina2 = EjercicioFactory.createRutina2()
        val rutina3 = EjercicioFactory.createRutina3()

        // Configuración de los botones para pasar las rutinas seleccionadas
        binding.check1.setOnClickListener {
            val intentEntrenamiento = Intent(this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina1))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados")
            startActivity(intentEntrenamiento)
        }

        binding.check2.setOnClickListener {
            val intentEntrenamiento = Intent(this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina2))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados")
            startActivity(intentEntrenamiento)
        }

        binding.check3.setOnClickListener {
            val intentEntrenamiento = Intent(this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina3))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados")
            startActivity(intentEntrenamiento)
        }

        binding.Button2.setOnClickListener {
            val intentAtrasEjPred = Intent(this, ActivityPantallaInicio::class.java)
            startActivity(intentAtrasEjPred)
        }
    }
}