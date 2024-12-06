package edu.ucne.jsautoimports.presentation.others

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ShoppingCartScreen() {
    var totalAmount by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A73E8))
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
        ) {
            IconButton(
                onClick = { /* Acción de retroceso */ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Carrito de compras",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StepIndicator(step = "1", label = "Cart", isActive = true)
            StepIndicator(step = "2", label = "Address", isActive = false)
            StepIndicator(step = "3", label = "Checkout", isActive = false)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            item {
                ShoppingCartItem(
                    storeName = "Fine Autos",
                    productName = "Limpiador Multiusos",
                    productPrice = "DOP 700",
                    shipping = "DOP 179",
                    deliveryRange = "15 - 17 Nov",
                    imageUrl = "https://jsautoimports.blob.core.windows.net/imagenes/producto1.png",
                    onSubtotalChange = { subtotal -> totalAmount += subtotal }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ShoppingCartItem(
                    storeName = "Pak Wheels Car Care",
                    productName = "Paquete de limpiadores",
                    productPrice = "DOP 839",
                    originalPrice = "DOP 1,198",
                    discount = "30% off",
                    imageUrl = "https://jsautoimports.blob.core.windows.net/imagenes/producto1.png",
                    onSubtotalChange = { subtotal -> totalAmount += subtotal }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ShoppingCartItem(
                    productName = "Kit Car 6 in 1",
                    productPrice = "DOP 755",
                    originalPrice = "DOP 1,078",
                    discount = "30% off",
                    imageUrl = "https://jsautoimports.blob.core.windows.net/imagenes/producto1.png",
                    onSubtotalChange = { subtotal -> totalAmount += subtotal }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total (incl. tax): DOP ${"%,.2f".format(totalAmount)}",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { /* Acción de continuar */ },
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF1A73E8),
                                contentColor = Color.White
                            )


                        ) {
                            Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepIndicator(step: String, label: String, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(if (isActive) Color(0xFF1A73E8) else Color.Gray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(step, color = Color.White, style = MaterialTheme.typography.caption)
        }
        Text(label, style = MaterialTheme.typography.caption)
    }
}

@Composable
fun ShoppingCartItem(
    storeName: String? = null,
    productName: String,
    productPrice: String,
    originalPrice: String? = null,
    discount: String? = null,
    shipping: String? = null,
    deliveryRange: String? = null,
    imageUrl: String? = null,
    onSubtotalChange: (Float) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    val pricePerItem = productPrice.replace("PKR ", "").replace(",", "").toFloatOrNull() ?: 0f
    var subtotal by remember { mutableStateOf(pricePerItem * quantity) }

    LaunchedEffect(quantity) {
        onSubtotalChange(subtotal)
        subtotal = pricePerItem * quantity
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            storeName?.let {
                Text(it, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(productName, style = MaterialTheme.typography.body1)
                    Row {
                        if (originalPrice != null) {
                            Text(
                                originalPrice,
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            "DOP ${"%,.2f".format(subtotal)}",
                            style = MaterialTheme.typography.body1,
                            color = Color(0xFF1A73E8)
                        )
                    }
                    discount?.let {
                        Text(it, color = Color.Red, fontSize = 12.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton( // Disminuir cantidad
                                onClick = { if (quantity > 1) quantity-- }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Decrease quantity")
                    }
                    Text(quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp), style = MaterialTheme.typography.body1)
                    IconButton(
                        onClick = { quantity++ }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase quantity")
                    }
                }
            }

            Text("Subtotal: DOP ${"%,.2f".format(subtotal)}", style = MaterialTheme.typography.body2)
            shipping?.let {
                Text("Shipping: $shipping", style = MaterialTheme.typography.body2)
            }
            deliveryRange?.let {
                Text("Receive between: $it", style = MaterialTheme.typography.body2)
            }
        }
    }

}
