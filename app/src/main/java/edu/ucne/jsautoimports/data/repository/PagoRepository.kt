package edu.ucne.jsautoimports.data.repository

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import edu.ucne.jsautoimports.data.local.dao.PagoDao
import edu.ucne.jsautoimports.data.local.entities.PagoEntity
import edu.ucne.jsautoimports.data.remote.Resources
import edu.ucne.jsautoimports.data.remote.dto.PagoDto
import edu.ucne.jsautoimports.data.remote.remoteDataSource.PagoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PagoRepository @Inject constructor(
    private val pagoDao: PagoDao,
    private val pagoRemoteDataSource: PagoRemoteDataSource

) {
    suspend fun getPago(id: Int) = pagoRemoteDataSource.getPagoById(id)
    suspend fun postPago(pago: PagoDto) = pagoRemoteDataSource.postPago(pago)
    suspend fun deletePago(id: Int) = pagoRemoteDataSource.deletePago(id)
    suspend fun updatePago(id: Int, pago: PagoDto) = pagoRemoteDataSource.updatePago(id, pago)

    fun getPagos(): Flow<Resources<List<PagoEntity>>> = flow {
        try{
            emit(Resources.Loading())
            val pagos = pagoRemoteDataSource.getPagos()

            pagos.forEach{
                pagoDao.save(
                    it.ToEntity()
                )
            }

            pagoDao.getPagos().collect{localPagos ->
                emit(Resources.Success(localPagos))
            }

        } catch (e: HttpException){
            pagoDao.getPagos().collect{localPagos ->
                emit(Resources.Success(localPagos))
            }


            emit(Resources.Error(e.message ?: "Error HTTP DESCONOCIDO"))

        }catch (e: Exception){
            pagoDao.getPagos().collect{localPagos ->
                emit(Resources.Success(localPagos))
            }
            emit(Resources.Error(e.message ?: "Error DESCONOCIDO"))

        }
    }

}

private fun PagoDto.ToEntity() = PagoEntity(
    pagoId = pagoId,
    pedidoId = pedidoId,
    tarjetaId = tarjetaId,
    fechaPago = fechaPago,
    monto = monto


)