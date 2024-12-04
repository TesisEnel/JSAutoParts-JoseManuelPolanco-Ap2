package edu.ucne.jsautoimports.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
     @Serializable
     data object Login: Screen()

     @Serializable
     data object SignUp: Screen()

     @Serializable
     data object CarritoScreen: Screen()

     @Serializable
     data class PiezasList(val categoriaId: String): Screen()

     @Serializable
     data object PiezasListScreen: Screen()

     @Serializable
     data object ProfileScreen: Screen()

     @Serializable
     data object PagoScreen: Screen()

     @Serializable
     data object CategoriaListScreen: Screen()



}