package com.example.debuggers.Helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.R
import com.example.debuggers.dataclasses.Registro
import com.example.debuggers.model.Ejercicio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistorialAdapter( private val context: Context, private val registros: List<Registro>): RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fecha: TextView = itemView.findViewById(R.id.fechaHistorial)
        val recycleDatos: RecyclerView = itemView.findViewById(R.id.recycleDatos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val registro = registros[position]
        holder.fecha.text = registro.fecha
        val datosAdapter = DatosAdapter(registro.ejercicios)
        holder.recycleDatos.layoutManager = LinearLayoutManager(context)
        holder.recycleDatos.adapter = datosAdapter
    }

    override fun getItemCount(): Int = registros.size
}