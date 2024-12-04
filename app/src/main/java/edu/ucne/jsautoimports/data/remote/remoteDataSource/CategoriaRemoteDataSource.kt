package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.CategoriaApi
import edu.ucne.jsautoimports.data.remote.dto.CategoriaDto
import javax.inject.Inject

class CategoriaRemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaApi

){
    suspend fun getCategorias() = categoriaApi.getCatagorias()
    suspend fun getCategoriaById(id: Int) = categoriaApi.getCategoriaById(id)
    suspend fun postCategoria(categoria: CategoriaDto) = categoriaApi.postCategoria(categoria)
    suspend fun putCategoria(id: Int, categoria: CategoriaDto) = categoriaApi.putCategoria(id, categoria)
    suspend fun deleteCategoria(id: Int) = categoriaApi.deleteCategoria(id)

}