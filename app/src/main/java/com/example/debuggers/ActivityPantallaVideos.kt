package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import android.view.Display.Mode
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.debuggers.databinding.ActivityPantallaVideosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityPantallaVideos : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaVideosBinding

    private lateinit var videoArrayList : ArrayList<ModeloVideo>
    private lateinit var adaptadorVideo: AdaptadorVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaVideosBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        cargarVideos()
        binding.FABAgregarVideo.setOnClickListener {
            startActivity(Intent(applicationContext,ActivityAgregarVideo::class.java))
        }

        binding.IbRegresar.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun cargarVideos() {
        videoArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Videos")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                videoArrayList.clear()
                for (ds in snapshot.children){
                    val modeloVideo = ds.getValue(ModeloVideo::class.java)
                    videoArrayList.add(modeloVideo!!)
                }

                adaptadorVideo = AdaptadorVideo(this@ActivityPantallaVideos, videoArrayList)
                binding.rvVideos.adapter = adaptadorVideo
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityPantallaVideos, "Error al cargar videos: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}