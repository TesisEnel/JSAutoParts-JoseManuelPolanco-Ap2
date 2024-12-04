package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PedidosDetalle")
data class PedidosDetalleEntity(
    @PrimaryKey
    val pedidosDetalleId: Int? = null,
    val pedidoId: Int,
    val piezaId: Int,
    val cantidad: Int,
    val precio: Double,
    val subTotal: Double,
    val itbis: Double
)
