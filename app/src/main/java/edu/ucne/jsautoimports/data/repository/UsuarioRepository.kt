package edu.ucne.jsautoimports.data.repository

import edu.ucne.jsautoimports.data.local.dao.UsuarioDao
import edu.ucne.jsautoimports.data.local.entities.UsuarioEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.UsuarioDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.UsuarioRemoteDataSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val usuarioRemoteDataSource: UsuarioRemoteDataSources

) {
    suspend fun addUsuario(usuario: UsuarioDto) = usuarioRemoteDataSource.addUsuario(usuario)
    suspend fun getUsuario(id: Int) = usuarioRemoteDataSource.getUsuarioById(id)
    suspend fun deleteUsuario(id: Int) = usuarioRemoteDataSource.deleteUsuario(id)
    suspend fun updateUsuario(id: Int, usuario: UsuarioDto) = usuarioRemoteDataSource.updateUsuario(id, usuario)

    suspend fun getUsuarioByCorreo(correo: String) = usuarioDao.getUsuarioByCorreo(correo)


    fun getUsuarios(): Flow<Resources<List<UsuarioEntity>>> = flow {
        try{
            emit(Resources.Loading())
            val usuarios = usuarioRemoteDataSource.getUsuarios()

            usuarios.forEach {
                usuarioDao.save(
                    it.toEntity()
                )
            }

            usuarioDao.getUsuarios().collect{localUsuarios ->
                emit(Resources.Success(localUsuarios))
            }

        }catch (e: HttpException){
            emit(Resources.Error("Error HTTP CONEXION ${e.message}"))
        }catch (e: Exception){
            usuarioDao.getUsuarios().collect{localUsuarios ->
                emit(Resources.Success(localUsuarios))
            }
            emit(Resources.Error("Error DE HTTP DESCONOCIDO ${e.message}"))

        }
    }
    suspend fun addUsuarioLocal(usuarioDto: UsuarioDto) = usuarioDao.save(usuarioDto.toEntity())

}

private fun UsuarioDto.toEntity() = UsuarioEntity(
    usuarioId = usuarioId,
    nombre =nombre,
    email = email,
    password = password
)