package edu.ucne.jsautoimports.presentation.categoria

import edu.ucne.jsautoimports.data.local.entities.CategoriaEntity

data class CategoriaUiState(
    val categoriaId: Int? = null,
    val nombre: String = "",
    val imagen: String = "",
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val errorMessage: String? = null,
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val categorias: List<CategoriaEntity> = emptyList(),
    val refresing: Boolean = false

)