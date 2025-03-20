package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debuggers.databinding.ActivityEleccionEjerciciosBinding
import com.example.debuggers.dataclasses.ejercicios
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.Helper.DatabaseHelper
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.adapter.EjerciciosAdapter
import com.example.debuggers.model.Ejercicio

class ActivityEleccionEjercicios : AppCompatActivity() {
    private lateinit var binding: ActivityEleccionEjerciciosBinding
    private lateinit var recyclerEjercicios: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private val selectedEjercicios= mutableListOf<Ejercicio>()
    private lateinit var ejerciciosAdapter: EjerciciosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEleccionEjerciciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerEjercicios = findViewById(R.id.recyclerEjercicios)
        dbHelper = DatabaseHelper.getInstance(this)
        val selectedItems: List<Musculo>? = intent.getParcelableArrayListExtra("musculosSeleccionados")
        if (selectedItems != null) {
            val ejercicios = getEjerciciosPorMusculo(selectedItems.toList())
            ejerciciosAdapter = EjerciciosAdapter(this,ejercicios){
                selectedEjercicios.clear()
                selectedEjercicios.addAll(it)
            }

        }
        recyclerEjercicios.layoutManager = LinearLayoutManager(this)
        recyclerEjercicios.adapter = ejerciciosAdapter
        binding.siguienteAEntrenamiento.setOnClickListener {
            val intentEntrenamiento = Intent (this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(selectedEjercicios))
            intentEntrenamiento.putParcelableArrayListExtra("musculosSeleccionados", ArrayList(selectedItems))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEleccionEjercicios") // Origen
            startActivity(intentEntrenamiento)
        }
        binding.botonAtrasAEleccionMusculo.setOnClickListener {
            val intentAtrasMusculos = Intent (this, ActivityEleccionMusculos::class.java)
            startActivity(intentAtrasMusculos)
        }
    }
    private fun getEjerciciosPorMusculo(selectedMuscles: List<Musculo>): List<Ejercicio> {
        val ejercicios = mutableListOf<Ejercicio>()
        val db = dbHelper.readableDatabase
        val query = """
            SELECT * FROM ${DatabaseHelper.TABLE_NAME2} 
            WHERE ${DatabaseHelper.ID_MUSCULO} IN (${selectedMuscles.map { it.id }.joinToString(",")})
        """
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val ejercicio = Ejercicio(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID_EJERCICIOS)),
                    idMusculo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID_MUSCULO)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOMBRE_EJERCICIO)),
                    gif = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.GIF_EJERCICIO)),
                    imagen = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IMAGEN_EJERCICIO)),
                    isSelected = false,
                    peso = 10
                )
                ejercicios.add(ejercicio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ejercicios
    }

}