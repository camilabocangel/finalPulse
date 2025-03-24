package com.example.debuggers.PatronEleccionEjercicios.controller

import android.content.Context
import com.example.debuggers.model.Ejercicio
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioObservable
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioModel
import com.example.debuggers.PatronEleccionEjercicios.model.EjercicioObserver


class EjercicioController(
    private val context: Context,
    private val ejercicioObservable: EjercicioObservable
) {
    fun obtenerEjercicios(selectedMuscles: List<Musculo>): List<Ejercicio> {
        val ejercicioModel = EjercicioModel(context)
        return ejercicioModel.getEjerciciosPorMusculo(selectedMuscles)
    }

    fun agregarObservador(observer: EjercicioObserver) {
        ejercicioObservable.agregarObservador(observer)
    }

    fun eliminarObservador(observer: EjercicioObserver) {
        ejercicioObservable.eliminarObservador(observer)
    }

    fun seleccionarEjercicio(ejercicio: Ejercicio) {
        ejercicioObservable.seleccionarEjercicio(ejercicio)
    }

    fun deseleccionarEjercicio(ejercicio: Ejercicio) {
        ejercicioObservable.deseleccionarEjercicio(ejercicio)
    }

    fun obtenerEjerciciosSeleccionados(): List<Ejercicio> {
        return ejercicioObservable.obtenerEjerciciosSeleccionados()
    }
}
