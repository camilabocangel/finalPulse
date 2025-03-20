package com.example.debuggers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debuggers.databinding.ActivityCrearCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityCrearCuenta : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere un momento")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.siguienteARegistro.setOnClickListener{

            validarInformacion()
        }
    }

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var r_password = ""
    private fun validarInformacion(){
        nombres = binding.usuarioRegistro.text.toString().trim()
        email = binding.emailRegistro.text.toString().trim()
        password = binding.contrasenaRegistro.text.toString().trim()
        r_password = binding.confirContrasenaRegistro.text.toString().trim()

        when {
            nombres.isEmpty() -> {
                binding.usuarioRegistro.error = "Este campo es obligatorio"
                return
            }
            email.isEmpty() -> {
                binding.emailRegistro.error = "Este campo es obligatorio"
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailRegistro.error = "Ingresa un email válido"
                return
            }
            password.isEmpty() -> {
                binding.contrasenaRegistro.error = "Este campo es obligatorio"
                return
            }
            r_password.isEmpty() -> {
                binding.confirContrasenaRegistro.error = "Confirma tu contraseña"
                return
            }
            password != r_password -> {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                registrarUsuario()
            }
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando tu cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                actualizarInformacion()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo crear la cuento debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun actualizarInformacion() {
        progressDialog.setMessage("Guardando información")

        val uidU = firebaseAuth.uid
        val nombresU = nombres
        val emailU = firebaseAuth.currentUser!!.email

        val datosUsuario = HashMap<String, Any>()

        datosUsuario["uid"] = "$uidU"
        datosUsuario["nombres"] = "$nombresU"
        datosUsuario["email"] = "$emailU"
        datosUsuario["proveedor"] = "Email"
        datosUsuario["estado"] = "Online"
        datosUsuario["imagen"] = ""

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, ActivityRegistrarDatos::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo crear la cuento debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}