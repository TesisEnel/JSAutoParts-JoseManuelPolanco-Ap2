package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.PagoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PagoApi {
    @POST("api/Pago")
    suspend fun addPago(@Body pago: PagoDto): PagoDto

    @GET("api/Pago/{id}")
    suspend fun getPago(@Path("id") id: Int): PagoDto

    @GET("api/Pago")
    suspend fun getPagos(): List<PagoDto>

    @PUT("api/Pago/{id}")
    suspend fun updatePago(@Path("id") id: Int, @Body pago: PagoDto): PagoDto

    @DELETE("api/Pago/{id}")
    suspend fun deletePago(@Path("id") id: Int)



}