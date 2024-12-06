package edu.ucne.jsautopartsprueba.presentation.pago

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import edu.ucne.proyectofinalaplicada2.presentation.components.TopBarComponent
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun PagoScreen(
    precio: Double,
    cantidad: Int,
    navController: NavController,
    onSavePago: (Int, Int, Int, String, String, String, String, Double) -> Unit,
    notificationCount: Int
) {
    val pagoId = remember { Random().nextInt(10000) }
    val pedidoId = remember { Random().nextInt(10000) }
    val tarjetaId = remember { Random().nextInt(10000) }

    var fechaPago by remember { mutableStateOf("") }
    var nombreTarjeta by remember { mutableStateOf("") }
    var numeroTarjeta by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var mesExpiracion by remember { mutableStateOf("") }
    var anioExpiracion by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Cálculo del monto total
    val monto by derivedStateOf {
        val total = precio * cantidad
        Log.d("PagoScreen", "Precio: $precio, Cantidad: $cantidad, Monto Total: $total")
        total
    }


    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Registrar Pago",
                navController = navController,

                notificationCount = notificationCount
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1A73E8))
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {


                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nombreTarjeta,
                        onValueChange = { nombreTarjeta = it },
                        label = { Text("Nombre de la Tarjeta") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = numeroTarjeta,
                        onValueChange = { numeroTarjeta = it },
                        label = { Text("Número de Tarjeta") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = mesExpiracion,
                            onValueChange = { mesExpiracion = it },
                            label = { Text("Mes de Expiración") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = anioExpiracion,
                            onValueChange = { anioExpiracion = it },
                            label = { Text("Año de Expiración") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = cvc,
                        onValueChange = { cvc = it },
                        label = { Text("CVC") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = monto.toString(),
                        onValueChange = {},
                        label = { Text("Monto Total") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (fechaPago.isNotEmpty() && nombreTarjeta.isNotEmpty() &&
                                numeroTarjeta.isNotEmpty() && cvc.isNotEmpty() &&
                                mesExpiracion.isNotEmpty() && anioExpiracion.isNotEmpty()
                            ) {
                                onSavePago(
                                    pagoId,
                                    pedidoId,
                                    tarjetaId,
                                    fechaPago,
                                    nombreTarjeta,
                                    numeroTarjeta,
                                    "$mesExpiracion/$anioExpiracion",
                                    monto
                                )
                                Toast.makeText(context, "Pago registrado con éxito", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A73E8),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Registrar Pago", fontSize = 18.sp)
                    }
                }
            }
        }
    )
}
