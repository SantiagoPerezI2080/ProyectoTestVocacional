package com.santiago.pantallastrabajodegrado.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santiago.pantallastrabajodegrado.R

/**
 * Scaffold-based ScreenWithTopBar.
 *
 * Ahora el `content` recibe los PaddingValues (innerPadding) que debe aplicar.
 * Esto hace que AndroidView y otros contenidos respeten la zona del topBar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithTopBar(
    showLogo: Boolean = true,
    title: String? = null,
    navBack: (() -> Unit)? = null,
    onNotification: (() -> Unit)? = null,
    containerColor: Color = Color(0xFF0F2143),
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (showLogo) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_white),
                            contentDescription = "Logo",
                            modifier = Modifier.height(36.dp)
                        )
                    } else {
                        Text(
                            text = title?.uppercase() ?: "",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    if (navBack != null) {
                        IconButton(onClick = navBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_backofi),
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    }
                },
                actions = {
                    if (onNotification != null) {
                        IconButton(onClick = onNotification) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_notificacion),
                                contentDescription = "Notificaciones",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = containerColor)
            )
        },
        containerColor = Color(0xFF0A1E3D) // fondo consistente
    ) { innerPadding ->
        // Delegamos al contenido la responsabilidad de usar el innerPadding
        content(innerPadding)
    }
}
