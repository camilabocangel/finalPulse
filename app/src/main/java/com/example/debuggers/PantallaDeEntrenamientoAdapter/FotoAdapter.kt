package com.example.debuggers

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.debuggers.ModeloFoto
import com.example.debuggers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FotoAdapter(
    private val context: Context,
    private var fotoArrayList: ArrayList<ModeloFoto>?
) : RecyclerView.Adapter<FotoAdapter.EjemploViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjemploViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_foto, parent, false)
        return EjemploViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fotoArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: EjemploViewHolder, position: Int) {
        val modeloFoto = fotoArrayList?.get(position)
        val fotoUri: String? = modeloFoto?.fotoUri

        Glide.with(context)
            .load(Uri.parse(fotoUri))
            .into(holder.imageView)

        holder.imageView.setOnLongClickListener {
            modeloFoto?.let { foto ->
                mostrarConfirmacion(foto)
            }
            true
        }
    }

    class EjemploViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewFoto)
    }

    private fun mostrarConfirmacion(foto: ModeloFoto) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro de que quieres eliminar esta foto?")
            .setPositiveButton("Eliminar") { dialog, which ->
                eliminarFoto(foto)
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun eliminarFoto(foto: ModeloFoto) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("Fotos/$uid").child(foto.id ?: "")

        ref.removeValue().addOnSuccessListener {
            Toast.makeText(context, "Foto eliminada", Toast.LENGTH_SHORT).show()

            fotoArrayList?.remove(foto)

            notifyDataSetChanged()
        }.addOnFailureListener {
            Toast.makeText(context, "Error al eliminar la foto", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(nuevaLista: ArrayList<ModeloFoto>) {
        this.fotoArrayList = nuevaLista
        notifyDataSetChanged()
    }
}
