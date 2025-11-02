package com.brzdev.huertohogar.ui.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.brzdev.huertohogar.viewmodel.AuthViewModel
import com.google.android.gms.location.LocationServices
import java.util.Locale

@Composable
fun ProfileScreen(authViewModel: AuthViewModel) {

    val userProfile by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    // Estados locales para los campos de texto
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // Rellena los campos cuando se carga el perfil desde la DB
    LaunchedEffect(userProfile) {
        userProfile?.let {
            address = it.address
            phone = it.phone
        }
    }

    // --- INICIO DE LÓGICA DE UBICACIÓN ---

    // 1. Inicializa el cliente de ubicación y el geocodificador
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val geocoder = remember(context) { Geocoder(context, Locale.getDefault()) }

    // 2. Función para convertir Lat/Lon en Dirección
    fun getAddressFromLocation(lat: Double, lon: Double) {
        try {
            // deprecatedGetFromLocation es más simple para este caso
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val foundAddress = addresses[0]
                // Formatea la dirección como un string
                address = foundAddress.getAddressLine(0) ?: "Dirección no encontrada"
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al obtener la dirección", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Función para obtener la ubicación (¡requiere chequeo de permiso!)
    fun fetchCurrentLocation() {
        // Esta comprobación es obligatoria
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // ¡Tenemos la ubicación! Ahora la convertimos
                        getAddressFromLocation(location.latitude, location.longitude)
                    } else {
                        Toast.makeText(context, "No se pudo obtener la ubicación. Activa el GPS.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al obtener ubicación", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // 4. Lanzador de permisos: esto pide el permiso al usuario
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Permiso concedido, obtenemos la ubicación
                fetchCurrentLocation()
            } else {
                // Permiso denegado
                Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // 5. Función de ayuda para chequear y pedir permiso
    fun checkAndRequestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Si el permiso YA está concedido
                fetchCurrentLocation()
            }
            else -> {
                // Si el permiso NO está concedido, lo pedimos
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // --- FIN DE LÓGICA DE UBICACIÓN ---


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Conectado como: ${userProfile?.email ?: ""}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))

        // --- Campo de Dirección (ahora se actualiza localmente) ---
        OutlinedTextField(
            value = address,
            onValueChange = { address = it }, // Permite al usuario editar manualmente
            label = { Text("Dirección de Entrega") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- NUEVO BOTÓN ---
        TextButton(
            onClick = { checkAndRequestPermission() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Usar mi ubicación actual")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Número de Contacto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Guarda los datos de los estados locales
                authViewModel.saveUserProfile(address, phone, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("GUARDAR PERFIL")
        }
    }
}