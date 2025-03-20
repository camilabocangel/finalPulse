package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.debuggers.databinding.ActivityGaleriaFotosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActivityGaleriaFotos : AppCompatActivity() {

    private lateinit var binding: ActivityGaleriaFotosBinding
    private lateinit var fotoArrayList: ArrayList<ModeloFoto>
    private lateinit var adaptadorFoto: FotoAdapter
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaFotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        cargarFotos()
        configurarSpinners()
        configurarRecyclerView()

        binding.FABAgregarFoto.setOnClickListener {
            val intentAgregarFoto = Intent(this, ActivityAgregarFoto::class.java)
            startActivity(intentAgregarFoto)
        }

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun configurarSpinners() {
        val meses = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val anios = (2018..2024).map { it.toString() }

        val adapterMes = ArrayAdapter(this, android.R.layout.simple_spinner_item, meses).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        val adapterAnio = ArrayAdapter(this, android.R.layout.simple_spinner_item, anios).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerMes.adapter = adapterMes
        binding.spinnerAnio.adapter = adapterAnio

        binding.spinnerMes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val mesSeleccionado = position + 1  // Los meses comienzan desde 0
                val anioSeleccionado = binding.spinnerAnio.selectedItem.toString().toInt()
                actualizarFotosFiltradas(mesSeleccionado, anioSeleccionado)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerAnio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val mesSeleccionado = binding.spinnerMes.selectedItemPosition + 1
                val anioSeleccionado = binding.spinnerAnio.selectedItem.toString().toInt()
                actualizarFotosFiltradas(mesSeleccionado, anioSeleccionado)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun configurarRecyclerView() {
        binding.recyclerViewFotos.layoutManager = GridLayoutManager(this, 2)

        adaptadorFoto = FotoAdapter(this@ActivityGaleriaFotos, fotoArrayList)
        binding.recyclerViewFotos.adapter = adaptadorFoto
    }

    private fun cargarFotos() {
        fotoArrayList = ArrayList()
        val uid = firebaseAuth.currentUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("Fotos/$uid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fotoArrayList.clear()
                for (ds in snapshot.children) {
                    val modeloFoto = ds.getValue(ModeloFoto::class.java)
                    modeloFoto?.let {
                        val fecha = it.fechaPublicacion
                        val fechaCalendar = convertirStringAFecha(fecha)
                        it.fechaEnCalendario = fechaCalendar
                        fotoArrayList.add(it)
                    }
                }
                adaptadorFoto.updateData(fotoArrayList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun convertirStringAFecha(fechaString: String): Calendar {
        val calendar = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(fechaString)
            calendar.time = date
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return calendar
    }

    private fun actualizarFotosFiltradas(mes: Int, anio: Int) {
        if (mes == 0 || anio == 0) {
            adaptadorFoto.updateData(fotoArrayList)
        } else {
            val fotosFiltradas = filtrarFotosPorFecha(fotoArrayList, mes, anio)
            if (fotosFiltradas.isEmpty()) {
                Toast.makeText(this@ActivityGaleriaFotos, "No hay fotos para este mes/año", Toast.LENGTH_SHORT).show()
            }
            adaptadorFoto.updateData(fotosFiltradas)
        }
    }

    private fun filtrarFotosPorFecha(fotoArrayList: ArrayList<ModeloFoto>, mes: Int, anio: Int): ArrayList<ModeloFoto> {
        return fotoArrayList.filter { modeloFoto ->
            try {
                val calendar = modeloFoto.fechaEnCalendario
                calendar?.let {
                    val photoMonth = it.get(Calendar.MONTH) + 1
                    val photoYear = it.get(Calendar.YEAR)
                    photoMonth == mes && photoYear == anio
                } ?: false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } as ArrayList<ModeloFoto>
    }


}
