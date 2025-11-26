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

@Composable
fun SignUpScreen(
    onSignUpClicked: (String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    var passwordTouched by remember { mutableStateOf(false) }
    val hasMinLength = password.length >= 8
    val hasNumber = password.any { it.isDigit() }

    val isPasswordValid = hasMinLength && hasNumber

    fun validateEmail(): Boolean {
        isEmailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return !isEmailError
    }

    @Composable
    fun PasswordRequirementText(met: Boolean, text: String) {
        val color = if (met) MaterialTheme.colorScheme.primary else Color(0xFFFFB4AB)
        Text(text, color = color, style = MaterialTheme.typography.bodySmall)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Fondo de registro",
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
                "Crear Cuenta",
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
                    passwordTouched = true
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordTouched && !isPasswordValid,
                supportingText = {
                    if (passwordTouched) {
                        Column {
                            PasswordRequirementText(
                                met = hasMinLength,
                                text = "Mínimo 8 caracteres"
                            )
                            PasswordRequirementText(
                                met = hasNumber,
                                text = "Mínimo 1 número"
                            )
                        }
                    }
                },
                singleLine = true,
                colors = fieldColors,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    passwordTouched = true

                    if (validateEmail() && isPasswordValid) {
                        onSignUpClicked(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("REGISTRARME")
            }
            TextButton(
                onClick = onNavigateToLogin,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.8f))
            ) {
                Text("¿Ya tienes cuenta? Inicia Sesión")
            }
        }
    }
}