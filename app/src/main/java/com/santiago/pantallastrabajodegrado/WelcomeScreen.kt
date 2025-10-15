package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button as XmlButton
import android.widget.LinearLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme

// --- COLORES UNIFICADOS DEL TEMA ---
private val azulOscuro = Color(0xFF074384)
private val azulClaro = Color(0xFF059DDE)
private val azulBarra = Color(0xFF0F50A8)
// --- ACTUALIZACIÓN DE COLOR ---
private val amarilloAcento = Color(0xFFC49A37) // Este será nuestro color principal de acento
private val azulBotonSecundario = Color(0xFF054594)

// Sealed class para definir cada pantalla de la barra de navegación
sealed class BottomNavItem(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavItem("home", "Inicio", R.drawable.icon_house)
    object Test : BottomNavItem("test", "Test", R.drawable.icon_testofi)
    object Carreras : BottomNavItem("carreras", "Carreras", R.drawable.iccon_carrerasofi)
    object Perfil : BottomNavItem("perfil", "Perfil", R.drawable.icon_perfil)
}

@Composable
fun WelcomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            paddingValues = innerPadding
        )
    }
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Test, BottomNavItem.Carreras, BottomNavItem.Perfil)
    Surface(color = azulBarra, modifier = Modifier.fillMaxWidth()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(80.dp).fillMaxWidth()
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                // --- ACTUALIZACIÓN DE COLOR ---
                val contentColor = if (isSelected) amarilloAcento else Color.White
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = contentColor,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(text = item.title, color = contentColor, fontSize = 11.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = Modifier.padding(paddingValues)) {
        composable(BottomNavItem.Home.route) { MainContent(navController) }
        composable(BottomNavItem.Test.route) { EncuestaScreen() }
        composable(BottomNavItem.Carreras.route) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(azulOscuro, azulClaro))), contentAlignment = Alignment.Center) {
                Text("Pantalla de Carreras", color = Color.White)
            }
        }
        composable(BottomNavItem.Perfil.route) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(azulOscuro, azulClaro))), contentAlignment = Alignment.Center) {
                Text("Pantalla de Perfil", color = Color.White)
            }
        }
    }
}

@Composable
fun EncuestaScreen() {
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_encuesta, null, false)
            val contenedorPreguntasLayout = view.findViewById<LinearLayout>(R.id.contenedorPreguntas)
            val btnEnviar = view.findViewById<XmlButton>(R.id.btnEnviar)
            val logicaEncuesta = Encuesta(context, contenedorPreguntasLayout, btnEnviar)
            logicaEncuesta.mostrarEncuestaEnUI()
            view
        },
        modifier = Modifier.fillMaxSize()
    )
}

// Contenido de la pantalla de "Inicio", recreando el XML
@Composable
fun MainContent(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(colors = listOf(azulOscuro, azulClaro))
        )
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp).background(azulBarra).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_backofi), contentDescription = "Atrás", tint = Color.White, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.logo_white), contentDescription = "Logo", modifier = Modifier.height(40.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.icon_notificacion), contentDescription = "Notificaciones", tint = Color.White, modifier = Modifier.size(32.dp))
        }

        // Body Content
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Welcome Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D5FC6)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "¡Bienvenido a tu Futuro!", color = amarilloAcento, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "¿Listo para descubrir la carrera que mejor se adapta a ti?\n\nEsta app está diseñada para ayudarte a tomar una de las decisiones más importantes de tu vida.\n\nA través de un test personalizado, exploraremos tus intereses, habilidades y valores para sugerirte las opciones de carrera que mejor se alinean con tu perfil.\n\nTu futuro comienza aquí, ¡comencemos!",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Buttons
            Button(
                onClick = { navController.navigate(BottomNavItem.Test.route) }, // Navega a la pantalla de Test
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                // --- ACTUALIZACIÓN DE COLOR ---
                colors = ButtonDefaults.buttonColors(containerColor = amarilloAcento)
            ) {
                Text("INICIAR TEST", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(BottomNavItem.Carreras.route) }, // Navega a la pantalla de Carreras/Resultados
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azulBotonSecundario)
            ) {
                Text("VER RESULTADOS", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomNavigationPreview() {
    PantallasTrabajoDeGradoTheme {
        val navController = rememberNavController()
        AppBottomNavigation(navController = navController)
    }
}