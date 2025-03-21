package com.example.debuggers.PatronEleccionEjercicios.controller

import android.content.Context
import com.example.debuggers.model.Ejercicio
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioModel
import com.example.debuggers.Helper.Musculo

class EjercicioController(context: Context) {

    private val ejercicioModel = EjercicioModel(context)

    fun obtenerEjercicios(selectedMuscles: List<Musculo>): List<Ejercicio> {
        return ejercicioModel.getEjerciciosPorMusculo(selectedMuscles)
    }
}
