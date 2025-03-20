package com.example.debuggers.Helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.R
import com.example.debuggers.dataclasses.RegistroEjercicio

class DatosAdapter (private val ejercicios: List<RegistroEjercicio>): RecyclerView.Adapter<DatosAdapter.DatosViewHolder>(){

    inner class DatosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ejercicio: TextView = itemView.findViewById(R.id.ejercicioH)
        val peso: TextView = itemView.findViewById(R.id.pesoH)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_datos, parent, false)
        return DatosViewHolder(view)
    }

    override fun onBindViewHolder(holder: DatosViewHolder, position: Int) {
        val datos = ejercicios[position]
        holder.ejercicio.text = datos.ejercicio
        holder.peso.text = datos.peso.toString()
    }
    override fun getItemCount(): Int = ejercicios.size
}