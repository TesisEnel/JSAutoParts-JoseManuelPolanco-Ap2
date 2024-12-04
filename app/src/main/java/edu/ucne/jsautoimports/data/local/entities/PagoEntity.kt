package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pago")
data class PagoEntity(
    @PrimaryKey
    val pagoId: Int,
    val pedidoId: Int,
    val tarjetaId: Int,
    val fechaPago: String,
    val monto: Double,
)
