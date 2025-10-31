package com.santiago.pantallastrabajodegrado.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.santiago.pantallastrabajodegrado.ui.main.MainContent
import com.santiago.pantallastrabajodegrado.ui.survey.EncuestaScreenCompose
import com.santiago.pantallastrabajodegrado.ui.carreras.CarrerasScreen
import com.santiago.pantallastrabajodegrado.ui.profile.PerfilScreen
import com.santiago.pantallastrabajodegrado.ui.profile.ResultsScreen
import com.santiago.pantallastrabajodegrado.data.model.programasDeEjemplo
import com.santiago.pantallastrabajodegrado.ui.carreras.ProgramDetailScreenCompose
import com.santiago.pantallastrabajodegrado.ui.auth.LoginActivity
import com.santiago.pantallastrabajodegrado.ui.survey.KuderTestScreen
import com.santiago.pantallastrabajodegrado.ui.survey.KuderResultsScreen
import com.santiago.pantallastrabajodegrado.ui.survey.KuderIntroScreen

@Composable
fun NavGraph(startDestination: String = "home") {
    val navController = rememberNavController()
    AppNavGraph(navController = navController, startDestination = startDestination)
}

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String = "home") {
    NavHost(navController = navController, startDestination = startDestination) {

        // Home / Welcome (XML embedded in Compose)
        composable("home") {
            MainContent(navController = navController)
        }

        // Encuesta (original)
        composable("test") {
            EncuestaScreenCompose(onBack = { navController.popBackStack() })
        }

        // Carreras (lista)
        composable("carreras") {
            CarrerasScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        // Perfil — con manejo de cierre de sesión que lanza LoginActivity y limpia la pila
        composable("perfil") {
            val context = LocalContext.current
            PerfilScreen(
                navController = navController,
                onSignOut = {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla de resultados simple (XML o Compose)
        composable("results") {
            ResultsScreen(onBack = { navController.popBackStack() })
        }

        // Detalle de programa con argumento index
        composable(
            route = "program/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val indexArg = backStackEntry.arguments?.getInt("index") ?: 0
            val program = programasDeEjemplo.getOrNull(indexArg)
            if (program != null) {
                ProgramDetailScreenCompose(program = program, navController = navController)
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }

        // Kuder: introducción / transición antes del test
        composable("kuder_intro") {
            KuderIntroScreen(navController = navController)
        }

        // Kuder: pantalla de test
        composable("kuder_test") {
            KuderTestScreen(navController = navController)
        }

        // Kuder: pantalla de resultados (con onBack)
        composable("kuder_results") {
            KuderResultsScreen(onBack = { navController.popBackStack() })
        }
    }
}
