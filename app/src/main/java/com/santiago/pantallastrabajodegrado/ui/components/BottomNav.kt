package com.santiago.pantallastrabajodegrado.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination

// colores
private val azulBarra = Color(0xFF0F50A8)
private val amarilloAcento = Color(0xFFFFC225)

// Sealed class para items
sealed class BottomNavItem(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavItem("home", "Inicio", com.santiago.pantallastrabajodegrado.R.drawable.icon_house)
    object Test : BottomNavItem("test", "Test", com.santiago.pantallastrabajodegrado.R.drawable.icon_testofi)
    object Carreras : BottomNavItem("carreras", "Carreras", com.santiago.pantallastrabajodegrado.R.drawable.iccon_carrerasofi)
    object Perfil : BottomNavItem("perfil", "Perfil", com.santiago.pantallastrabajodegrado.R.drawable.icon_perfil)
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Test, BottomNavItem.Carreras, BottomNavItem.Perfil)
    Surface(
        color = azulBarra,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(80.dp).fillMaxWidth()
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                val contentColor = if (isSelected) amarilloAcento else Color.White
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            // DEBUG: log antes de la navegación
                            Log.d("BottomNav", "Click item=${item.route} currentRoute=$currentRoute")

                            // Navegación determinista: siempre usamos navigate con popUpTo al startDestination.
                            // Esto evita inconsistencias cuando la pila fue modificada por otras acciones (ej: AndroidView).
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            // DEBUG: log posterior (el currentRoute se actualizará poco después)
                            Log.d("BottomNav", "navigate requested to ${item.route}")
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = contentColor,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(text = item.title, color = contentColor, fontSize = 11.sp)
                }
            }
        }
    }
}
