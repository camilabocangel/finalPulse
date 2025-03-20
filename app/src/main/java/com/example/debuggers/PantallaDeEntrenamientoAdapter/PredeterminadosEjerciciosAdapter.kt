package com.example.debuggers.PantallaDeEntrenamientoAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.databinding.ItemHistorialBinding
import com.example.debuggers.databinding.ItemPredeterminadosBinding
import com.example.debuggers.dataclasses.ejercicios
import com.example.debuggers.dataclasses.ejerciciosPredeterminados

class PredeterminadosEjerciciosAdapter : RecyclerView.Adapter<PredeterminadosEjerciciosAdapter.EjemploViewHolder>() {

    private val listaDatos = mutableListOf<ejerciciosPredeterminados>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemPredeterminadosBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EjemploViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class EjemploViewHolder(private val binding: ItemPredeterminadosBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: ejerciciosPredeterminados) {

            binding.musculosPredeterminados.text = data.nombreMs
            binding.ejercicioPredeterminadosRecycler.text = data.nombreEj
        }
    }

    fun addDataToList(list: List<ejerciciosPredeterminados>) {
        listaDatos.clear()
        listaDatos.addAll(list)
        notifyDataSetChanged()
    }

}