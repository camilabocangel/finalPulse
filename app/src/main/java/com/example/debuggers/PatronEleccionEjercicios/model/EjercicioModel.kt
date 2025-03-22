package com.example.debuggers.PatronEleccionEjercicios.model

import android.content.Context
import com.example.debuggers.Helper.DatabaseHelper
import com.example.debuggers.Helper.Musculo
import com.example.debuggers.model.Ejercicio

class EjercicioModel(private val context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(context)

    fun getEjerciciosPorMusculo(selectedMuscles: List<Musculo>): List<Ejercicio> {
        val ejercicios = mutableListOf<Ejercicio>()
        val db = dbHelper.readableDatabase
        val query = """
            SELECT * FROM ${DatabaseHelper.TABLE_NAME2} 
            WHERE ${DatabaseHelper.ID_MUSCULO} IN (${selectedMuscles.map { it.id }.joinToString(",")})
        """
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val ejercicio = Ejercicio(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID_EJERCICIOS)),
                    idMusculo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID_MUSCULO)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NOMBRE_EJERCICIO)),
                    gif = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.GIF_EJERCICIO)),
                    imagen = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IMAGEN_EJERCICIO)),
                    isSelected = false,
                    peso = 10
                )
                ejercicios.add(ejercicio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ejercicios
    }
}