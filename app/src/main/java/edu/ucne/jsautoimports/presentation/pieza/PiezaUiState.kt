package edu.ucne.jsautoimports.presentation.pieza

import edu.ucne.jsautoimports.data.local.entities.CategoriaEntity
import edu.ucne.jsautoimports.data.local.entities.PiezaEntity

data class PiezaUiState(
    val piezaId: Int? = null,
    val nombre: String? = "",
    val categoriaId: Int? = null,
    val descripcion: String? = "",
    val precio: Double? = null,
    val cantidadDisponible: Int? = null,
    val imagen: String? = "",
    val impuesto: Double? = null,
    val nombreError: String? = "",
    val imagenError: String? = "",
    val errorLoad: String = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val piezas: List<PiezaEntity> = emptyList(),
    val categorias: List<CategoriaEntity> = emptyList(),
    val refresing: Boolean = false
)
