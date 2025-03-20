package com.example.debuggers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.debuggers.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()



        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere porfavor")
        progressDialog.setCanceledOnTouchOutside(false)

        //LoginVerificacionDeUsuario
        binding.botonLogin.setOnClickListener{
           validarInformacion()
        }


        binding.crearCuenta.setOnClickListener{
            startActivity(Intent(applicationContext,ActivityCrearCuenta::class.java))
        }
    }



    private var email = ""
    private var password = ""
    private fun validarInformacion(){
        email = binding.emailLogin.text.toString().trim()
        password = binding.contrasenaLogin.text.toString().trim()

        when{
            email.isEmpty()->{
                binding.emailLogin.error = "Este campo es obligatorio"
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->{
                binding.emailLogin.error = "Ingresa un email valido"
                return
            }
            password.isEmpty()->{
                binding.contrasenaLogin.error = "Este campo es obligatorio"
                return
            }else->{
                logearUsuario()
            }
        }
    }

    private fun logearUsuario() {
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, ActivityPantallaInicio::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se realizo el logeo debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}