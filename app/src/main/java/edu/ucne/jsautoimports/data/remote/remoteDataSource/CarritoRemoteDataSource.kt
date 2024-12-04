package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.CarritoApi
import edu.ucne.jsautoimports.data.remote.dto.CarritoDto
import javax.inject.Inject

class CarritoRemoteDataSource @Inject constructor(
    private val carritoApi: CarritoApi

){
    suspend fun getCarritos() = carritoApi.getCarritos()
    suspend fun getCarritoById(id: Int) = carritoApi.getCarritoById(id)
    suspend fun postCarrito(carrito: CarritoDto) = carritoApi.postCarrito(carrito)
    suspend fun deleteCarrito(id: Int) = carritoApi.deleteCarrito(id)

}
