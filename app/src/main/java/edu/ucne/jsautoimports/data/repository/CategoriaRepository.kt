package edu.ucne.jsautoimports.data.repository

import edu.ucne.jsautoimports.data.local.dao.CategoriaDao
import edu.ucne.jsautoimports.data.local.entities.CategoriaEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.CategoriaDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.CategoriaRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val categoriaDao: CategoriaDao,
    private val categoriaRemoteDataSource: CategoriaRemoteDataSource

) {
    suspend fun addCategoria(categoria: CategoriaDto) = categoriaRemoteDataSource.postCategoria(categoria)
    suspend fun updateCategoria(id: Int, categoria: CategoriaDto) = categoriaRemoteDataSource.putCategoria(id, categoria)
    suspend fun deleteCategoria(id: Int) = categoriaRemoteDataSource.deleteCategoria(id)

    fun getCategorias(): Flow<Resources<List<CategoriaEntity>>> = flow {
        try{
            emit(Resources.Loading())
            val categorias = categoriaRemoteDataSource.getCategorias()

            categorias.forEach{
                categoriaDao.save(
                    it.toEntity()
                )
            }

            categoriaDao.getCategorias().collect{localCategorys ->
                emit(Resources.Success(localCategorys))
            }

        }catch (e: HttpException){
            emit(Resources.Error(e.message ?:"Error HTTP DESCONOCIDO"))
        }catch (e: Exception){
            categoriaDao.getCategorias().collect{localCategorys ->
                emit(Resources.Success(localCategorys))

            }
            emit(Resources.Error(e.message ?:"Error DE CONEXION"))
        }
    }
}
private fun CategoriaDto.toEntity()= CategoriaEntity (
    categoriaId = categoriaId,
    nombre = nombre,
    imagen = imagen

)