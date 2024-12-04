package edu.ucne.jsautopartsprueba.presentation.carrito

import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity

interface CarritoUiEvent {
    data class IsRefreshingChanged(val isRefreshing: Boolean): CarritoUiEvent
    data class AddToCart(val detalle: CarritoDetalleEntity) : CarritoUiEvent
    object Save: CarritoUiEvent
    object Delete: CarritoUiEvent
    data object Refresh: CarritoUiEvent

}