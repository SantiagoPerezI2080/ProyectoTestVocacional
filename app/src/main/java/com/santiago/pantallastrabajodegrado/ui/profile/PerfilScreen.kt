package com.santiago.pantallastrabajodegrado.ui.profile

import androidx.compose.foundation.layout.fillMaxSize
import android.view.LayoutInflater
import android.view.View
import android.widget.Button as XmlButton
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.santiago.pantallastrabajodegrado.ui.components.ScreenWithTopBar
import androidx.compose.foundation.layout.padding

@Composable
fun PerfilScreen(navController: NavController, onSignOut: () -> Unit, onBack: () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    ScreenWithTopBar(
        showLogo = false,
        title = "Información de usuario",

        onNotification = null
    ) { innerPadding ->
        AndroidView(
            factory = { ctx ->
                LayoutInflater.from(ctx).inflate(com.santiago.pantallastrabajodegrado.R.layout.activity_profile, null, false).apply {
                    findViewById<TextView>(com.santiago.pantallastrabajodegrado.R.id.tv_name).text = user?.displayName ?: "Usuario"
                    findViewById<TextView>(com.santiago.pantallastrabajodegrado.R.id.tv_email).text = user?.email ?: "Sin correo"
                    findViewById<View>(com.santiago.pantallastrabajodegrado.R.id.option_results).setOnClickListener { navController.navigate("results") }
                    findViewById<XmlButton>(com.santiago.pantallastrabajodegrado.R.id.btn_sign_out).setOnClickListener {
                        FirebaseAuth.getInstance().signOut()
                        onSignOut()
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // <= aplica innerPadding
        )
    }
}
