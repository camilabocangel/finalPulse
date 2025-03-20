package com.example.debuggers.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ejercicio(
    val id: Int,
    val idMusculo: Int,
    val nombre: String,
    val gif: String,
    val imagen: String,
    var isSelected: Boolean=false,
    var peso: Int
):Parcelable