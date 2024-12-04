package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.PedidoApi
import edu.ucne.jsautoimports.data.remote.dto.PedidoDto
import javax.inject.Inject

class PedidoRemoteDataSources @Inject constructor(
    private val pedidoApi: PedidoApi
){
    suspend fun addPedido(pedido: PedidoDto) = pedidoApi.addPedido(pedido)
    suspend fun getPedido(id: Int) = pedidoApi.getPedido(id)
    suspend fun getPedidos() = pedidoApi.getPedidos()
    suspend fun deletePedido(id: Int) = pedidoApi.deletePedido(id)
    suspend fun updatePedido(id: Int, pedido: PedidoDto) = pedidoApi.updatePedido(id, pedido)

}