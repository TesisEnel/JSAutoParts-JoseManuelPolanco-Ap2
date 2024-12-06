package edu.ucne.jsautopartsprueba.presentation.pieza

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.PiezaEntity
import edu.ucne.jsautoimports.presentation.components.SearchBar
import edu.ucne.jsautoimports.presentation.navigation.Screen
import edu.ucne.jsautoimports.presentation.pieza.PiezaViewModel
import edu.ucne.jsautopartsprueba.presentation.CarritoViewModel
import edu.ucne.jsautopartsprueba.presentation.carrito.CarritoUiEvent
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun PiezasListScreen(
    viewModel: PiezaViewModel = hiltViewModel(),
    carritoViewModel: CarritoViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
    categoriaId: String,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }

    val piezasFiltradas = uiState.piezas.filter { pieza ->
        pieza.categoriaId.toString() == categoriaId &&
                pieza.nombre.contains(searchQuery, ignoreCase = true)
    }

    val nombreCategoria: String =
        uiState.categorias.find { it.categoriaId.toString() == categoriaId }?.nombre ?: ""

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "$nombreCategoria",
                navController = navController,
                notificationCount = 0,

                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            SearchBar(searchQuery) { newText -> searchQuery = newText }
            Spacer(modifier = Modifier.height(16.dp))

            if (piezasFiltradas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No hay piezas disponibles para esta categoría.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(piezasFiltradas) { pieza ->
                        ItemPieza(
                            item = pieza,
                            goToPieza = {

                            },
                            onAddToCart = { cantidad ->
                                val detalle = CarritoDetalleEntity(
                                    carritoDetalleId = 0,
                                    carritoId = null,
                                    piezaId = pieza.piezaId,
                                    cantidad = cantidad,
                                    precio = pieza.precio,
                                    subTotal = pieza.precio * cantidad,
                                    itbis = (pieza.precio * cantidad) * 0.18
                                )
                                carritoViewModel.onUiEvent(
                                    event = CarritoUiEvent.AddToCart(detalle)
                                )
                                navController.navigate(Screen.CarritoScreen)
                            },
                            navController = navController
                        )


                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun ItemPieza(
    item: PiezaEntity,
    goToPieza: (PiezaEntity) -> Unit,
    onAddToCart: (Int) -> Unit,
    navController: NavController,
) {
    var cantidad by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { goToPieza(item) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val painter = rememberAsyncImagePainter(
                    model = item.imagen, // La URL de la imagen
                )

                Image(
                    painter = painter,
                    contentDescription = "Imagen de la pieza",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = item.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "DOP ${item.precio}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A73E8) // Azul para el precio
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = item.descripcion,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { if (cantidad > 1) cantidad-- },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Disminuir cantidad",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Text(
                                text = cantidad.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { cantidad++ },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Aumentar cantidad",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onAddToCart(cantidad) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)),
                    modifier = Modifier
                        .weight(1f)
                        .height(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar al carrito",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Add to Cart",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.PagoScreen)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63)),
                    modifier = Modifier
                        .weight(1f)
                        .height(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Comprar ahora",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Buy Now",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }

            }
        }
    }
}









