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

import android.widget.Button
import android.widget.TextView
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController


// --- COLORES UNIFICADOS DEL TEMA ---
private val azulOscuro = Color(0xFF074384)
private val azulClaro = Color(0xFF059DDE)
private val azulBarra = Color(0xFF0F50A8)
// --- ACTUALIZACIÓN DE COLOR ---
private val amarilloAcento = Color(0xFFFFC225) // Este será nuestro color principal de acento
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
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Test,
        BottomNavItem.Carreras,
        BottomNavItem.Perfil
    )

    Surface(
        color = azulBarra,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // 👈 Ajusta para no quedar bajo la barra del sistema
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
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
                    Text(
                        text = item.title,
                        color = contentColor,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = Modifier.padding(paddingValues)) {
        composable(BottomNavItem.Home.route) { MainContent(navController) }
        composable(BottomNavItem.Test.route) { TestVocacionalScreen(onBack = { navController.popBackStack() }) }
        composable(BottomNavItem.Carreras.route) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(azulOscuro, azulClaro))), contentAlignment = Alignment.Center) {
                Text("Pantalla de Carreras", color = Color.White)
            }
        }
        composable(BottomNavItem.Perfil.route) {
            val context = LocalContext.current
            PerfilScreen(
                navController = navController, // <- pásalo desde MainActivity
                onSignOut = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }

        composable("results") {
            ResultsScreen(onBack = { navController.popBackStack() })
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

@Composable
fun PerfilScreen(
    navController: NavController, // <- pásalo desde MainActivity
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser

    ScreenWithTopBar(
        showLogo = false,
        title = "Mi Perfil",
        navBack = { },
        onNotification = { }
    ) {
        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(R.layout.activity_profile, null, false).apply {
                    val tvName = findViewById<TextView>(R.id.tv_name)
                    val tvEmail = findViewById<TextView>(R.id.tv_email)
                    val btnSignOut = findViewById<Button>(R.id.btn_sign_out)
                    val optionResults = findViewById<View>(R.id.option_results)

                    tvName.text = user?.displayName ?: "Usuario"
                    tvEmail.text = user?.email ?: "Sin correo"

                    // ✅ Navegar correctamente con el navController que viene por parámetro
                    optionResults.setOnClickListener {
                        navController.navigate("results")
                    }

                    btnSignOut.setOnClickListener {
                        FirebaseAuth.getInstance().signOut()
                        onSignOut()
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun ResultsScreen(onBack: () -> Unit) {
    ScreenWithTopBar(
        showLogo = false,
        title = "Mis Resultados",
        navBack = { onBack() },
        onNotification = { /* si luego agregas notificaciones */ }
    ) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.activity_results, null, false)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun TestVocacionalScreen(onBack: () -> Unit) {
    ScreenWithTopBar(
        showLogo = false,
        title = "Test Vocacional",
        navBack = { onBack() },
        onNotification = { /* Acción de notificación */ }
    ) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context)
                    .inflate(R.layout.activity_test_vocacional, null, false)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}



// Contenido de la pantalla de "Inicio", recreando el XML
@Composable
fun MainContent(navController: NavController) {
    ScreenWithTopBar(
        showLogo = true,
        title = "Inicio",// o false si quieres mostrar un título
        navBack = { /* Acción de volver atrás si aplica */ },
        onNotification = { /* Acción de notificación */ }
    ) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.activity_welcome2, null, false)
                    .apply {
                        // Referencias a los botones
                        val btnIniciar = findViewById<Button>(R.id.btn_iniciar_test)
                        val btnResultados = findViewById<Button>(R.id.btn_ver_resultados)

                        // Navegar a test
                        btnIniciar.setOnClickListener {
                            navController.navigate(BottomNavItem.Test.route)
                        }

                        // Navegar a resultados (o carreras)
                        btnResultados.setOnClickListener {
                            navController.navigate(BottomNavItem.Carreras.route)
                        }
                    }
            },
            modifier = Modifier.fillMaxSize()
        )
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