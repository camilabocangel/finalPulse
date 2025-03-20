package com.example.debuggers.adapters.PantallaDeEntrenamientoAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.geometry.CornerRadius
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.debuggers.R
import com.example.debuggers.model.Ejercicio
import kotlin.math.round

class PantallaDeEntrenamientoAdapter(private val context: Context,
                                     private val listaEjercicios:List<Ejercicio>,
                                     private val onItemUpdated: (List<Ejercicio>) -> Unit
) : RecyclerView.Adapter<PantallaDeEntrenamientoAdapter.EntrenamientoViewHolder>() {

    inner class EntrenamientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreEjercicio: TextView = itemView.findViewById(R.id.ejercicioEnPeso)
        val gifEjercicio: ImageView = itemView.findViewById(R.id.gifEjercicio)
        val pesoTextView: TextView = itemView.findViewById(R.id.contador)
        val botonIncrementar: Button = itemView.findViewById(R.id.botonAumentarPeso)
        val botonDecrementar: Button = itemView.findViewById(R.id.botonRebajarPeso)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrenamientoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entrenamiento, parent, false)
        return EntrenamientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntrenamientoViewHolder, position: Int) {
        val ejercicio = listaEjercicios[position]
        holder.nombreEjercicio.text = ejercicio.nombre
        val resourceId = context.resources.getIdentifier(ejercicio.gif, "drawable", context.packageName)
        if (resourceId != 0) {
            Glide.with(context)
            .asGif()
            .load(resourceId).centerCrop()
            .into(holder.gifEjercicio)
        }

        holder.pesoTextView.text = ejercicio.peso.toString()
        holder.botonIncrementar.setOnClickListener {
            ejercicio.peso += 1
            holder.pesoTextView.text = ejercicio.peso.toString()
            onItemUpdated(listaEjercicios)
        }
        holder.botonDecrementar.setOnClickListener {
            if (ejercicio.peso > 0) {
                ejercicio.peso -= 1
                holder.pesoTextView.text = ejercicio.peso.toString()
                onItemUpdated(listaEjercicios)
            }
        }

    }

    override fun getItemCount(): Int = listaEjercicios.size

}