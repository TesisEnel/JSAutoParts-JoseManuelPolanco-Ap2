package edu.ucne.jsautoimports.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey
    val categoriaId: Int? = 0,
    val nombre: String,
    val imagen: String,
)
