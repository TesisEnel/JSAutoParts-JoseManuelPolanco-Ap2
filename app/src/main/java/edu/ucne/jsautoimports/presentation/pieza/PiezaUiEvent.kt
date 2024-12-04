package edu.ucne.jsautoimports.presentation.pieza

sealed interface PiezaUiEvent {
    data class PiezaIdChange(val piezaId: Int): PiezaUiEvent
    data class NombreChange(val nombre: String): PiezaUiEvent
    data class CategoriaIdChange(val categoriaId: Int): PiezaUiEvent
    data class DescripcionChange(val descripcion: String): PiezaUiEvent
    data class PrecioChange(val precio: Double): PiezaUiEvent
    data class CantidadDisponibleChange(val cantidadDisponible: Int): PiezaUiEvent
    data class ImagenChange(val imagen: String): PiezaUiEvent
    data class ImpuestoChange(val impuesto: Double): PiezaUiEvent
    data class SelectedPiezaChange(val piezaId: Int): PiezaUiEvent
    data object Save: PiezaUiEvent
    data object Delete: PiezaUiEvent
    data object Refresh: PiezaUiEvent
    data class IsRefresingChange(val isrefresing: Boolean): PiezaUiEvent
    data object Nuevo: PiezaUiEvent


}