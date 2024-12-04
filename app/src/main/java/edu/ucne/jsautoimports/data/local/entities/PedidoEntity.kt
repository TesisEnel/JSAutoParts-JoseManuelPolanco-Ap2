package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Pedidos",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["usuarioId"],
            childColumns = ["usuarioId"]
        )
    ], indices = [Index(value = ["usuarioId"])]
    )
data class PedidoEntity(
    @PrimaryKey
    val pedidoId: Int? = null,
    val usuarioId: Int,
    val estado: String,
    val fechaPedido: Date,
    val total: Double,
    val pedidosDetalle: List<PedidosDetalleEntity>
)
