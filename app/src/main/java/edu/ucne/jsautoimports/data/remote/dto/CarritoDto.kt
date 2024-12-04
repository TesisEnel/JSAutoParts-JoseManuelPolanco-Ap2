package edu.ucne.jsautoimports.data.remote.dto

data class CarritoDto(
    val carritoId: Int,
    val usuarioId: Int,
    val pagado: Boolean,
    val fechaCreacion: String,
    val carritoDetalle: List<CarritoDetalleDto>
)

