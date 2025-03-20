package com.example.debuggers.Helper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Musculo(
    val id: Int,
    val nombre: String,
    var isSelected: Boolean
):Parcelable