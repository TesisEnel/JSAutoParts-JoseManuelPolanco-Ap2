package edu.ucne.jsautoimports.data.remote.remoteDataSource

import edu.ucne.jsautoimports.data.remote.apis.UsuarioAPi
import edu.ucne.jsautoimports.data.remote.dto.UsuarioDto
import javax.inject.Inject

class UsuarioRemoteDataSources @Inject constructor(
    private val usuarioApi: UsuarioAPi
) {
    suspend fun addUsuario(usuario: UsuarioDto) = usuarioApi.addUsuario(usuario)
    suspend fun getUsuarioById(id: Int) = usuarioApi.getUsuarioById(id)
    suspend fun getUsuarios() = usuarioApi.getUsuarios()
    suspend fun deleteUsuario(id: Int) = usuarioApi.deleteUsuario(id)
    suspend fun updateUsuario(id: Int, usuario: UsuarioDto) = usuarioApi.updateUsuario(id, usuario)


}