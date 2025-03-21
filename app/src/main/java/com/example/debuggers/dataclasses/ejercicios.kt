package com.example.debuggers.dataclasses

import android.os.Parcel
import android.os.Parcelable

data class ejercicios(
    val imagen: String,
    val nombreEj: String,
    var peso: Int = 0,
    var completado: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        imagen = parcel.readString() ?: "",
        nombreEj = parcel.readString() ?: "",
        peso = parcel.readInt(),
        completado = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagen)
        parcel.writeString(nombreEj)
        parcel.writeInt(peso)
        parcel.writeByte(if (completado) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ejercicios> {
        override fun createFromParcel(parcel: Parcel): ejercicios {
            return ejercicios(parcel)
        }

        override fun newArray(size: Int): Array<ejercicios?> {
            return arrayOfNulls(size)
        }
    }
}
