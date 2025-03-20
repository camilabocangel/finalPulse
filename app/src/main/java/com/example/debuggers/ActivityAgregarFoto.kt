package com.example.debuggers

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debuggers.databinding.ActivityAgregarFotoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ActivityAgregarFoto : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarFotoBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var uriFoto: Uri? = null
    private val REQUEST_IMAGE_CAPTURE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setMessage("La foto se está subiendo")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonSeleccionarFoto.setOnClickListener {
            seleccionarFoto()
        }

        binding.buttonTomarFoto.setOnClickListener {
            if (uriFoto != null) {
                subirFoto()
            } else {
                Toast.makeText(this, "Por favor, seleccione una foto", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonTomarFotoCamara.setOnClickListener {
            tomarFoto()
        }

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun seleccionarFoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "No se puede abrir la cámara", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            uriFoto = data?.data
            binding.imageViewPreview.setImageURI(uriFoto)
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            val path = MediaStore.Images.Media.insertImage(contentResolver, photo, "foto_" + System.currentTimeMillis(), null)
            uriFoto = Uri.parse(path)
            binding.imageViewPreview.setImageURI(uriFoto)
        }
    }

    private fun subirFoto() {
        progressDialog.show()

        val uid = firebaseAuth.currentUser!!.uid
        val tiempo = "" + System.currentTimeMillis()
        val rutaNombre = "Fotos/$uid/foto_$tiempo"

        val storageRef = FirebaseStorage.getInstance().getReference(rutaNombre)
        storageRef.putFile(uriFoto!!)
            .addOnSuccessListener { task ->
                task.storage.downloadUrl.addOnSuccessListener { uri ->
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = "$tiempo"
                    hashMap["fotoUri"] = "$uri"
                    hashMap["tiempo"] = "$tiempo"

                    val databaseRef = FirebaseDatabase.getInstance().getReference("Fotos/$uid")
                    databaseRef.child(tiempo).setValue(hashMap)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(this, "Foto subida exitosamente", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            progressDialog.dismiss()
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
