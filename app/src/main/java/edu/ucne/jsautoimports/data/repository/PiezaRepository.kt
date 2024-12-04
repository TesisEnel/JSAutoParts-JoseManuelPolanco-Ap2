package edu.ucne.jsautoimports.data.repository

import edu.ucne.jsautoimports.data.local.dao.PiezaDao
import edu.ucne.jsautoimports.data.local.entities.PiezaEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.PiezaDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.PiezaRemoteDataSources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PiezaRepository @Inject constructor(
    private val piezaDao: PiezaDao,
    private val piezaRemoteDataSource: PiezaRemoteDataSources

) {
    private var piezasListas: List<PiezaEntity> = emptyList()
    suspend fun addPieza(pieza: PiezaDto) = piezaRemoteDataSource.addPieza(pieza)
    suspend fun getProducto(id: Int) = piezaDao.getPieza(id)
    suspend fun deletePieza(id: Int) = piezaRemoteDataSource.deletePieza(id)
    suspend fun updatePieza(id: Int, pieza: PiezaDto) = piezaRemoteDataSource.updatePieza(id, pieza)

    fun getPiezas(): Flow<Resources<List<PiezaEntity>>> = flow {
        try {
            emit(Resources.Loading())
            val piezas = piezaRemoteDataSource.getPiezas()

            piezas.forEach {
                piezaDao.save(
                    it.toEntity()
                )
            }

            piezaDao.getPiezas().collect { localPieza ->
                emit(Resources.Success(localPieza))
            }
        } catch (e: HttpException) {
            emit(Resources.Error("Error HTTP CONEXION ${e.message}"))
        } catch (e: Exception) {
            emit(Resources.Error("Error DE HTTP DESCONOCIDO ${e.message}"))

            piezaDao.getPiezas().collect { localPieza ->
                emit(Resources.Success(localPieza))
            }
        }
    }
    fun buscarPiezas(consulta: String): List<PiezaEntity>{
        return piezasListas.filter { pieza ->
            pieza.nombre?.contains(consulta, ignoreCase = true) == true
        }
    }
}

fun PiezaDto.toEntity(): PiezaEntity{
    return PiezaEntity(
        piezaId = this.piezaId,
        nombre = this.nombre,
        categoriaId = this.categoriaId,
        descripcion = this.descripcion,
        precio = this.precio,
        cantidadDisponible = this.cantidadDisponible,
        imagen = this.imagen,
        impuesto = this.impuesto
    )
}

