package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.jsautoimports.data.remote.dto.CarritoDetalleDto


@Entity(tableName = "Carritos")
data class CarritoEntity(
    @PrimaryKey
    val carritoId: Int? = null,
    val usuarioId: Int,
    val pagado: Boolean = false,
    val fechaCreacion: String,
    val carritoDetalle: List<CarritoDetalleDto>
)
