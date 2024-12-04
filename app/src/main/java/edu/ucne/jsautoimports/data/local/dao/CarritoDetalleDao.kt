package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDetalleDao {

    @Upsert
    suspend fun save(carritoDetalle: CarritoDetalleEntity)

    @Delete
    suspend fun deleteCarritoDetalle(carritoDetalle: CarritoDetalleEntity)

    @Query(
        """
            SELECT * FROM CarritoDetalle
            WHERE carritoId=:carritoId
        """
    )
    fun getCarritoDetalles(carritoId: Int): Flow<List<CarritoDetalleEntity>> // Elimina 'suspend'

    @Query("DELETE FROM CarritoDetalle WHERE carritoId = :carritoId")
    suspend fun clearCarrito(carritoId: Int)

    @Query(
        """
            SELECT * FROM CarritoDetalle
            WHERE piezaId=:id and carritoId=:idCarrito
            LIMIT 1
        """
    )
    suspend fun getCarritoDetalleByPiezaId(id: Int, idCarrito: Int): CarritoDetalleEntity?

    @Query(
        """
            SELECT EXISTS 
                (SELECT 1 
                 FROM carritoDetalle 
                 WHERE piezaId = :productoId AND carritoId = :carritoId)
        """
    )
    suspend fun carritoDetalleExit(productoId: Int, carritoId: Int): Boolean
}
