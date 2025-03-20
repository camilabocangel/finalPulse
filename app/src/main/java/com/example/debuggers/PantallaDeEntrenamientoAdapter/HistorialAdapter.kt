/*package com.example.debuggers.PantallaDeEntrenamientoAdapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.databinding.ItemEntrenamientoBinding
import com.example.debuggers.databinding.ItemHistorialBinding
import com.example.debuggers.dataclasses.ejercicios

class HistorialAdapter :
    RecyclerView.Adapter<HistorialAdapter.EjemploViewHolder>() {

    private val listaDatos = mutableListOf<ejercicios>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EjemploViewHolder {
        context = parent.context
        return EjemploViewHolder(
            ItemHistorialBinding.inflate(
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

    inner class EjemploViewHolder(private val binding: ItemHistorialBinding) :
        RecyclerView.ViewHolder(binding.root)  {
        fun binding(data: ejercicios) {

            binding.ejercicioHistorialRecycler.text = data.nombreEj
            binding.pesoHistorialRecycler.text = data.peso.toString()

        }
    }

    fun addDataToList(list: List<ejercicios>) {
        listaDatos.clear()
        listaDatos.addAll(list)
        notifyDataSetChanged()
    }

}*/