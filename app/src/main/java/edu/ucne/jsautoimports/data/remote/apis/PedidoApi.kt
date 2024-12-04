package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.PedidoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PedidoApi {
    @POST("api/Pedidos")
    suspend fun addPedido(@Body pedido: PedidoDto): PedidoDto

    @GET("api/Pedidos/{id}")
    suspend fun getPedido(@Path("id") id: Int): PedidoDto

    @GET("api/Pedidos")
    suspend fun getPedidos(): List<PedidoDto>

    @DELETE("api/Pedidos/{id}")
    suspend fun deletePedido(@Path("id") id: Int): Response<Unit>

    @PUT("api/Pedidos/{id}")
    suspend fun updatePedido(@Path("id") id: Int, @Body pedido: PedidoDto): Response<PedidoDto>



}