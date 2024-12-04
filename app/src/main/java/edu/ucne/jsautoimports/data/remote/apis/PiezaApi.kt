package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.PiezaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PiezaApi {
    @GET("api/Piezas")
    suspend fun getPiezas(): List<PiezaDto>
    @GET("api/Piezas/{id}")
    suspend fun getPiezaById(@Path("id") id: Int): PiezaDto
    @POST("api/Piezas")
    suspend fun addPieza(@Body pieza: PiezaDto): PiezaDto
    @PUT("api/Piezas/{id}")
    suspend fun updatePieza(id: Int, pieza: PiezaDto): PiezaDto
    @DELETE("api/Piezas/{id}")
    suspend fun deletePieza(id: Int)



}