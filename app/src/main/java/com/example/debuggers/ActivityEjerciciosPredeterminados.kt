package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.debuggers.databinding.ActivityEjerciciosPredeterminadosBinding
import com.example.debuggers.model.Ejercicio
import com.example.debuggers.model.EjercicioFactory

class ActivityEjerciciosPredeterminados : AppCompatActivity() {
    private lateinit var binding: ActivityEjerciciosPredeterminadosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjerciciosPredeterminadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir las rutinas
        val rutina1 = EjercicioFactory.crearRutina(1)
        val rutina2 = EjercicioFactory.crearRutina(2)
        val rutina3 = EjercicioFactory.crearRutina(3)

        // Eventos de los botones para seleccionar rutinas
        binding.check1.setOnClickListener { iniciarEntrenamiento(rutina1) }
        binding.check2.setOnClickListener { iniciarEntrenamiento(rutina2) }
        binding.check3.setOnClickListener { iniciarEntrenamiento(rutina3) }
    }

    private fun iniciarEntrenamiento(rutina: List<Ejercicio>) {
        // Crear un intent y pasar la lista de ejercicios seleccionados
        val intentEntrenamiento = Intent(this, ActivityPantallaDeEntrenamiento::class.java)
        intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina))
        intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados")
        startActivity(intentEntrenamiento)
    }
}
