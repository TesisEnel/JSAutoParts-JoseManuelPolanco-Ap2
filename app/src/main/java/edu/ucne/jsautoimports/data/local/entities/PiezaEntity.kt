package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pieza")
data class PiezaEntity (
    @PrimaryKey
    val piezaId: Int,
    val nombre: String,
    val categoriaId: Int?,
    val descripcion: String,
    val imagen: String,
    val precio: Double,
    val cantidadDisponible: Int,
    val impuesto: Double,
)
