package com.santiago.pantallastrabajodegrado.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santiago.pantallastrabajodegrado.data.ApiPregunta

private val FondoAzul = Color(0xFF0A1E3D)
private val AzulBarra = Color(0xFF0F2143)
private val GradStart = Color(0xFF0B75C8)
private val GradEnd = Color(0xFF0A478B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuestaScreenCompose(
    onBack: () -> Unit,
    vm: EncuestaViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val seleccion by vm.seleccion.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        containerColor = FondoAzul,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TEST VOCACIONAL", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = AzulBarra)
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(color = FondoAzul, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (!vm.todoRespondido()) {
                                Toast.makeText(context, "Por favor, responde todas las preguntas.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, vm.respuestasDebug(), Toast.LENGTH_LONG).show()
                            }
                        },
                        shape = CircleShape
                    ) { Text("Enviar") }
                }
            }
        }
    ) { inner ->
        when (val state = uiState) {
            is EncuestaUiState.Loading -> Box(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            is EncuestaUiState.Error -> Box(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text(state.message, color = Color.White) }

            is EncuestaUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(inner)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.preguntas) { p ->
                        PreguntaCardCompose(
                            pregunta = p,
                            seleccionada = seleccion[p.id],
                            onSeleccion = { opcionId -> vm.seleccionar(p.id, opcionId) }
                        )
                    }
                    item { Spacer(Modifier.height(80.dp)) } 
                }
            }
        }
    }
}

@Composable
fun PreguntaCardCompose(
    pregunta: ApiPregunta,
    seleccionada: String?,
    onSeleccion: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF123258))
    ) {
        Column(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(GradStart, GradEnd)),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = pregunta.texto,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(Modifier.padding(12.dp)) {
                pregunta.opciones.forEach { opcion ->
                    OpcionRadioRow(
                        texto = opcion.texto,
                        selected = seleccionada == opcion.id,
                        onClick = { onSeleccion(opcion.id) }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun OpcionRadioRow(
    texto: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color.White else Color(0x55FFFFFF)
    val bg = if (selected) Color(0x22FFFFFF) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .background(bg, RoundedCornerShape(12.dp))
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(Modifier.width(8.dp))
        Text(texto, color = Color.White)
    }
}
