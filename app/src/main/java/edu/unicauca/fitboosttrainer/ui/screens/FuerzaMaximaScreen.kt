package edu.unicauca.fitboosttrainer.ui.screens

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuerzaMaximaScreen(scrollBehavior: TopAppBarScrollBehavior, drawerState: DrawerState) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }  // Control de la barra de navegación
    Scaffold(
        topBar = {
            MainTopAppBar(
                title = stringResource(R.string.strength_max_title),
                drawerState = drawerState,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.ejercicios),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Lista de ejercicios

            ExerciseItem(
                title = "Press Banca",
                imageRes = R.drawable.banca,
                reps = "(3 x 10)"

            )
            ExerciseItem(
                title = "Press Militar",
                imageRes = R.drawable.militar,
                reps = "(2 x 10)"
            )
            ExerciseItem(
                title = "Remo",
                imageRes = R.drawable.remo,
                reps = "(2 x 10)"
            )
            ExerciseItem(
                title = "Curl",
                imageRes = R.drawable.curl,
                reps = "(2 x 10)"
            )
            ExerciseItem(
                title = "Sentadilla",
                imageRes = R.drawable.sentadilla,
                reps = "(3 x 10)"
            )
            ExerciseItem(
                title = "Peso Muerto",
                imageRes = R.drawable.muerto,
                reps = "(2 x 10)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Acción del botón */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006C7A))  // Color del botón
            ) {
                Text(text = stringResource(R.string.finalizar))  // Texto del botón
            }
        }
    }
}

@Composable
fun ExerciseItem(title: String, imageRes: Int, reps: String) {
    var weight by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDE3E7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del ejercicio
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Imagen de ejercicio",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.peso))
                    Spacer(modifier = Modifier.width(8.dp))

                    // TextField con opciones de teclado numérico
                    TextField(
                        value = weight,
                        onValueChange = { weight = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.peso_placeholder),
                                fontSize = (11.sp),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic  // Cursiva aplicada
                            )
                        },
                        modifier = Modifier.width(60.dp),  // Hacer más corto el campo de entrada
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,  // Teclado numérico
                            imeAction = ImeAction.Done
                        )
                    )
                    Text(text = "kg")

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = reps)
                }
            }

            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FuerzaMaximaScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    FuerzaMaximaScreen(scrollBehavior = scrollBehavior, drawerState = drawerState)
}