package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.CarritoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Upsert
    suspend fun save(carritoEntity: CarritoEntity)
    @Query("""
        SELECT * FROM Carritos
        WHERE CarritoId = :carritoid
        LIMIT 1
    """)
    suspend fun getCarrito(carritoid: Int): CarritoEntity?
    @Delete
    suspend fun deleteCarrito(carritoEntity: CarritoEntity)
    @Query("SELECT * FROM Carritos")
    fun getCarritos(): Flow<List<CarritoEntity>>

    @Query("""
        SELECT * FROM carritos
        WHERE pagado = 0
        ORDER BY carritoId DESC LIMIT 1
    """)
    suspend fun getLastCarrito(): CarritoEntity?

    @Query(
        """
            SELECT *
            FROM carritos
            WHERE pagado = 0 AND usuarioId = :usuarioId 
            ORDER BY carritoId DESC 
            LIMIT 1
        """
    )
    suspend fun getLastCarritoByUsuario(usuarioId: Int): CarritoEntity?

    @Query("SELECT * FROM carritos WHERE usuarioId = :usuarioId")
    fun getCarritosPorUsuario(usuarioId: Int): List<CarritoEntity>

    @Query("SELECT * FROM CarritoDetalle WHERE carritoId IN (SELECT carritoId FROM carritos WHERE usuarioId = :usuarioId)")
    fun getCarritoDetallesPorUsuario(usuarioId: Int): List<CarritoDetalleEntity>

    @Query(
        """
           SELECT *
            FROM carritos
            WHERE usuarioId in (SELECT usuarioId FROM usuarios WHERE email like :email)
            ORDER BY carritoId DESC 
            LIMIT 1;
        """
    )
    fun getLastCarritoByEmail(email: String): Flow<CarritoEntity>

    @Query("SELECT * FROM carritos WHERE usuarioId = :usuarioId AND pagado = 0 LIMIT 1")
    suspend fun getCarritoNoPagadoPorUsuario(usuarioId: Int): CarritoEntity?

    @Query("SELECT * FROM CarritoDetalle WHERE carritoId = :carritoId")
    fun getCarritoDetallesPorCarritoId(carritoId: Int): Flow<List<CarritoDetalleEntity>>



}