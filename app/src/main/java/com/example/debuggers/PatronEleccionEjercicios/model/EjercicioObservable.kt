package com.example.debuggers.PatronEleccionEjercicios.model

import com.example.debuggers.model.Ejercicio

class EjercicioObservable {
    private val observers = mutableListOf<EjercicioObserver>()
    private val ejerciciosSeleccionados = mutableListOf<Ejercicio>()

    fun agregarObservador(observer: EjercicioObserver) {
        observers.add(observer)
    }

    fun eliminarObservador(observer: EjercicioObserver) {
        observers.remove(observer)
    }

    fun seleccionarEjercicio(ejercicio: Ejercicio) {
        if (!ejerciciosSeleccionados.contains(ejercicio)) {
            ejerciciosSeleccionados.add(ejercicio)
            notificarObservadores()
        }
    }

    fun deseleccionarEjercicio(ejercicio: Ejercicio) {
        if (ejerciciosSeleccionados.contains(ejercicio)) {
            ejerciciosSeleccionados.remove(ejercicio)
            notificarObservadores()
        }
    }

    fun obtenerEjerciciosSeleccionados(): List<Ejercicio> {
        return ejerciciosSeleccionados
    }

    private fun notificarObservadores() {
        println("Notificando observadores: ${observers.size} observadores")
        observers.forEach { it.onEjercicioSeleccionadoCambiado(ejerciciosSeleccionados) }

    }
}
