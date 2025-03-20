package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debuggers.Helper.DatabaseHelper
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.Helper.MusculosAdapter
import com.example.debuggers.databinding.ActivityEleccionMusculosBinding
import java.util.ArrayList


class ActivityEleccionMusculos : AppCompatActivity() {
    private lateinit var binding: ActivityEleccionMusculosBinding
    private lateinit var helper: DatabaseHelper
    private val selectedMusculos= mutableListOf<Musculo>()
    //private lateinit var listView: ListView
    private lateinit var selectedItems: MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEleccionMusculosBinding.inflate(layoutInflater)
        //listView = findViewById(R.id.list_musculos)
        setContentView(binding.root)

        binding.siguienteAElecEjercicios.setOnClickListener {
            val intentEleccionEjercicios = Intent (applicationContext, ActivityEleccionEjercicios::class.java)
            intentEleccionEjercicios.putParcelableArrayListExtra("musculosSeleccionados", ArrayList(selectedMusculos))
            startActivity(intentEleccionEjercicios)
        }
        binding.botonAtrasARegistro.setOnClickListener {
            val intentAtrasARegistro = Intent (this, ActivityPantallaInicio::class.java)
            startActivity(intentAtrasARegistro)
        }
        helper = DatabaseHelper.getInstance(this)
        val db = helper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.TABLE_NAME1,
            arrayOf(DatabaseHelper.ID_MUSCULO, DatabaseHelper.MUSCULO),
            null, null, null, null, null
        )

        val dataList = mutableListOf<Musculo>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseHelper.ID_MUSCULO))
                val musculos = getString(getColumnIndexOrThrow(DatabaseHelper.MUSCULO))
                dataList.add(Musculo(id.toInt(), musculos, false))
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.listMusculos.layoutManager = layoutManager
        binding.listMusculos.adapter = MusculosAdapter(this, dataList) {
            selectedMusculos.clear()
            selectedMusculos.addAll(it)
        }
        cursor.close()
    }
    /*override fun onResume() {
        super.onResume()
        // When the activity resumes, pass the selected items to the next activity
        val intent = Intent(this, ActivityEleccionEjercicios::class.java)
        intent.putIntegerArrayListExtra("musculosSeleccionados", ArrayList(selectedItems))
        startActivity(intent)
    }*/
}