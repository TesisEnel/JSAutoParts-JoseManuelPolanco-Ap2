package edu.ucne.jsautoimports.data.remote.dto

data class PagoDto(
    val pagoId: Int,
    val pedidoId: Int,
    val tarjetaId: Int,
    val fechaPago: String,
    val monto: Double,
)
