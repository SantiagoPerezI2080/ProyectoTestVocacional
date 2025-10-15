package com.santiago.pantallastrabajodegrado

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.santiago.pantallastrabajodegrado.R
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme

// Color de la barra superior
private val azulBarra = Color(0xFF0F50A8)

/**
 * Componente reutilizable de la barra superior (TopBar) de la app.
 * @param showLogo Si true, muestra el logo central; si false, muestra un título.
 * @param title Texto a mostrar en la barra si showLogo = false
 * @param onBack Acción al presionar el botón de atrás
 * @param onNotification Acción al presionar el botón de notificaciones
 */
@Composable
fun AppTopBar(
    showLogo: Boolean = true,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onNotification: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(azulBarra)
            .height(56.dp) // altura fija o adaptativa
            .padding(horizontal = 16.dp)
    ){
        // Botón de regreso
        if (onBack != null) {
            Icon(
                painter = painterResource(id = R.drawable.icon_backofi),
                contentDescription = "Atrás",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
            )
        }

        // Logo o título
        if (showLogo) {
            Image(
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(40.dp)
                    .align(Alignment.Center)
            )
        } else if (!title.isNullOrEmpty()) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Notificación
        if (onNotification != null) {
            Icon(
                painter = painterResource(id = R.drawable.icon_notificacion),
                contentDescription = "Notificaciones",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { onNotification() }
            )
        }
    }
}


/**
 * Composable genérico para pantallas con TopBar y contenido debajo.
 * @param showLogo true para mostrar el logo, false para mostrar title
 * @param title Título de la pantalla si showLogo es false
 * @param navBack Acción para el botón de regreso
 * @param onNotification Acción para el botón de notificaciones
 * @param content Contenido principal de la pantalla
 */
@Composable
fun ScreenWithTopBar(
    showLogo: Boolean = true,
    title: String? = null,
    navBack: (() -> Unit)? = null,
    onNotification: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            showLogo = showLogo,
            title = title,
            onBack = navBack,
            onNotification = onNotification
        )
        // **MODIFICACIÓN:** Se eliminó el padding(top = 8.dp)
        Column(
            modifier = Modifier.fillMaxSize()
            // Puedes agregar un fondo al contenido si lo deseas, por ejemplo:
            // .background(Color.White),
            ,
            content = content
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 56)
@Composable
fun PreviewOnlyTopBar() {
    PantallasTrabajoDeGradoTheme {
        AppTopBar(
            showLogo = true,
            onBack = { /* Acción de prueba */ },
            onNotification = { /* Acción de prueba */ }
        )
    }
}
