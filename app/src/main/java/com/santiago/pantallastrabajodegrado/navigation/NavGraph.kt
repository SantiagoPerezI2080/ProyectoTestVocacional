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
import androidx.navigation.compose.currentBackStackEntryAsState

import com.google.firebase.auth.FirebaseAuth
import com.santiago.pantallastrabajodegrado.ui.main.MainContent
import com.santiago.pantallastrabajodegrado.ui.survey.EncuestaScreenCompose
import com.santiago.pantallastrabajodegrado.ui.carreras.CarrerasScreen
import com.santiago.pantallastrabajodegrado.ui.profile.PerfilScreen
import com.santiago.pantallastrabajodegrado.ui.profile.ResultsScreen
import com.santiago.pantallastrabajodegrado.data.model.programasDeEjemplo
import com.santiago.pantallastrabajodegrado.ui.carreras.ProgramDetailScreenCompose
import com.santiago.pantallastrabajodegrado.ui.auth.LoginActivity

@Composable
fun NavGraph(startDestination: String = "home") {
    val navController = rememberNavController()
    AppNavGraph(navController = navController, startDestination = startDestination)
}

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String = "home") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            MainContent(navController = navController)
        }

        composable("test") {
            EncuestaScreenCompose(onBack = { navController.popBackStack() })
        }

        composable("carreras") {
            CarrerasScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        composable("perfil") {
            // Usamos LocalContext para lanzar LoginActivity cuando el usuario cierre sesión
            val context = LocalContext.current
            PerfilScreen(
                navController = navController,
                onSignOut = {
                    // cerrar sesión en Firebase
                    FirebaseAuth.getInstance().signOut()
                    // Lanzar LoginActivity y limpiar la pila para que no se pueda volver con "Atrás"
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("results") {
            ResultsScreen(onBack = { navController.popBackStack() })
        }

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
    }
}
