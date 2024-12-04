package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.PiezaApi
import edu.ucne.jsautoimports.data.remote.dto.PiezaDto
import javax.inject.Inject

class PiezaRemoteDataSources @Inject constructor(
    private val piezaApi: PiezaApi

) {
    suspend fun getPiezas() = piezaApi.getPiezas()
    suspend fun getPiezaById(id: Int) = piezaApi.getPiezaById(id)
    suspend fun addPieza(pieza: PiezaDto) = piezaApi.addPieza(pieza)
    suspend fun updatePieza(id: Int, pieza: PiezaDto) = piezaApi.updatePieza(id, pieza)
    suspend fun deletePieza(id: Int) = piezaApi.deletePieza(id)



}