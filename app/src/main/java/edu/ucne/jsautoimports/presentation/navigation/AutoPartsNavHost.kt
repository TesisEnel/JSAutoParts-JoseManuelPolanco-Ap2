package edu.ucne.jsautoimports.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.jsautoimports.presentation.carrito.CartScreen
import edu.ucne.jsautoimports.presentation.categoria.CategoriaListScreen
import edu.ucne.jsautoimports.presentation.login.AuthViewModel
import edu.ucne.jsautoimports.presentation.login.LoginPage
import edu.ucne.jsautoimports.presentation.login.SignUpPage
import edu.ucne.jsautoimports.presentation.others.ProfileScreen
import edu.ucne.jsautoimports.presentation.pago.PagoScreen
import edu.ucne.jsautoimports.presentation.pieza.PiezasListScreen

@Composable
fun AutoPartsNavHost(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ){


        composable<Screen.Login> {
            LoginPage(modifier, navHostController, authViewModel)
        }
        composable<Screen.SignUp> {
            SignUpPage(modifier, navHostController, authViewModel)
        }

        composable<Screen.CategoriaListScreen> {
            CategoriaListScreen(navController = navHostController)
        }
        composable<Screen.PiezasList> {entry ->
            val categoriaId = entry.arguments?.getString("categoriaId") ?: ""
            PiezasListScreen(navController = navHostController, categoriaId = categoriaId)
        }
        composable<Screen.CarritoScreen> {
            CartScreen(
                navController = navHostController
            )
        }
        composable<Screen.ProfileScreen> {
            ProfileScreen(navController = navHostController)
        }



        composable<Screen.PiezasListScreen> {
            PiezasListScreen( navController = navHostController, categoriaId = "")
        }




        composable<Screen.PagoScreen>{
            PagoScreen(
                onSavePago = { pagoId, pedidoId, tarjetaId, fechaPago, monto ->
                }
            )
        }



    }




}