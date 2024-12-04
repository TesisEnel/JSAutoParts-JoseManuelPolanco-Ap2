package edu.ucne.jsautoimports.data.remote.dto

data class PedidosDetalleDto(
    val pedidosDetalleId: Int? = null,
    val pedidoId: Int,
    val piezaId: Int,
    val cantidad: Int,
    val precio: Double,
    val subTotal: Double,
    val itbis: Double
)




