package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.debuggers.databinding.ActivityUserPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityUserPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityUserPerfilBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        cargarInformacion()

        binding.btnActualizarInfo.setOnClickListener {
            startActivity(Intent(this, ActivityEditarInformacion::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, ActivityLogin::class.java))
            finishAffinity()
        }
        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnHistorial.setOnClickListener {
            startActivity(Intent(this, ActivityHistorial::class.java))
        }
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = snapshot.child("nombres").value?.toString() ?: "N/A"
                    val altura = snapshot.child("altura").value?.toString() ?: "N/A"
                    val peso = snapshot.child("peso").value?.toString() ?: "N/A"
                    val imagen = snapshot.child("imagen").value?.toString()

                    binding.txtNombre.text = nombres
                    binding.txtAltura.text = altura
                    binding.txtPeso.text = peso

                    try {
                        Glide.with(this@ActivityUserPerfil)
                            .load(imagen)
                            .placeholder(R.drawable.ic_image_perfil)
                            .into(binding.perfil)

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@ActivityUserPerfil,
                            "Error al cargar la imagen: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ActivityUserPerfil,
                        "Error al cargar datos: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
