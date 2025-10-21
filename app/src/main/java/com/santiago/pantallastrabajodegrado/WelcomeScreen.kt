package com.santiago.pantallastrabajodegrado

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button as XmlButton
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.google.firebase.auth.FirebaseAuth
import com.santiago.pantallastrabajodegrado.ui.EncuestaScreenCompose
import com.santiago.pantallastrabajodegrado.ui.theme.PantallasTrabajoDeGradoTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.*



// --- COLORES UNIFICADOS DEL TEMA ---
private val azulOscuro = Color(0xFF074384)
private val azulClaro = Color(0xFF059DDE)
private val azulBarra = Color(0xFF0F50A8)
private val amarilloAcento = Color(0xFFFFC225)
private val azulBotonSecundario = Color(0xFF054594)

// Sealed class para definir cada pantalla de la barra de navegación
sealed class BottomNavItem(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavItem("home", "Inicio", R.drawable.icon_house)
    object Test : BottomNavItem("test", "Test", R.drawable.icon_testofi)
    object Carreras : BottomNavItem("carreras", "Carreras", R.drawable.iccon_carrerasofi)
    object Perfil : BottomNavItem("perfil", "Perfil", R.drawable.icon_perfil)
}

// 1. Modelo de Datos para las carreras
data class Programa(
    val titulo: String,
    val duracion: String,
    val creditos: Int,
    val imagenRes: Int,
    val colorTitulo: Color = Color.White
)

// 2. Datos de ejemplo para las carreras
val programasDeEjemplo = listOf(
    Programa("Ingeniería de Sistemas", "9 semestres", 157, R.drawable.ing_software, colorTitulo = Color.White),
    Programa("Administración de Empresas", "9 semestres", 157, R.drawable.admin_empr, colorTitulo = Color(0xFFFF7F2A)), // naranja
    Programa("Derecho", "10 semestres", 169, R.drawable.derecho, colorTitulo = Color(0xFF39E27D)), // verde
    Programa("Entrenamiento Deportivo", "9 semestres", 168, R.drawable.deporte, colorTitulo = Color(0xFFD243F0)), // magenta
    Programa("Psicología", "10 semestres", 158, R.drawable.psicologia, colorTitulo = Color.White),
    Programa("Medicina", "12 semestres", 280, R.drawable.medicina, colorTitulo = Color(0xFF4FC3F7)), // celeste
    Programa("Diseño Gráfico", "8 semestres", 140, R.drawable.diseno_grafica, colorTitulo = Color(0xFFFFEB3B)), // amarillo
    Programa("Arquitectura", "10 semestres", 175, R.drawable.arquitectura, colorTitulo = Color.White),
    Programa("Comunicación Social", "9 semestres", 155, R.drawable.com_social, colorTitulo = Color(0xFFF06292)), // rosa
    Programa("Contaduría Pública", "9 semestres", 150, R.drawable.con_publica, colorTitulo = Color.White)
)


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
    Surface(
        color = azulBarra,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ){
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
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(painter = painterResource(id = item.icon), contentDescription = item.title, tint = contentColor, modifier = Modifier.size(23.dp))
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
        // --- RUTA DEL TEST ACTUALIZADA ---
        composable(BottomNavItem.Test.route) {
            EncuestaScreenCompose(onBack = { navController.popBackStack() })
        }
        composable(BottomNavItem.Carreras.route) {
            CarrerasScreen(navController = navController, onBack = { navController.popBackStack() })
        }
        composable(BottomNavItem.Perfil.route) {
            val context = LocalContext.current
            PerfilScreen(
                onBack = { navController.popBackStack() },
                navController = navController,
                onSignOut = {
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
        composable("results") { ResultsScreen(onBack = { navController.popBackStack() }) }

        composable("program/{index}") { backStackEntry ->
            val indexArg = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            val programa = programasDeEjemplo.getOrNull(indexArg)
            if (programa != null) {
                ProgramDetailScreenCompose(programa = programa, navController = navController)
            } else {
                // opcional: mostrar fallback / regresar
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}

// --- BARRA SUPERIOR REUTILIZABLE (NUEVA VERSIÓN) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithTopBar(
    title: String,
    showLogo: Boolean,
    navBack: () -> Unit,
    onNotification: () -> Unit,
    leftIsSearch: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1E3D)) // fondo azul oscuro
    ) {
        CenterAlignedTopAppBar(
            title = {
                if (showLogo) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_white),
                        contentDescription = "Logo",
                        modifier = Modifier.height(35.dp)
                    )
                } else {
                    Text(
                        text = title.uppercase(), // mayúsculas
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            navigationIcon = {
                if (leftIsSearch) {
                    IconButton(onClick = { /* abrir búsqueda */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_lupa),
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                } else {
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
                IconButton(onClick = onNotification) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_notificacion),
                        contentDescription = "Notificaciones",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF0F2143) // barra azul oscura
            )
        )
        content(this)
    }
}

// --- NUEVA TARJETA DE PROGRAMA ---
@Composable
fun ProgramaCardMockup(
    programa: Programa,
    modifier: Modifier = Modifier,
    onVerMas: () -> Unit = {}
) {
    val radius = 16.dp
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(radius),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF123258)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = programa.imagenRes),
                contentDescription = programa.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = radius, bottomStart = radius,
                            topEnd = 0.dp, bottomEnd = 0.dp
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF0B75C8), Color(0xFF0A478B))
                        ),
                        shape = RoundedCornerShape(
                            topStart = 0.dp, bottomStart = 0.dp,
                            topEnd = radius, bottomEnd = radius
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                ) {
                    Text(
                        text = programa.titulo,
                        color = programa.colorTitulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(text = "Duración: ${programa.duracion}", color = Color(0xE6FFFFFF), fontSize = 12.sp)
                    Text(text = "Total de créditos: ${programa.creditos}", color = Color(0xE6FFFFFF), fontSize = 12.sp)
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = onVerMas,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF0B2447)),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(36.dp)
                    ) { Text("Ver más") }
                }
            }
        }
    }
}

