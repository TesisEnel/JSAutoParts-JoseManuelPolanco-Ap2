package edu.ucne.jsautoimports.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.jsautoimports.R
import edu.ucne.jsautoimports.presentation.navigation.Screen

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(Screen.CategoriaListScreen)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A73E8))
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_removebg_preview),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 24.dp),
            tint = Color.Unspecified
        )

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                textColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                textColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        androidx.compose.material3.Button(
            onClick = { authViewModel.signup(email, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            androidx.compose.material3.Text(
                text = "Create Account",
                fontSize = 16.sp,
                color = Color(0xFF1A73E8)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(Screen.Login) }
        ) {
            Text(text = "Already have an account? Login", color = Color.White)
        }
    }
}
