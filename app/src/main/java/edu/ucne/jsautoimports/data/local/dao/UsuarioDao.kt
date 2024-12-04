package edu.ucne.jsautoimports.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jsautoimports.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Upsert
    suspend fun save(usuarioEntity: UsuarioEntity)
    @Query("""
        SELECT * FROM Usuarios
        WHERE UsuarioId = :usuarioId
        LIMIT 1
    """)
    suspend fun getUsuario(usuarioId: Int): UsuarioEntity?
    @Query("""
        SELECT * FROM Usuarios
        WHERE email = :correo
        LIMIT 1 
    """)
    suspend fun getUsuarioByCorreo(correo: String): UsuarioEntity?


    @Delete
    suspend fun deleteUsuario(usuarioEntity: UsuarioEntity)
    @Query("SELECT * FROM Usuarios")
    fun getUsuarios(): Flow<List<UsuarioEntity>>
}