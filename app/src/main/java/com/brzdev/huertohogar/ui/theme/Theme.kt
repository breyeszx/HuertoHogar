package com.brzdev.huertohogar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = HuertoMutedTeal,       // El teal como color principal
    secondary = HuertoSoftGreen,     // El verde suave como secundario
    background = HuertoPaleLime,     // El más claro como fondo
    surface = HuertoPaleLime,        //
    onPrimary = HuertoDarkPlum,      // Texto sobre el color principal
    onSecondary = HuertoDarkPlum,    // Texto sobre el secundario
    onBackground = HuertoDarkPlum,   // Texto sobre el fondo (contraste alto)
    onSurface = HuertoDarkPlum       // Texto sobre superficies
)

private val DarkColorScheme = darkColorScheme(
    primary = HuertoSoftGreen,       // El verde suave resalta más en oscuro
    secondary = HuertoMutedTeal,     // El teal como secundario
    background = HuertoDarkPlum,     // El morado oscuro como fondo
    surface = HuertoDarkPlum,
    onPrimary = HuertoDarkPlum,
    onSecondary = HuertoDarkPlum,
    onBackground = HuertoPaleLime,   // Texto claro sobre fondo oscuro
    onSurface = HuertoPaleLime
)

@Composable
fun HuertoHogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}