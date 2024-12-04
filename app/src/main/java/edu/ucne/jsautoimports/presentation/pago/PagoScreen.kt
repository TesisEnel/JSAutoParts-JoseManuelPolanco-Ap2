package edu.ucne.jsautoimports.presentation.pago

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun PagoScreen(
    onSavePago: (Int, Int, Int, String, Double) -> Unit
) {
    // Los IDs se generan automáticamente
    val pagoId = remember { Random().nextInt(10000) }
    val pedidoId = remember { Random().nextInt(10000) }
    val tarjetaId = remember { Random().nextInt(10000) }

    var fechaPago by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registrar Pago",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fechaPago,
            onValueChange = { fechaPago = it },
            label = { Text("Fecha de Pago") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            fechaPago = "$dayOfMonth/${month + 1}/$year"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.show()
                }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                if (fechaPago.isNotEmpty() && monto.isNotEmpty()) {
                    onSavePago(
                        pagoId,
                        pedidoId,
                        tarjetaId,
                        fechaPago,
                        monto.toDouble()
                    )
                    Toast.makeText(context, "Pago guardado con éxito", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Por favor, llena todos los campos", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Pago")
        }
    }
}


