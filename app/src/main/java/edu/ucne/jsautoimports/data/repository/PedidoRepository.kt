package edu.ucne.jsautoimports.data.repository

import edu.ucne.jsautoimports.data.local.dao.PedidoDao
import edu.ucne.jsautoimports.data.local.entities.PedidoEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.PedidoDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.PedidoRemoteDataSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoDao: PedidoDao,
    private val pedidoRemoteDataSource: PedidoRemoteDataSources
){
    suspend fun addPedido(pedido: PedidoDto) = pedidoRemoteDataSource.addPedido(pedido)
    suspend fun getPedido(id: Int) = pedidoRemoteDataSource.getPedido(id)
    suspend fun deletePedido(id: Int) = pedidoRemoteDataSource.deletePedido(id)
    suspend fun updatePedido(id: Int, pedido: PedidoDto) = pedidoRemoteDataSource.updatePedido(id, pedido)


    fun getPedidos(): Flow<Resources<List<PedidoEntity>>> = flow{
        try{
            emit(Resources.Loading())
            val pedidos = pedidoRemoteDataSource.getPedidos()

            pedidos.forEach{
                pedidoDao.save(
                    it.toEntity()
                )
            }

            pedidoDao.getPedidos().collect{localPedidos ->
                emit(Resources.Success(localPedidos))
            }


        }catch (e: HttpException){
            pedidoDao.getPedidos().collect{localPedidos ->
                emit(Resources.Success(localPedidos))

            }

            emit(Resources.Error("Error HTTP DESCONOCIDO ${e.message}"))

        }catch (e: Exception){
            pedidoDao.getPedidos().collect { localPedidos ->
                emit(Resources.Success(localPedidos))
            }
            emit(Resources.Error("Error DESCONOCIDO ${e.message}"))
        }
    }
}

private fun PedidoDto.toEntity() = PedidoEntity(
    pedidoId = pedidoId,
    usuarioId = usuarioId,
    total = total,
    pedidosDetalle = pedidosDetalle,
    estado = estado,
    fechaPedido = fechaPedido
)
