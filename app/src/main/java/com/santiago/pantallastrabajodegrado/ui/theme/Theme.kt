package com.santiago.pantallastrabajodegrado.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- INICIO DE LA ACTUALIZACIÓN ---
// Usando los colores personalizados de Color.kt

// Paleta para el tema oscuro (puedes ajustarla si quieres un look diferente en modo noche)
private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    secondary = DarkOrange,
    tertiary = Orange,
    background = DarkBlue, // Fondo oscuro
    surface = Blue,      // Superficie de las tarjetas
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White,
)

// Paleta para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,
    secondary = DarkOrange,
    tertiary = Orange,
    background = White,      // Fondo claro
    surface = GrayLight,   // Superficie de las tarjetas
    onPrimary = White,
    onSecondary = White,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)
// --- FIN DE LA ACTUALIZACIÓN ---

@Composable
fun PantallasTrabajoDeGradoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Hacemos que la barra de estado sea del mismo color que el primario del tema
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