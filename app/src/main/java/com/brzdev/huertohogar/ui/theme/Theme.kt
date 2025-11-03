package com.brzdev.huertohogar.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- ASIGNACIÓN DE LA NUEVA PALETA ---

private val LightColorScheme = lightColorScheme(
    primary = VerdeBosque,
    secondary = NaranjaZanahoria,
    background = CremaNatural,
    surface = VerdeHojaSuave,

    onPrimary = CremaNatural,
    onSecondary = Color.White,
    onBackground = MarronOscuro,
    onSurface = MarronOscuro
)


private val DarkColorScheme = lightColorScheme(
    primary = VerdeBosque,
    secondary = NaranjaZanahoria,
    background = CremaNatural,
    surface = VerdeHojaSuave,
    onPrimary = CremaNatural,
    onSecondary = Color.White,
    onBackground = MarronOscuro,
    onSurface = MarronOscuro
)

@Composable
fun HuertoHogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}