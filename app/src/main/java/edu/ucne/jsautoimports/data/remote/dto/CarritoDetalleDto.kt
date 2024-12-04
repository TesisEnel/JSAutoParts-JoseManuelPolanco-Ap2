package edu.ucne.jsautoimports.data.remote.dto

data class CarritoDetalleDto(
    val carritoDetalleId: Int? = null,
    val carritoId: Int? = null,
    val piezaId: Int? = null,
    val cantidad: Int? = null,
    val precio: Double,
    val subTotal: Double,
    val itbis: Double

)


