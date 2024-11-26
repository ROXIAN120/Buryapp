package com.example.filtrador_buscador_kotlin

// Clase de datos que representa a un usuario
data class Usuario(
    var nombre: String,   // Nombre del usuario
    var telefono: String, // Tipo de local, "bar" , "Resto bar" o "Discoteca"
    var Imagen: Int      // Referencia al recurso de imagen del usuario
)
