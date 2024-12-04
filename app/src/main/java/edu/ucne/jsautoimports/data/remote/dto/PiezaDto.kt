package edu.ucne.jsautoimports.data.remote.dto

data class PiezaDto(
    val piezaId: Int,
    val nombre: String,
    val categoriaId: Int,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    val cantidadDisponible: Int,
    val impuesto: Double,
)





