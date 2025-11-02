package com.brzdev.huertohogar.ui.view

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(
    onSignUpClicked: (String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados para la validación
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        isEmailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        // Podrías añadir más validaciones, ej. contraseña > 6 caracteres
        isPasswordError = password.isBlank()

        return !isEmailError && !isPasswordError
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineLarge)
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
            supportingText = {
                if (isEmailError) {
                    Text("Introduce un email válido")
                }
            },
            singleLine = true
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
            supportingText = {
                if (isPasswordError) {
                    Text("La contraseña no puede estar vacía")
                }
                // Ejemplo de una validación más compleja:
                // if (isPasswordError) {
                //    Text("Debe tener al menos 6 caracteres")
                // }
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    onSignUpClicked(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("REGISTRARME")
        }
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia Sesión")
        }
    }
}