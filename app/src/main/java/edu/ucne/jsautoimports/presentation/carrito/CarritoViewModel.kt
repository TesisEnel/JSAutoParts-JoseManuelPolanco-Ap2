package edu.ucne.jsautopartsprueba.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.CarritoDetalleDto
import edu.ucne.jsautoimports.data.remote.dto.CarritoDto
import edu.ucne.jsautoimports.data.repository.CarritoRepository
import edu.ucne.jsautoimports.presentation.carrito.CarritoUiState
import edu.ucne.jsautopartsprueba.presentation.carrito.CarritoUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  CarritoViewModel @Inject constructor(
    private val repository: CarritoRepository,

    ): ViewModel(){

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    init {
        getCarritos()
    }

    private fun getCarritos()
    {
        viewModelScope.launch {
            repository.getCarritoos().collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                carritos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }

                    is Resources.Error -> TODO()
                    is Resources.Loading -> TODO()
                    is Resources.Success -> TODO()
                }
            }
        }
    }

    fun onUiEvent(event: CarritoUiEvent) {
        when (event) {
            CarritoUiEvent.Save -> saveCar()
            CarritoUiEvent.Delete -> deleteCar()

            is CarritoUiEvent.IsRefreshingChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(isRefreshing = event.isRefreshing)
                }
            }

            is CarritoUiEvent.AddToCart -> addToCart(event.detalle)

            CarritoUiEvent.Refresh -> {
                getCarritos()
            }
        }
    }


    fun addToCart(detalle: CarritoDetalleEntity) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedList = currentState.carritoDetalle + detalle
                Log.d("CarritoViewModel", "Carrito actualizado: $updatedList")
                currentState.copy(carritoDetalle = updatedList)
            }
        }
    }



    private fun saveCar(){
        viewModelScope.launch {
            val carritoDto = CarritoDto(
                carritoId = _uiState.value.CarritoId ?: 0,
                fechaCreacion = _uiState.value.fechaCreacion,
                carritoDetalle = _uiState.value.carritoDetalle.map { it.toDto() },
                usuarioId = TODO(),
                pagado = TODO()
            )
            repository.addCarrito(carritoDto)
            _uiState.update { it.copy(success = true) }
        }
    }

    private fun deleteCar() {
        viewModelScope.launch {
            _uiState.value.CarritoId?.let{carritoId ->
                repository.deleteCarrito(carritoId)
                _uiState.update { it.copy(success = true) }

            }
        }
    }

    private fun CarritoDetalleEntity.toDto() = CarritoDetalleDto(
        carritoDetalleId = carritoDetalleId,
        carritoId = carritoId,
        piezaId = piezaId,
        cantidad = cantidad,
        precio = precio,
        subTotal = subTotal,
        itbis = itbis
    )

}