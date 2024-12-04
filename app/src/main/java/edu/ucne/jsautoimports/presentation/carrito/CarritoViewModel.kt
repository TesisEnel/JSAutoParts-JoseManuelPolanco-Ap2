package edu.ucne.jsautoimports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.CarritoEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.CarritoDetalleDto
import edu.ucne.jsautoimports.data.remote.dto.CarritoDto
import edu.ucne.jsautoimports.data.repository.AuthRepository
import edu.ucne.jsautoimports.data.repository.CarritoRepository
import edu.ucne.jsautoimports.data.repository.PiezaRepository
import edu.ucne.jsautoimports.data.repository.UsuarioRepository
import edu.ucne.jsautoimports.presentation.carrito.CarritoUiEvent
import edu.ucne.jsautoimports.presentation.carrito.CarritoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  CarritoViewModel @Inject constructor(
    private val repository: CarritoRepository,
    private val piezaRepository: PiezaRepository,
    private val usuarioRepository: UsuarioRepository,
    private val authRepository: AuthRepository

): ViewModel(){

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    init {
        ObtenerDatos()
    }

    private fun ObtenerDatos(){
        viewModelScope.launch {
            val currentUserEmail = authRepository.getUser()
            val user = usuarioRepository.getUsuarioByCorreo(currentUserEmail ?: "")
            if (user != null){
                _uiState.update {
                    it.copy(usuarioId = user.usuarioId ?: 0 )
                }
                cargar(user.usuarioId ?:0)
            }else{
                _uiState.update {
                    it.copy(error = "No se encontro el usuario")
                }
            }
        }
    }
    private fun cargar(usuarioId: Int){
        getCarritos(usuarioId)
        getCarritoDetalles(usuarioId)
    }

    private fun getCarritos(usuarioId: Int)
    {
        viewModelScope.launch {
            repository.getCarritosPorUsuario(usuarioId).collect { result ->
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
                }
            }
        }
    }

    private fun getCarritoDetalles(usuarioId: Int){
        viewModelScope.launch {
            val carrito = repository.getLastCarritobyPersona(usuarioId)
            if (carrito != null){
                repository.getCarritoDetallesPorCarritoId(carrito.carritoId!!).collect{detalle ->
                    _uiState.update {
                        it.copy(carritoDetalle = detalle.toMutableList())
                    }
                    calcular()
                }
            }else(
                _uiState.update {
                    it.copy(error = "Nose encontro el carrito")
                }
            )
        }
    }

    private suspend fun  agregarProducto(carritodetalle: CarritoDetalleEntity, cantidad: Int){
        var carritoAnterior = repository.getCarritoNoPagadoPorUsuario(_uiState.value.usuarioId)
        if (carritoAnterior == null){
            repository.saveCarrito(
                CarritoEntity(
                    usuarioId = _uiState.value.usuarioId,
                    fechaCreacion = System.currentTimeMillis().toString(),
                    pagado = false,
                    carritoDetalle = mutableListOf()

                )
            )
            carritoAnterior = repository.getCarritoNoPagadoPorUsuario(_uiState.value.usuarioId)

        }
        val existeCarrito = repository.CarritoExiste(
            carritodetalle.piezaId ?: 0,
            carritoAnterior?.carritoId ?:0
        )
        val pieza = piezaRepository.getProducto(carritodetalle.piezaId ?: 0)

        if (existeCarrito.equals(false)){
            repository.addCarritoDetalle(
                CarritoDetalleEntity(
                    carritoId = carritoAnterior?.carritoId ?: 0,
                    piezaId = carritodetalle.piezaId ?: 0,
                    cantidad = cantidad,
                    precio = pieza?.precio ?: 0.0
                ,
                    subTotal = (pieza?.precio ?: 0.0) * cantidad,
                    itbis = ((pieza?.precio ?: 0.0 ) * cantidad) * 0.12

                )

            )
        }else{
            val carritoDetallex2 = repository.getCarritoDetallesByPiezaId(
                carritodetalle.piezaId ?: 0,
                carritoAnterior?.carritoId ?: 0

            )
            val nuevaCantidad = (carritoDetallex2?.cantidad ?: 0) + (carritodetalle.cantidad ?: 0)

            repository.addCarritoDetalle(
                CarritoDetalleEntity(
                    carritoDetalleId = carritoDetallex2?.carritoDetalleId,
                    carritoId = carritoAnterior?.carritoId ?: 0,
                    piezaId = carritodetalle.piezaId ?: 0,
                    cantidad = nuevaCantidad,
                    precio = pieza?.precio ?: 0.0,
                    itbis = 0.0,
                    subTotal = 0.0,


                )
            )
        }
    }

    private fun limpiarCarrito(){
        _uiState.update {
            it.copy(
                carritoDetalle = mutableListOf(),
                subTotal = 0.0,
                total = 0.0,
                impuesto = 0.0,


            )
        }
    }

    suspend fun onUiEvent(event: CarritoUiEvent) {
        when (event) {
            CarritoUiEvent.SaveCarritos -> {
                saveCar()
            }
            CarritoUiEvent.DeleteCarritos -> {
                deleteCar()
            }

            is CarritoUiEvent.IsRefreshingChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(isRefreshing = event.isRefreshing)
                }
            }

            is CarritoUiEvent.Refresh -> {

                getCarritos(_uiState.value.usuarioId)
            }

            is CarritoUiEvent.AgregarPieza -> agregarProducto(event.pieza, event.cantidad)
            is CarritoUiEvent.EliminarPieza -> deleteCar()
            CarritoUiEvent.LimpiarCarrito -> limpiarCarrito()
            CarritoUiEvent.cargarCarritoDetalles -> TODO()
            CarritoUiEvent.LoadCarritos -> TODO()

        }
    }


    private fun saveCar(){
        viewModelScope.launch {
            try{
                val carritoDto = CarritoDto(
                    carritoId = _uiState.value.CarritoId ?: 0,
                    usuarioId = _uiState.value.usuarioId,
                    pagado = _uiState.value.pagado,
                    fechaCreacion = _uiState.value.fechaCreacion,
                    carritoDetalle = _uiState.value.carritoDetalle.map { it.toDto() }

                )
                repository.addCarrito(carritoDto)
                _uiState.update { it.copy(success = true) }
                limpiarCarrito()
            }catch (e: Exception){
                _uiState.update { it.copy(error = "Error al guardar el carrito ${e.localizedMessage}" ) }
            }
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

    private fun  calcular(){
        val total = _uiState.value.carritoDetalle.sumOf { it.subTotal ?: 0.0 }
        val impuesto = total * 0.12

        _uiState.update {
            it.copy(
                subTotal = total,
                impuesto = impuesto,
                total = total + impuesto
            )
        }
    }

}