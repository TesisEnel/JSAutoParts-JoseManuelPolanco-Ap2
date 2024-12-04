package edu.ucne.jsautoimports.presentation.categoria

sealed interface CategoriaUiEvent {
    data class SetNombre(val nombre: String) : CategoriaUiEvent
    data class SetNombreError(val nombreError: String) : CategoriaUiEvent
    data class SetDescripcion(val descripcion: String) : CategoriaUiEvent
    data class SetDescripcionError(val descripcionError: String) : CategoriaUiEvent
    data class IsRefresing(val refresing: Boolean) : CategoriaUiEvent
    object Save : CategoriaUiEvent
    object Delete : CategoriaUiEvent
    object Refresh : CategoriaUiEvent


}