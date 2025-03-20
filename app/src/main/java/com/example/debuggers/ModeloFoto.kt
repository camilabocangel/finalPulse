package com.example.debuggers

import java.util.Calendar

class ModeloFoto {
    var id: String? = null
    var fotoUri: String? = null
    var tiempo: String? = null
    var fechaPublicacion: String = ""
    var fechaEnCalendario: Calendar? = null

    constructor()

    constructor(id: String?, fotoUri: String?, tiempo: String?, fechaPublicacion: String, fechaEnCalendario: Calendar?) {
        this.id = id
        this.fotoUri = fotoUri
        this.tiempo = tiempo
        this.fechaPublicacion = fechaPublicacion
        this.fechaEnCalendario = fechaEnCalendario
    }
}