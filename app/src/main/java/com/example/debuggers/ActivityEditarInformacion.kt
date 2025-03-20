package com.example.debuggers

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.debuggers.databinding.ActivityEditarInformacionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ActivityEditarInformacion : AppCompatActivity() {

    private lateinit var binding: ActivityEditarInformacionBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var imagenUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarInformacionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        cargarInformacion()

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.IvEditarImg.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                abrirGaleria()
            }else{
                solicitarPermisoDeAlmacenamiento.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        binding.btnActualizar.setOnClickListener {
            validarInformacion()
        }

    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galeriaARL.launch(intent)
    }

    private val galeriaARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if(resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imagenUri = data!!.data
                subirImagenStorage(imagenUri)
            }else{
                Toast.makeText(
                    this,
                    "Cancelado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun subirImagenStorage(imagenUri: Uri?) {
        progressDialog.setMessage("Subir Imagen a Storage")
        progressDialog.show()

        val rutaImagen = "imagenesPerfil/" + firebaseAuth.uid
        val ref = FirebaseStorage.getInstance().getReference(rutaImagen)
        ref.putFile(imagenUri!!)
            .addOnSuccessListener {taskSnapShot->
                val uriTask = taskSnapShot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val urlImagenCargada = uriTask.result.toString()

                if (uriTask.isSuccessful){
                    actualizarInfoBD(urlImagenCargada)
                }
            }
            .addOnFailureListener {e->

            }

    }

    private fun actualizarInfoBD(urlImagenCargada: String) {
        progressDialog.setMessage("Actualizando imagen")
        progressDialog.show()

        val hashMap: HashMap<String, Any> = HashMap()
        if (imagenUri != null){
            hashMap["imagen"] = urlImagenCargada
        }

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Su imagen del perfil se ha actualizado",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private val solicitarPermisoDeAlmacenamiento =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){esConcedido->
            if (esConcedido){
                abrirGaleria()
            }else{
                Toast.makeText(
                    this,
                    "El permiso de almacenamiento ha sido denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    private var nombres = ""
    private var altura = ""
    private var peso = ""

    private fun validarInformacion() {
        nombres = binding.etNombres.text.toString().trim()
        altura = binding.etAltura.text.toString().trim()
        peso = binding.etPeso.text.toString().trim()

        if(nombres.isEmpty()){
            binding.etNombres.error = "Ingrese su nombre"
            binding.etNombres.requestFocus()
        }else if (altura.isEmpty()){
            binding.etAltura.error = "Ingrese su altura"
            binding.etAltura.requestFocus()
        }else if (peso.isEmpty()){
            binding.etPeso.error = "Ingrese su peso"
            binding.etPeso.requestFocus()
        }else{
            actualizarInfo()
        }
    }

    private fun actualizarInfo() {
        progressDialog.setMessage("Actualizando Informacion")
        progressDialog.show()

        val hashMap : HashMap<String, Any> = HashMap()

        hashMap["nombres"] = nombres
        hashMap["altura"] = altura
        hashMap["peso"] = peso

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "Su informacion ha sido actualizada",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child("${firebaseAuth.uid}")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = "${snapshot.child("nombres").value}"
                    val imagen = "${snapshot.child("imagen").value}"
                    val altura= "${snapshot.child("altura").value}"
                    val peso = "${snapshot.child("peso").value}"

                    binding.etNombres.setText(nombres)
                    binding.etAltura.setText(altura)
                    binding.etPeso.setText(peso)

                    try{
                        Glide.with(applicationContext)
                            .load(imagen)
                            .placeholder(R.drawable.ic_image_perfil)
                            .into(binding.ivPerfil)
                    }catch (e:Exception){
                        Toast.makeText(
                            applicationContext,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}