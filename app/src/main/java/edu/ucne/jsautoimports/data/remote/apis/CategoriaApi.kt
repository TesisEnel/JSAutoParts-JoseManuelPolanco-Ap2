package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.CategoriaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoriaApi {
@GET("api/Categorias")
suspend fun getCatagorias(): List<CategoriaDto>
@GET("api/Categorias/{id}")
suspend fun getCategoriaById(@Path("id") id: Int): CategoriaDto
@POST("api/Categorias/{id}")
suspend fun postCategoria(@Body categoria: CategoriaDto?): CategoriaDto?
@POST("api/Categorias")
suspend fun putCategoria(@Path("id") id: Int, @Body categoria: CategoriaDto): CategoriaDto
@DELETE("api/Categorias/{id}")
suspend fun deleteCategoria(@Path("id") id: Int)




}