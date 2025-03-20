package com.example.debuggers.Helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.debuggers.R

class MusculosAdapter(context: Context,
                      private val items: List<Musculo>,
    private val onClick: (List<Musculo>) -> Unit) :
RecyclerView.Adapter<MusculosAdapter.MusculoViewHolder>() {

    inner class MusculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_musculo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusculoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_musculo, parent, false)
        return MusculoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusculoViewHolder, position: Int) {
        val musculo = items[position]

        holder.checkBox.text = musculo.nombre
        // Manejo del CheckBox
        holder.checkBox.isChecked = musculo.isSelected
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            musculo.isSelected = isChecked
            onClick(items.filter { it.isSelected }) //devuelve la lista filtrada
        }
    }
    override fun getItemCount(): Int = items.size
}