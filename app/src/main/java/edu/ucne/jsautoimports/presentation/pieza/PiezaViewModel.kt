package edu.ucne.jsautoimports.presentation.pieza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.PiezaDto
import edu.ucne.jsautoimports.data.repository.CategoriaRepository
import edu.ucne.jsautoimports.data.repository.PiezaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PiezaViewModel @Inject constructor(
    private val piezaRepository: PiezaRepository,
    private val categoriaRepository: CategoriaRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(PiezaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPiezas()
        getCategorias()
    }

    private fun getPiezas(){
        viewModelScope.launch {
            piezaRepository.getPiezas().collectLatest { result ->
                when(result){
                    is Resources.Loading ->{
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resources.Success ->{
                        _uiState.update {
                            it.copy(
                                piezas = result.data ?: emptyList(),
                                isLoading = false
                            )

                        }
                    }
                    is Resources.Error ->{
                        _uiState.update {
                            it.copy(
                                piezas = result.data ?: emptyList(),
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategorias() {
        viewModelScope.launch {
            categoriaRepository.getCategorias().collectLatest { result ->
                when (result) {
                    is Resources.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                categorias = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                categorias = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onPiezaEvent(event: PiezaUiEvent){
        when(event){
            is PiezaUiEvent.PiezaIdChange ->{
                _uiState.update { it.copy(piezaId = event.piezaId) }
            }
            is PiezaUiEvent.NombreChange ->{
                _uiState.update { it.copy(nombre = event.nombre, nombreError = "") }
            }
            is PiezaUiEvent.CategoriaIdChange ->{
                _uiState.update { it.copy(categoriaId = event.categoriaId) }
            }
            is PiezaUiEvent.DescripcionChange ->{
                _uiState.update { it.copy(descripcion = event.descripcion) }
            }
            is PiezaUiEvent.PrecioChange ->{
                _uiState.update { it.copy(precio = event.precio) }
            }
            is PiezaUiEvent.CantidadDisponibleChange ->{
                _uiState.update { it.copy(cantidadDisponible = event.cantidadDisponible) }

            }
            is PiezaUiEvent.ImagenChange ->{
                _uiState.update { it.copy(imagen = event.imagen) }
            }
            is PiezaUiEvent.ImpuestoChange ->{
                _uiState.update { it.copy(impuesto = event.impuesto) }
            }
            is PiezaUiEvent.IsRefresingChange ->{
                _uiState.update { it.copy(refresing = event.isrefresing) }
            }
            is PiezaUiEvent.Nuevo ->{
                _uiState.value = PiezaUiState()
            }
            PiezaUiEvent.Refresh ->{
                getPiezas()
                getCategorias()
            }
            is PiezaUiEvent.SelectedPiezaChange -> {
                viewModelScope.launch {
                    if (event.piezaId > 0){
                        val pieza = piezaRepository.getProducto(event.piezaId)

                        _uiState.update {
                            it.copy(
                                piezaId = pieza?.piezaId,
                                nombre = pieza?.nombre,
                                categoriaId = pieza?.categoriaId,
                                descripcion = pieza?.descripcion,
                                precio = pieza?.precio,
                                cantidadDisponible = pieza?.cantidadDisponible,
                                imagen = pieza?.imagen,
                                impuesto = pieza?.impuesto
                            )
                        }
                    }
                }
            }
            PiezaUiEvent.Save ->{
                viewModelScope.launch {
                    val nombreSearched = _uiState.value.piezas
                        .find { it.nombre.lowercase() == uiState.value.nombre?.lowercase() }

                    if (_uiState.value.nombre?.isBlank() == true){
                        _uiState.update {
                            it.copy(nombreError = "El Nombre no puede estar vacio")
                        }
                    }else if(nombreSearched != null && nombreSearched.piezaId != _uiState.value.piezaId){
                        _uiState.update {
                            it.copy(nombreError = "Nombre ya existe")
                        }
                    }

                    if(_uiState.value.imagen.isNullOrBlank()){
                        _uiState.update { it.copy(imagenError = "Debe seleccionar una imagen") }
                    }

                    //Si no hay errores, se guarda el producto
                    if(_uiState.value.nombreError!!.isEmpty() &&  _uiState.value.imagenError.isNullOrEmpty()){
                        if (_uiState.value.piezaId == null)
                            piezaRepository.addPieza(_uiState.value.toEntity())
                        else
                            piezaRepository.updatePieza(
                                _uiState.value.piezaId ?: 0,
                                _uiState.value.toEntity()
                            )

                        _uiState.update { it.copy(success = true) }

                        }
                    }
                }
            PiezaUiEvent.Delete ->{
                viewModelScope.launch {
                    piezaRepository.deletePieza(_uiState.value.piezaId ?: 0)
                }
            }
        }
    }


    fun PiezaUiState.toEntity() =PiezaDto(
        piezaId = piezaId ?: 0,
        nombre = nombre ?: "",
        categoriaId = categoriaId ?: 0,
        descripcion = descripcion ?: "",
        precio = precio ?: 0.0,
        cantidadDisponible = cantidadDisponible ?: 0,
        imagen = imagen ?: "",
        impuesto = impuesto ?: 0.0
    )
}