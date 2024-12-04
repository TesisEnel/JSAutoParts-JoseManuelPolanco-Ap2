package edu.ucne.jsautoimports.data.repository

import edu.ucne.jsautoimports.data.local.dao.CarritoDao
import edu.ucne.jsautoimports.data.local.dao.CarritoDetalleDao
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.CarritoEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.CarritoDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.CarritoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CarritoRepository @Inject constructor(
    private val carritoRemoteDataSource: CarritoRemoteDataSource,
    private val carritoDao: CarritoDao,
    private val carritodetalleDao: CarritoDetalleDao,
){
    suspend fun addCarrito(carrito: CarritoDto) = carritoRemoteDataSource.postCarrito(carrito)
    suspend fun deleteCarrito(id: Int) = carritoRemoteDataSource.deleteCarrito(id)
    suspend fun getCarrito() = carritoRemoteDataSource.getCarritos()
    suspend fun getCarritoById(id: Int) = carritoRemoteDataSource.getCarritoById(id)
    suspend fun saveCarrito(carrito: CarritoEntity) = carritoDao.save(carrito)

    fun getCarritoos(): Flow<Resources<List<CarritoEntity>>> = flow {
        try {
            emit(Resources.Loading())
            val carrito = carritoRemoteDataSource.getCarritos()

            carrito.forEach {
                carritoDao.save(
                    it.toEntity()
                )
            }

            carritoDao.getCarritos().collect { localCarrito ->
                emit(Resources.Success(localCarrito))
            }

        }catch (e: HttpException){
            emit(Resources.Error(e.message ?: "Error HTTP Geneeral"))
        }catch (e: Exception){
            emit(Resources.Error(e.message ?: "Error Desconocido"))

            carritoDao.getCarritos().collect{ localCarrito ->
                emit(Resources.Success(localCarrito))
            }
        }
    }
    fun getCarritosPorUsuario(usuarioId: Int): Flow<Resources<List<CarritoEntity>>>{
        return flow{
            emit(Resources.Loading())

            try {
                val carritos = carritoDao.getCarritosPorUsuario(usuarioId)
                emit(Resources.Success(carritos))
            }catch (e: Exception){
                emit(Resources.Error(e.localizedMessage ?: "Error Desconocido"))
            }
        }
    }

    suspend fun getCarritoNoPagadoPorUsuario(usuarioId: Int): CarritoEntity?{
        return carritoDao.getCarritoNoPagadoPorUsuario(usuarioId)
    }
    suspend fun getCarritoDetallesPorCarritoId(carritoId: Int): Flow<List<CarritoDetalleEntity>>{
        return carritoDao.getCarritoDetallesPorCarritoId(carritoId)
    }


    suspend fun addCarritoDetalle(detalle: CarritoDetalleEntity){
        carritodetalleDao.save(detalle)
    }

    suspend fun getLastCarritobyPersona(personaId: Int)  =
        carritoDao.getLastCarritoByUsuario(personaId)

    suspend fun CarritoExiste(piezaId: Int, carritoId: Int){
        carritodetalleDao.carritoDetalleExit(piezaId, carritoId)
    }

    suspend fun getCarritoDetallesByPiezaId(piezaId: Int, carritoId: Int)=
        carritodetalleDao.getCarritoDetalleByPiezaId(piezaId, carritoId)


    private fun CarritoDto.toEntity()= CarritoEntity(
        carritoId = carritoId,
        usuarioId = usuarioId,
        pagado= pagado,
        fechaCreacion = fechaCreacion,
        carritoDetalle.toMutableList(),
    )





}

