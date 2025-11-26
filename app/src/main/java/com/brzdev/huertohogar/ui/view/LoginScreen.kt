package com.brzdev.huertohogar.ui.view

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.brzdev.huertohogar.R
import com.brzdev.huertohogar.ui.theme.Montserrat

@Composable
fun LoginScreen(
    onLoginClicked: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        isEmailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.isBlank()
        return !isEmailError && !isPasswordError
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Fondo de inicio de sesión",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                cursorColor = Color.White,
                errorTextColor = Color(0xFFFFB4AB),
                errorBorderColor = Color(0xFFFFB4AB),
                errorSupportingTextColor = Color(0xFFFFB4AB)
            )

            Text(
                "Iniciar Sesión",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailError = false
                },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = isEmailError,
                supportingText = { if (isEmailError) Text("Introduce un email válido") },
                singleLine = true,
                colors = fieldColors,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordError = false
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = isPasswordError,
                supportingText = { if (isPasswordError) Text("La contraseña no puede estar vacía") },
                singleLine = true,
                colors = fieldColors,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (validateFields()) {
                        onLoginClicked(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("INGRESAR")
            }
            TextButton(
                onClick = onNavigateToSignUp,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.8f))
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}