package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.adapters.PantallaDeEntrenamientoAdapter.PantallaDeEntrenamientoAdapter
import com.example.debuggers.databinding.ActivityPantallaDeEntrenamientoBinding
import com.example.debuggers.model.Ejercicio
import com.example.debuggers.Helper.DatabaseHelper
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.PatronEleccionEjercicios.view.ActivityEleccionEjercicios

class ActivityPantallaDeEntrenamiento : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaDeEntrenamientoBinding
    private lateinit var recyclerEntrenamiento: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var entrenamientoAdapter: PantallaDeEntrenamientoAdapter
    private var musculosSeleccionados: List<Musculo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaDeEntrenamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerEntrenamiento = findViewById(R.id.recyclerEntrenamiento)
        dbHelper = DatabaseHelper.getInstance(this)

        // Recupera los datos del Intent
        var ejerciciosSeleccionados: List<Ejercicio> =
            intent.getParcelableArrayListExtra("ejerciciosSeleccionados") ?: emptyList()
        musculosSeleccionados = intent.getParcelableArrayListExtra("musculosSeleccionados")

        Log.d("ActivityPantallaDeEntrenamiento", "Ejercicios seleccionados: $ejerciciosSeleccionados")

        // Configura el adaptador y el RecyclerView
        entrenamientoAdapter = PantallaDeEntrenamientoAdapter(this, ejerciciosSeleccionados) { selectedExercises ->
            ejerciciosSeleccionados = selectedExercises
        }
        recyclerEntrenamiento.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerEntrenamiento.adapter = entrenamientoAdapter

        // Configura los botones
        binding.siguienteAFelicidades.setOnClickListener {
            dbHelper.insertRutina(ejerciciosSeleccionados)
            val intentFelicidades = Intent(this, ActivityFelicidades::class.java)
            startActivity(intentFelicidades)
        }

        binding.cancelarEntrenamiento.setOnClickListener {
            val actividadOrigen = intent.getStringExtra("actividad_origen")
            val intentAtrasEjercicio = when (actividadOrigen) {
                "ActivityEleccionEjercicios" -> {
                    Intent(this, ActivityEleccionEjercicios::class.java).apply {
                        putParcelableArrayListExtra("musculosSeleccionados", ArrayList(musculosSeleccionados))
                    }
                }
                "ActivityEjerciciosPredeterminados" -> {
                    Intent(this, ActivityEjerciciosPredeterminados::class.java)
                }
                else -> null
            }
            intentAtrasEjercicio?.let { startActivity(it) } // Si el intent no es nulo, inicia la actividad
        }
    }
}
