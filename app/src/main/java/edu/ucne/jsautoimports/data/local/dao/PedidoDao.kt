package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.PedidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Upsert
    suspend fun save(pedidoEntity: PedidoEntity)
    @Query("""
        SELECT * FROM Pedidos
        WHERE PedidoId = :pedidoId
        LIMIT 1
    """)
    suspend fun getPedido(pedidoId: Int): PedidoEntity?
    @Delete
    suspend fun deletePedido(pedidoEntity:PedidoEntity)
    @Query("SELECT * FROM Pedidos")
    fun getPedidos(): Flow<List<PedidoEntity>>
}