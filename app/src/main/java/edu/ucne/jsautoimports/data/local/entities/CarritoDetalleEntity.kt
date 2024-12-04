package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CarritoDetalle")
data class CarritoDetalleEntity(
    @PrimaryKey
    val carritoDetalleId: Int? = null,
    val carritoId: Int?= null,
    val piezaId: Int?= null,
    val cantidad: Int?= null,
    val precio: Double,
    val subTotal: Double,
    val itbis: Double
)
