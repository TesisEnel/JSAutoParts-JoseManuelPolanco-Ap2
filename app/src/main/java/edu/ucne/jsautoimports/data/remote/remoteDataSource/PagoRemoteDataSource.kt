package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.PagoApi
import edu.ucne.jsautoimports.data.remote.dto.PagoDto
import javax.inject.Inject

class PagoRemoteDataSource @Inject constructor(
    private val pagoApi: PagoApi
){
    suspend fun getPagos() = pagoApi.getPagos()
    suspend fun getPagoById(id: Int) = pagoApi.getPago(id)
    suspend fun postPago(pago: PagoDto) = pagoApi.addPago(pago)
    suspend fun deletePago(id: Int) = pagoApi.deletePago(id)
    suspend fun updatePago(id: Int, pago: PagoDto) = pagoApi.updatePago(id, pago)
}