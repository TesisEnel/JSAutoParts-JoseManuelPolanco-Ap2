package edu.ucne.jsautopartsprueba.presentation.categoria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import edu.ucne.jsautoimports.R
import edu.ucne.jsautoimports.data.local.entities.CategoriaEntity
import edu.ucne.jsautoimports.presentation.categoria.CategoriaUiState
import edu.ucne.jsautoimports.presentation.navigation.Screen

@Composable
fun CategoriaListScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoriasListBodyScreen(
        uiState = uiState,
        navController = navController
    )
}

@Composable
fun CategoriasListBodyScreen(
    uiState: CategoriaUiState,
    navController: NavHostController = rememberNavController(),
) {
    val categorias =
        remember { mutableStateListOf<CategoriaEntity>(*uiState.categorias.toTypedArray()) }

    LaunchedEffect(uiState.categorias) {
        categorias.clear()
        categorias.addAll(uiState.categorias)
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopBarComponent(
                title = "Categorias",
                navController = navController,
                notificationCount = 0

            )



        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        color = Color(0xFF1A73E8)
                    )
                }

                uiState.categorias.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.empty_icon),
                            contentDescription = "Empty"
                        )
                        Text(
                            text = "No hay categorias",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.categorias) { categoria ->
                            CategoriaItem(
                                item = categoria,
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaItem( item: CategoriaEntity, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.PiezasList(item.categoriaId.toString())) }
            .padding(horizontal = 8.dp, vertical = 4.dp), // Espaciado entre las tarjetas
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados más atractivos
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Fondo claro para el card
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Altura del Card
        ) {
            // Imagen de la categoría que ocupa todo el Card
            AsyncImage(
                model = item.imagen,
                contentDescription = "Imagen de la categoría",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize() // La imagen ocupa todo el espacio disponible
                    .clip(RoundedCornerShape(12.dp)) // Bordes redondeados para la imagen
            )

            // Fondo oscuro semitransparente para el nombre
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f)) // Fondo semitransparente
                    .align(Alignment.BottomStart) // Posiciona el fondo en la parte inferior
                    .padding(8.dp)
            ) {
                // Nombre de la categoría
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleMedium, // Tipografía estándar
                    color = Color.White, // Texto blanco para contrastar
                    maxLines = 1, // Solo una línea
                    overflow = TextOverflow.Ellipsis // Mostrar "..." si es muy largo
                )
            }
        }
    }
}


