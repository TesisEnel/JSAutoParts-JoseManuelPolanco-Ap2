package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.PiezaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PiezaDao {
    @Upsert
    suspend fun save(categoriaEntity: PiezaEntity)
    @Query("""
        SELECT * FROM Pieza
        WHERE PiezaId = :piezaId
        LIMIT 1
    """)
    suspend fun getPieza(piezaId: Int): PiezaEntity?
    @Delete
    suspend fun deletePieza(categoriaEntity: PiezaEntity)
    @Query("SELECT * FROM Pieza")
     fun getPiezas(): Flow<List<PiezaEntity>>

}