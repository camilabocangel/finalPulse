package com.example.debuggers

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.debuggers.PantallaDeEntrenamientoAdapter.PredeterminadosEjerciciosAdapter
import com.example.debuggers.databinding.ActivityEjerciciosPredeterminadosBinding
import com.example.debuggers.databinding.ActivityEleccionEjerciciosBinding
import com.example.debuggers.dataclasses.ejercicios
import com.example.debuggers.dataclasses.ejerciciosPredeterminados
import com.example.debuggers.model.Ejercicio

class ActivityEjerciciosPredeterminados : AppCompatActivity() {

    private lateinit var binding :ActivityEjerciciosPredeterminadosBinding

    private val PredeterminadosEjerciciosAdapter by lazy { PredeterminadosEjerciciosAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEjerciciosPredeterminadosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rutina1:List<Ejercicio> = listOf(
            Ejercicio(1,1,"Crossover bicep polea","crossover_bicep_polea","imagen_crossover_de_biceps",true,10),
            Ejercicio(4,1,"Curl inverso","curl_inverso","imagen_curl_inverso",true,10),
            Ejercicio(5,1,"Curl martillo","curl_martillo","imagen_curl_martillo",true,10),
            Ejercicio(37,6,"Press con barra","press_con_barra","imagen_press_con_barra",true,10),
            Ejercicio(38,6,"Press inclinado","press_declinado","imagen_press_declinado",true,10),
            Ejercicio(41,6,"Pushups","pushups","imagen_pushups",true,10))

        val rutina2:List<Ejercicio> = listOf(
            Ejercicio(23,4,"Jalón al pecho","jalon_al_pecho","imagen_jalon_al_pecho",true,10),
            Ejercicio(24,4,"Pushdown","pushdown","imagen_pushdowns",true,10),
            Ejercicio(26,4,"Remo con barra t","remo_barra_t","imagen_remo_barra_t",true,10),
            Ejercicio(29,5,"Femoral sentado","femoral_sentado","imagen_femoral_sentado",true,10),
            Ejercicio(31,5,"Hiperextensiones","hiperextensiones","imagen_hiperextensiones",true,10),
            Ejercicio(33,5,"Peso muerto","peso_muerto","imagen_peso_muerto",true,10))

        val rutina3:List<Ejercicio> = listOf(
            Ejercicio(7,2,"Fondos en paralelas","fondos_en_paralelas","imagen_fondos_en_paralelas",true,10),
            Ejercicio(8,2,"Copa con mancuernas","copa_con_mancuerna","imagen_copa_con_mancuernas",true,10),
            Ejercicio(11,2,"Pushdown con cuerda","pushdown_con_cuerda","imagen_pushdown_con_cuerda",true,10),
            Ejercicio(14,3,"Búlgaras","bulgaras","imagen_bulgaras",true,10),
            Ejercicio(18,3,"Lounges estáticos","lounges_estaticos","imagen_lounges_estaticos",true,10),
            Ejercicio(19,3,"Prensa de piernas","prensa_de_piernas","imagen_prensa_de_piernas",true,10))

        binding.check1.setOnClickListener {
            val intentEntrenamiento = Intent (this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina1))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados") // Origen
            startActivity(intentEntrenamiento)
        }
        binding.check2.setOnClickListener {
            val intentEntrenamiento = Intent (this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina2))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados") // Origen
            startActivity(intentEntrenamiento)
        }
        binding.check3.setOnClickListener {
            val intentEntrenamiento = Intent (this, ActivityPantallaDeEntrenamiento::class.java)
            intentEntrenamiento.putParcelableArrayListExtra("ejerciciosSeleccionados", ArrayList(rutina3))
            intentEntrenamiento.putExtra("actividad_origen", "ActivityEjerciciosPredeterminados") // Origen
            startActivity(intentEntrenamiento)
        }
        binding.Button2.setOnClickListener {
            val intentAtrasEjPred = Intent (this, ActivityPantallaInicio::class.java)
            startActivity(intentAtrasEjPred)
        }

    }


}