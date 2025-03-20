package com.example.debuggers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.debuggers.databinding.ActivityRegistrarDatosBinding
import com.example.debuggers.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegistrarDatos : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarDatosBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere un momento")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.siguienteAElecMusculos.setOnClickListener {
            validarDatos()
        }
    }

    private var peso = ""
    private var altura = ""

    private fun validarDatos() {
        peso = binding.pesoRegistro.text.toString().trim()
        altura = binding.alturaRegistro.text.toString().trim()

        when {
            peso.isEmpty() -> {
                binding.pesoRegistro.error = "Este campo es obligatorio"
                return
            }

            altura.isEmpty() -> {
                binding.alturaRegistro.error = "Este campo es obligatorio"
                return
            }

            else -> {
                guardarDatos()
            }
        }
    }

    private fun guardarDatos() {
        progressDialog.setMessage("Guardando tus datos")
        progressDialog.show()

        val uidU = firebaseAuth.uid
        val datosUsuario = HashMap<String, Any>()
        datosUsuario["peso"] = peso
        datosUsuario["altura"] = altura

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .updateChildren(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, ActivityPantallaInicio::class.java))
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Error al guardar los datos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
