package edu.ucne.jsautoimports.presentation.carrito

import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity

sealed class CarritoUiEvent {
    object cargarCarritoDetalles: CarritoUiEvent()
    object LoadCarritos : CarritoUiEvent()
    object SaveCarritos: CarritoUiEvent()
    object DeleteCarritos: CarritoUiEvent()
    object LimpiarCarrito: CarritoUiEvent()
    data class AgregarPieza(val pieza: CarritoDetalleEntity, val cantidad: Int) : CarritoUiEvent()
    data class EliminarPieza(val piezaId: Int): CarritoUiEvent()
    data class IsRefreshingChanged(val isRefreshing: Boolean) : CarritoUiEvent()
    object Refresh : CarritoUiEvent()



}