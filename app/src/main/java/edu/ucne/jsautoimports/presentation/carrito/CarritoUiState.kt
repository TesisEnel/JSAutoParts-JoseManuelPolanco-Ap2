package edu.ucne.jsautoimports.presentation.carrito

import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.CarritoEntity

data class CarritoUiState(
    val CarritoId: Int? = null,
    val usuarioId: Int = 1,
    val error: String? = null,
    val precio: Double = 0.0,
    val fechaCreacion: String = "",
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val subTotal: Double = 0.0,
    val pagado: Boolean = false,
    val impuesto: Double = 0.0,
    val total: Double = 0.0,
    val carritos: List<CarritoEntity> = emptyList(),
    val carritoDetalle: List<CarritoDetalleEntity> = emptyList()
)
