package com.example.debuggers.model

object EjercicioFactory {
    fun crearRutina(tipo: Int): List<Ejercicio> {
        return when (tipo) {
            1 -> listOf(
                Ejercicio(1, 1, "Crossover bicep polea", "crossover_bicep_polea", "imagen_crossover_de_biceps", false, 10),
                Ejercicio(4, 1, "Curl inverso", "curl_inverso", "imagen_curl_inverso", false, 10),
                Ejercicio(5, 1, "Curl martillo", "curl_martillo", "imagen_curl_martillo", false, 10),
                Ejercicio(37, 6, "Press con barra", "press_con_barra", "imagen_press_con_barra", false, 10),
                Ejercicio(38, 6, "Press inclinado", "press_declinado", "imagen_press_declinado", false, 10),
                Ejercicio(41, 6, "Pushups", "pushups", "imagen_pushups", false, 10)
            )
            2 -> listOf(
                Ejercicio(23, 4, "Jalón al pecho", "jalon_al_pecho", "imagen_jalon_al_pecho", false, 10),
                Ejercicio(24, 4, "Pushdown", "pushdown", "imagen_pushdowns", false, 10),
                Ejercicio(26, 4, "Remo con barra t", "remo_barra_t", "imagen_remo_barra_t", false, 10),
                Ejercicio(29, 5, "Femoral sentado", "femoral_sentado", "imagen_femoral_sentado", false, 10),
                Ejercicio(31, 5, "Hiperextensiones", "hiperextensiones", "imagen_hiperextensiones", false, 10),
                Ejercicio(33, 5, "Peso muerto", "peso_muerto", "imagen_peso_muerto", false, 10)
            )
            3 -> listOf(
                Ejercicio(7, 2, "Fondos en paralelas", "fondos_en_paralelas", "imagen_fondos_en_paralelas", false, 10),
                Ejercicio(8, 2, "Copa con mancuernas", "copa_con_mancuerna", "imagen_copa_con_mancuernas", false, 10),
                Ejercicio(11, 2, "Pushdown con cuerda", "pushdown_con_cuerda", "imagen_pushdown_con_cuerda", false, 10),
                Ejercicio(14, 3, "Búlgaras", "bulgaras", "imagen_bulgaras", false, 10),
                Ejercicio(18, 3, "Lounges estáticos", "lounges_estaticos", "imagen_lounges_estaticos", false, 10),
                Ejercicio(19, 3, "Prensa de piernas", "prensa_de_piernas", "imagen_prensa_de_piernas", false, 10)
            )
            else -> emptyList()
        }
    }
}
