package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.CarritoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface CarritoApi {
    @GET("api/Carritos")
    suspend fun getCarritos(): List<CarritoDto>
    @GET("api/Carritos/{id}")
    suspend fun getCarritoById(@Path("id") id: Int): CarritoDto
    @DELETE("api/Carritos/{id}")
    suspend fun deleteCarrito(@Path("id") id: Int)
    @POST("api/Carritos")
    suspend fun postCarrito(@Body carrito: CarritoDto?): CarritoDto



}