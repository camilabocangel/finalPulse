package com.example.debuggers.PantallaDeEntrenamientoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.debuggers.R

class viewpageAdapter(private val imagenviewList: ArrayList<Int>, private val viewPager2: ViewPager2)
    :RecyclerView.Adapter<viewpageAdapter.ImageViewHolder>()
{
    class ImageViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.cuadroVerdeHistorial)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagenviewList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imagenviewList[position])
        if (position == imagenviewList.size-1){
            viewPager2.post(runnable)
        }
    }
    private val runnable = Runnable {
        imagenviewList.addAll(imagenviewList)
        notifyDataSetChanged()
    }
}