// --- NUEVA PANTALLA DE CARRERAS ---
@Composable
fun CarrerasScreen(navController: NavHostController, onBack: () -> Unit) {
    ScreenWithTopBar(
        showLogo = false,
        title = "Programas de pregrado",
        navBack = { /* sin back en esta vista */ },
        onNotification = { /* abrir notificaciones */ },
        leftIsSearch = true
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A1E3D)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(programasDeEjemplo) { index, programa ->
                ProgramaCardMockup(
                    programa = programa,
                    onVerMas = {
                        navController.navigate("program/$index")
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun PerfilScreen(navController: NavController, onSignOut: () -> Unit, onBack: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    ScreenWithTopBar(showLogo = false, title = "Mi Perfil", navBack = { onBack() }, onNotification = { }) {
        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(R.layout.activity_profile, null, false).apply {
                    findViewById<TextView>(R.id.tv_name).text = user?.displayName ?: "Usuario"
                    findViewById<TextView>(R.id.tv_email).text = user?.email ?: "Sin correo"
                    findViewById<View>(R.id.option_results).setOnClickListener { navController.navigate("results") }
                    findViewById<XmlButton>(R.id.btn_sign_out).setOnClickListener {
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
    ScreenWithTopBar(showLogo = false, title = "Mis Resultados", navBack = { onBack() }, onNotification = { }) {
        AndroidView(factory = { LayoutInflater.from(it).inflate(R.layout.activity_results, null, false) }, modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun MainContent(navController: NavController) {
    ScreenWithTopBar(showLogo = true, title = "Inicio", navBack = { }, onNotification = { }) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.activity_welcome2, null, false)
                    .apply {
                        findViewById<XmlButton>(R.id.btn_iniciar_test).setOnClickListener { navController.navigate(BottomNavItem.Test.route) }
                        findViewById<XmlButton>(R.id.btn_ver_resultados).setOnClickListener { navController.navigate(BottomNavItem.Carreras.route) }
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
