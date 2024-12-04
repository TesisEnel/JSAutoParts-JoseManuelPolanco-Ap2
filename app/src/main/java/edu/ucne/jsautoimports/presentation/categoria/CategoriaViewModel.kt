package edu.ucne.jsautopartsprueba.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.CategoriaDto
import edu.ucne.jsautoimports.data.repository.CategoriaRepository
import edu.ucne.jsautoimports.presentation.categoria.CategoriaUiEvent
import edu.ucne.jsautoimports.presentation.categoria.CategoriaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val categoriaRepository: CategoriaRepository

): ViewModel() {
    private val _uiState = MutableStateFlow(CategoriaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCategorias()
    }

    private fun getCategorias(){
        viewModelScope.launch {
            categoriaRepository.getCategorias().collect {result ->
                when(result){
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
                                errorMessage = result.message ?: "Error desconocido",
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

    fun onUiEvent(event: CategoriaUiEvent){
        when (event){
            CategoriaUiEvent.Save ->{
                if (Validar()) {
                    Save()
                    _uiState.update { it.copy(success = true) }
                }
            }
            CategoriaUiEvent.Refresh -> getCategorias()
            CategoriaUiEvent.Delete -> delete()
            is CategoriaUiEvent.SetNombre -> _uiState.update {
                it.copy(nombre = event.nombre, nombreError = "")
            }
            is CategoriaUiEvent.SetDescripcion -> _uiState.update {
                it.copy(imagen = event.descripcion, descripcionError = "")
            }
            is CategoriaUiEvent.SetNombreError -> _uiState.update {
                it.copy(nombreError = event.nombreError)
            }
            is CategoriaUiEvent.SetDescripcionError -> _uiState.update {
                it.copy(descripcionError = event.descripcionError)
            }
            is CategoriaUiEvent.IsRefresing -> _uiState.update {
                it.copy(refresing = event.refresing)
            }
        }
    }

    private fun Save(){
        viewModelScope.launch {
            if (_uiState.value.categoriaId == null){
                categoriaRepository.addCategoria(_uiState.value.toEntity())
            } else{
                categoriaRepository.updateCategoria(_uiState.value.categoriaId ?: 0, _uiState.value.toEntity())
            }
        }
    }

    private fun delete(){
        viewModelScope.launch {
            _uiState.value.categoriaId?.let {
                categoriaRepository.deleteCategoria(it)
                _uiState.update { it.copy(success = true, errorMessage = null) }
            }
        }
    }

    private fun Validar(): Boolean {
        var isValid = true
        _uiState.update {
            it.copy(
                nombreError = if(it.nombre.isBlank()){
                    isValid = false
                    "El Nombre no puede estar vacio"
                } else null,
                descripcionError = if(it.imagen.isBlank()){
                    isValid = false
                    "La Descripcion no puede estar vacio"
                } else null
            )
        }
        return isValid
    }

    fun CategoriaUiState.toEntity() = CategoriaDto(
        categoriaId = categoriaId,
        nombre = nombre,
        imagen = imagen
    )

}










