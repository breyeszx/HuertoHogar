package com.brzdev.huertohogar.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
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
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onMyOrdersClick: () -> Unit
) {

    val userProfile by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(userProfile) {
        userProfile?.let {
            address = it.address
            phone = it.phone
        }
    }

    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val geocoder = remember(context) { Geocoder(context, Locale.getDefault()) }

    fun getAddressFromLocation(lat: Double, lon: Double) {
        try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val foundAddress = addresses[0]
                address = foundAddress.getAddressLine(0) ?: "Dirección no encontrada"
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al obtener la dirección", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
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

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                fetchCurrentLocation()
            } else {
                Toast.makeText(context, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    fun checkAndRequestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                fetchCurrentLocation()
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

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

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Dirección de Entrega") },
            modifier = Modifier.fillMaxWidth()
        )

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
                authViewModel.saveUserProfile(address, phone, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("GUARDAR PERFIL")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onMyOrdersClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("VER MIS PEDIDOS")
        }
    }
}