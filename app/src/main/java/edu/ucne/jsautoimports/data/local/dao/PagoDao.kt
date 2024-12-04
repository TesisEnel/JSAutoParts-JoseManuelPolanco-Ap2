package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.PagoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PagoDao {
    @Upsert
    suspend fun save(pagoEntity: PagoEntity)
    @Query("""
        SELECT * FROM Pago
        WHERE PagoId = :pagoId
        LIMIT 1
    """)
    suspend fun getPago(pagoId: Int): PagoEntity?

    @Delete
    suspend fun deletePago(pagoEntity: PagoEntity)

    @Query("SELECT * FROM Pago")
    fun getPagos(): Flow<List<PagoEntity>>


}