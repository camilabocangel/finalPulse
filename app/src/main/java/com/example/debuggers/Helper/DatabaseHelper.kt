package com.example.debuggers.Helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.debuggers.dataclasses.Registro
import com.example.debuggers.dataclasses.RegistroEjercicio
import com.example.debuggers.model.Ejercicio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Quintuple<A, B, C, D, E>(val first: A, val second: B, val third: C, val fourth: D, val fifth: E)




class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "gimnasio.db"
        private const val DATABASE_VERSION = 9

        const val TABLE_NAME1 = "musculos"
        const val ID_MUSCULO = "id"
        const val MUSCULO = "musculo"

        const val TABLE_NAME2 = "ejercicios"
        const val ID_EJERCICIOS = "id_ejercicos"
        const val NOMBRE_EJERCICIO = "nombre_ejercicio"
        const val GIF_EJERCICIO = "gif_ejercicio"
        const val IMAGEN_EJERCICIO = "imagen_ejercicio"

        const val TABLE_NAME3 = "registro"
        const val ID_REGISTRO = "id_registro"
        const val FECHA = "fecha"

        const val TABLE_NAME4 = "registro_ejercicios"
        const val ID_EJ_REGISTRO = "id_ejercicio_registro"
        const val PESO = "peso"
        const val TERMINADO = "terminado"

        @Volatile
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return instance ?: synchronized(this) {
                instance ?: DatabaseHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable1 = """
             CREATE TABLE $TABLE_NAME1 (
             $ID_MUSCULO INTEGER PRIMARY KEY,
             $MUSCULO TEXT NOT NULL
             )
             """
        db?.execSQL(createTable1)
        val createTable2 = """
            CREATE TABLE $TABLE_NAME2 (
            $ID_EJERCICIOS INTEGER PRIMARY KEY,
            $ID_MUSCULO INTEGER NOT NULL,
            $NOMBRE_EJERCICIO TEXT NOT NULL,
            $GIF_EJERCICIO TEXT NOT NULL,
            $IMAGEN_EJERCICIO TEXT NOT NULL
            )
            """
        db?.execSQL(createTable2)
        val createTable3 = """
             CREATE TABLE $TABLE_NAME3 (
             $ID_REGISTRO INTEGER PRIMARY KEY AUTOINCREMENT,
             $FECHA DATETIME NOT NULL
             )
             """
        db?.execSQL(createTable3)
        val createTable4 = """
             CREATE TABLE $TABLE_NAME4 (
             $ID_EJ_REGISTRO INTEGER PRIMARY KEY AUTOINCREMENT,
             $ID_REGISTRO INTEGER NOT NULL,
             $ID_EJERCICIOS INTEGER NOT NULL,
             $PESO INTEGER NOT NULL,
             $TERMINADO BOOLEAN NOT NULL
             )
             """
        db?.execSQL(createTable4)

        val musculos = listOf("Biceps", "Triceps","Cuadriceps", "Espalda", "Femoral", "Pecho")
        val ejercicios = listOf(
            Quintuple(1,1, "Crossover bicep polea", "crossover_bicep_polea","imagen_crossover_de_biceps"),
            Quintuple(2,1, "Curl barra", "curl_barra","imagen_curl_barra"),
            Quintuple(3,1, "Curl concentrado", "curl_concentrado","imagen_curl_concentrado"),
            Quintuple(4,1, "Curl inverso", "curl_inverso","imagen_curl_inverso"),
            Quintuple(5,1, "Curl martillo", "curl_martillo","imagen_curl_martillo"),
            Quintuple(6,1, "Curl predicador", "curl_predicador","imagen_curl_predicador"),
            Quintuple(7,2, "Copa con mancuernas", "copa_con_mancuerna","imagen_copa_con_mancuerna"),
            Quintuple(8,2, "Fondos en paralelas", "fondos_en_paralelas","imagen_fondos_en_paralelas"),
            Quintuple(9,2, "Overhead tricep extension", "overhead_tricep_extension","imagen_overhead_tricep_extension"),
            Quintuple(10,2, "Pullover tricep", "pullover_tricep","imagen_pullover_tricep"),
            Quintuple(11,2, "Pushdown con cuerda", "pushdown_con_cuerda","imagen_pushdown_con_cuerda"),
            Quintuple(12,2, "Skull crushers", "skull_crushers","imagen_skull_crushers"),
            Quintuple(13,2, "Tricep kickbacks", "tricep_kickbacks","imagen_tricep_kickbacks"),
            Quintuple(14,3, "Búlgaras", "bulgaras","imagen_bulgaras"),
            Quintuple(15,3, "Extensiones de cuádriceps", "extensiones_de_cuadriceps","imagen_extensiones_cuadriceps"),
            Quintuple(16,3, "Front squats", "front_squats","imagen_front_squats"),
            Quintuple(17,3, "Hack squat", "hack_squat","imagen_hack_squat"),
            Quintuple(18,3, "Lounges estáticos", "lounges_estaticos","imagen_lounges_estaticos"),
            Quintuple(19,3, "Prensa de piernas", "prensa_de_piernas","imagen_prensa_de_piernas"),
            Quintuple(20,3, "Smith squats", "squats","imagen_smith_squats"),
            Quintuple(21,4, "Dominadas", "dominadas","imagen_dominadas"),
            Quintuple(22,4, "Good mornings", "good_mornings","imagen_good_mornings"),
            Quintuple(23,4, "Jalón al pecho", "jalon_al_pecho","imagen_jalon_al_pecho"),
            Quintuple(24,4, "Pushdown", "pushdown","imagen_pushdowns"),
            Quintuple(25,4, "Remo banco inclinado", "remo_banco_inclinado","imagen_remo_banco_inclinado"),
            Quintuple(26,4, "Remo con barra t", "remo_barra_t","imagen_remo_barra_t"),
            Quintuple(27,4, "Remo unilateral", "remo_unilateral","imagen_remo_unilateral"),
            Quintuple(28,5, "Femoral acostado", "femoral_acostado","imagen_femoral_acostado"),
            Quintuple(29,5, "Femoral sentado", "femoral_sentado","imagen_femoral_sentado"),
            Quintuple(30,5, "Hip thrust", "hip_thrust","imagen_hip_thrust"),
            Quintuple(31,5, "Hiperextensiones", "hiperextensiones","imagen_hiperextensiones"),
            Quintuple(32,5, "Patada unilateral", "patada_unilateral","imagen_patada_unilateral"),
            Quintuple(33,5, "Peso muerto", "peso_muerto","imagen_peso_muerto"),
            Quintuple(34,5, "Prensa con apertura", "prensa_con_apertura","imagen_prensa_apertura"),
            Quintuple(35,6, "Cruce de poleas", "cruce_de_poleas","imagen_cruce_de_poleas"),
            Quintuple(36,6, "Jale abierto inclinado", "jale_abierto_inclinado","imagen_jale_abierto_inclinado"),
            Quintuple(37,6, "Press con barra", "press_con_barra","imagen_press_con_barra"),
            Quintuple(38,6, "Press declinado", "press_declinado","imagen_press_declinado"),
            Quintuple(39,6, "Press en máquina", "press_en_maquina","imagen_press_en_maquina"),
            Quintuple(40,6, "Press plano mancuernas", "press_plano_mancuernas","imagen_press_plano_mancuernas"),
            Quintuple(41,6, "Pushups", "pushups","imagen_pushups")
        )

        db?.beginTransaction()
        try {
            val sql = "INSERT INTO $TABLE_NAME1 ($ID_MUSCULO, $MUSCULO) VALUES (?,?)"
            val statement = db?.compileStatement(sql)
            for ((i,m) in musculos.withIndex()) {
                statement?.clearBindings()
                statement?.bindLong(1,i.toLong()+1)
                statement?.bindString(2,m)
                statement?.executeInsert()
            }

            val sql1 = "INSERT INTO $TABLE_NAME2 ($ID_EJERCICIOS, $ID_MUSCULO, $NOMBRE_EJERCICIO, $GIF_EJERCICIO, $IMAGEN_EJERCICIO) VALUES (?,?,?,?,?)"
            val statement1 = db?.compileStatement(sql1)

            for (e in ejercicios) {
                statement1?.clearBindings()
                statement1?.bindLong(1,e.first.toLong())
                statement1?.bindLong(2,e.second.toLong())
                statement1?.bindString(3,e.third)
                statement1?.bindString(4,e.fourth)
                statement1?.bindString(5,e.fifth)
                statement1?.executeInsert()
            }

            db?.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db?.endTransaction()
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME4")
        onCreate(db)
    }
    fun insertRutina(ejercicios: List<Ejercicio>):Unit {
        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(FECHA, fecha)
        }
        val idRegistro = db.insert(TABLE_NAME3, null, contentValues)
        ejercicios.forEach { ejercicio ->
            val cv = ContentValues().apply {
                put(ID_REGISTRO, idRegistro)
                put(ID_EJERCICIOS, ejercicio.id)
                put(PESO, ejercicio.peso)
                put(TERMINADO, ejercicio.isSelected)
            }
            db.insert(TABLE_NAME4, null, cv)
        }
    }
    fun getHistorial(): List<Registro> {
        val registros = mutableListOf<Registro>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME3 ORDER BY fecha DESC", null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID_REGISTRO))
                val fecha = getString(getColumnIndexOrThrow(FECHA)) // Si guardaste la fecha como String
                registros.add(Registro(id,fecha, mutableListOf()))
            }
        }
        cursor.close()
        registros.forEach{registro ->
            val cursor2 = db.rawQuery("SELECT * FROM $TABLE_NAME4 JOIN $TABLE_NAME2 ON $TABLE_NAME4.$ID_EJERCICIOS = $TABLE_NAME2.$ID_EJERCICIOS WHERE id_registro = ?", arrayOf(registro.id.toString()))
            with(cursor2) {
                while (moveToNext()) {
                    val ejercicio = getString(getColumnIndexOrThrow(NOMBRE_EJERCICIO))
                    val peso = getInt(getColumnIndexOrThrow(PESO))  // Si guardaste la fecha como String
                    registro.ejercicios.add(RegistroEjercicio(ejercicio,peso))
                }
            }
            cursor2.close()
        }
        db.close()
        return registros
    }
     //val nombre = getString(getColumnIndexOrThrow("nombre"))
               // val peso = getInt(getColumnIndexOrThrow("peso"))
}