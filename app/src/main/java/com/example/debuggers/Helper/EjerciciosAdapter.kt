package com.example.debuggers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.debuggers.R
import com.example.debuggers.model.Ejercicio


class EjerciciosAdapter(
    private val context: Context,
    private val ejerciciosList: List<Ejercicio>,
    private val onClick: (List<Ejercicio>) -> Unit

) : RecyclerView.Adapter<EjerciciosAdapter.EjercicioViewHolder>() {

    inner class EjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreEjercicio: CheckBox = itemView.findViewById(R.id.checkboxEjercicios)
        val imagenEjercicio: ImageView = itemView.findViewById(R.id.imagenEjercicio)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ejercicios, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejerciciosList[position]

        holder.nombreEjercicio.text = ejercicio.nombre
         // Ejemplo: Usa un recurso o Glide para cargar imágenes
        val resourceId = context.resources.getIdentifier(ejercicio.imagen, "drawable", context.packageName)
        if (resourceId != 0) {
            holder.imagenEjercicio.setImageResource(resourceId)
            Glide.with(context)
                .load(resourceId).centerCrop()
                .into(holder.imagenEjercicio)
        } else {

        }
        // Manejo del CheckBox
        holder.nombreEjercicio.isChecked = ejercicio.isSelected
        holder.nombreEjercicio.setOnCheckedChangeListener { _, isChecked ->
            ejercicio.isSelected = isChecked
            onClick(ejerciciosList.filter { it.isSelected }) //devuelve la lista filtrada
        }
    }

    override fun getItemCount(): Int = ejerciciosList.size
}