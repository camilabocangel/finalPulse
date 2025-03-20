package com.example.debuggers.dataclasses

data class Registro(
    val id: Int,
    val fecha: String,
    val ejercicios: MutableList<RegistroEjercicio>
)

data class RegistroEjercicio(
    val ejercicio: String,
    val peso: Int
)