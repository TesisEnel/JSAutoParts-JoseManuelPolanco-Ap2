package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.PedidosDetalleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidosDetalleDao {
    @Upsert
    suspend fun save(pedidosDetalleEntity: PedidosDetalleEntity)
    @Query("""
        SELECT * FROM PedidosDetalle
        WHERE PedidosDetalleId = :pedidosDetalleId
        LIMIT 1
    """)
    suspend fun getPedidoD(pedidosDetalleId: Int): PedidosDetalleEntity?
    @Delete
    suspend fun deletePedidoD(pedidosDetalleEntity: PedidosDetalleEntity)
    @Query("SELECT * FROM PedidosDetalle")
    fun getPedidosD(): Flow<List<PedidosDetalleEntity>>
}