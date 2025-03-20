package com.example.debuggers

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.debuggers.databinding.ActivityAgregarVideoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ActivityAgregarVideo : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarVideoBinding
    private var uriVideo : Uri ?= null
    private var titulo : String ?= null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarVideoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setMessage("El video se esta subiendo")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnSelectVideo.setOnClickListener {
            videoPickDialog()
        }

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSubitVideo.setOnClickListener {
            titulo = binding.etTituloVideo.text.toString().trim()
            if(titulo.isNullOrEmpty()){
                Toast.makeText(applicationContext, "Por favor, ingrese un Titulo", Toast.LENGTH_SHORT).show()
            }else if(uriVideo == null){
                Toast.makeText(applicationContext, "Por favor, seleccione un video", Toast.LENGTH_SHORT).show()
            }else{
                subirVideo()
            }
        }
    }

    private fun subirVideo() {
        progressDialog.show()

        val autor = firebaseAuth.currentUser!!.displayName

        val tiempo = "" + System.currentTimeMillis()
        val rutaNombre = "Video/video_$tiempo"
        val storageRef = FirebaseStorage.getInstance().getReference(rutaNombre)
        storageRef.putFile(uriVideo!!)
            .addOnSuccessListener { task->
                val uriTask = task.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downloadUri = uriTask.result.toString()
                if (uriTask.isSuccessful){
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = "$tiempo"
                    hashMap["titulo"] = "$titulo"
                    hashMap["tiempo"] = "$tiempo"
                    hashMap["videoUri"] = "$downloadUri"
                    hashMap["autor"] = "$autor"

                    val reference = FirebaseDatabase.getInstance().getReference("Videos")
                    reference.child(tiempo)
                        .setValue(hashMap)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, "Se subio el video", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@ActivityAgregarVideo, ActivityPantallaVideos::class.java))
                            finish()
                        }
                        .addOnFailureListener { e->
                            Toast.makeText(applicationContext,"${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e->
                Toast.makeText(applicationContext,"${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun videoPickDialog() {
        val popupMenu = PopupMenu(this,binding.btnSelectVideo)

        popupMenu.menu.add(Menu.NONE,1,1,"Galeria")

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item->
            val itemId = item.itemId
            if(itemId ==1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    videoPickGaleria()
                } else {
                    solicitarPermisoDeAlmacenamiento.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun videoPickGaleria(){
        val intent = Intent()
        intent.type = "video/*"
        resultGaleriaARL.launch(intent)
    }

    private val resultGaleriaARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if(resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                uriVideo = data!!.data
                setVideo()
                binding.txtEstado.text = "El video se ha seleccionado y esta listo para subirse"
            }else{
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT)
            }
        }

    private val solicitarPermisoDeAlmacenamiento =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){esConcedido->
            if(esConcedido){
                videoPickGaleria()
            }else{
                Toast.makeText(this,
                    "El permiso no esta condedido",
                    Toast.LENGTH_SHORT).show()
            }

        }

    private fun setVideo() {

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)

        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(uriVideo)
        binding.videoView.requestFocus()
        binding.videoView.setOnPreparedListener{
            binding.videoView.pause()
        }









    }
}