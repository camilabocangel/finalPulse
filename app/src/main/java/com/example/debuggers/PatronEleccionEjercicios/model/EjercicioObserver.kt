package com.example.debuggers.PatronEleccionEjercicios.model


import com.example.debuggers.model.Ejercicio

interface EjercicioObserver {
    fun onEjercicioSeleccionadoCambiado(ejerciciosSeleccionados: List<Ejercicio>)

}