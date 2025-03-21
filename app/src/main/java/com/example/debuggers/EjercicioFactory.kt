package com.example.debuggers.model

import com.example.debuggers.dataclasses.ejercicios

object EjercicioFactory {

    fun createRutina1(): List<ejercicios> {
        return listOf(
            ejercicios("imagen_crossover_de_biceps", "Crossover bicep polea", 10, true),
            ejercicios("imagen_curl_inverso", "Curl inverso", 10, true),
            ejercicios("imagen_curl_martillo", "Curl martillo", 10, true),
            ejercicios("imagen_press_con_barra", "Press con barra", 10, true),
            ejercicios("imagen_press_declinado", "Press inclinado", 10, true),
            ejercicios("imagen_pushups", "Pushups", 10, true)
        )
    }

    fun createRutina2(): List<ejercicios> {
        return listOf(
            ejercicios("imagen_jalon_al_pecho", "Jalón al pecho", 10, true),
            ejercicios("imagen_pushdowns", "Pushdown", 10, true),
            ejercicios("imagen_remo_barra_t", "Remo con barra t", 10, true),
            ejercicios("imagen_femoral_sentado", "Femoral sentado", 10, true),
            ejercicios("imagen_hiperextensiones", "Hiperextensiones", 10, true),
            ejercicios("imagen_peso_muerto", "Peso muerto", 10, true)
        )
    }

    fun createRutina3(): List<ejercicios> {
        return listOf(
            ejercicios("imagen_fondos_en_paralelas", "Fondos en paralelas", 10, true),
            ejercicios("imagen_copa_con_mancuernas", "Copa con mancuernas", 10, true),
            ejercicios("imagen_pushdown_con_cuerda", "Pushdown con cuerda", 10, true),
            ejercicios("imagen_bulgaras", "Búlgaras", 10, true),
            ejercicios("imagen_lounges_estaticos", "Lounges estáticos", 10, true),
            ejercicios("imagen_prensa_de_piernas", "Prensa de piernas", 10, true)
        )
    }
}
