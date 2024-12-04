package edu.ucne.jsautoimports.data.remote.dto

import edu.ucne.jsautoimports.data.local.entities.PedidosDetalleEntity
import java.util.Date


data class PedidoDto(
    val pedidoId: Int? = null,
    val usuarioId: Int,
    val fechaPedido: Date,
    val estado: String,
    val total: Double,
    val pedidosDetalle: List<PedidosDetalleEntity>

)

