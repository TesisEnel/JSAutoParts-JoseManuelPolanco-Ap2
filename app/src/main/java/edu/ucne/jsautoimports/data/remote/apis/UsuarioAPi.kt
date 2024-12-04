package edu.ucne.jsautoimports.data.remote.apis

import edu.ucne.jsautoimports.data.remote.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioAPi {
    @POST("api/Usuarios")
    suspend fun addUsuario(@Body usuario: UsuarioDto): UsuarioDto
    @GET("api/Usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuarioDto
    @GET("api/Usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>
    @DELETE("api/Usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<Unit>
    @PUT("api/Usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body usuario: UsuarioDto): Response<UsuarioDto>

}