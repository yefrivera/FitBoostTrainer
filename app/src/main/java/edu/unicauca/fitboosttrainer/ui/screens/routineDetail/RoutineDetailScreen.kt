package edu.unicauca.fitboosttrainer.ui.screens.routineDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    routineId: String, // ID de la rutina seleccionada
    viewModel: RoutineDetailViewModel = viewModel() // ViewModel para cargar los ejercicios
) {
    // Cargar los ejercicios de la rutina seleccionada
    LaunchedEffect(routineId) {
        viewModel.loadRoutineExercises(routineId)
    }
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }
    val routineName = viewModel.routineName

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = routineName,
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it },
                navController = navController
            )
        }
    ) { innerPadding -> paddingExercise(innerPadding = innerPadding, viewModel = viewModel, navController = navController)}

}

@Composable
fun paddingExercise(innerPadding: PaddingValues,
                    viewModel: RoutineDetailViewModel = viewModel(),
                    navController: NavHostController
) {
    val exercises = viewModel.exercises
    val isLoading = viewModel.isLoading

    var completedCount by remember { mutableStateOf(0) }

    val progress = if (exercises.isNotEmpty()) completedCount.toFloat() / exercises.size else 0f

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de progreso
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(exercises) { exercise ->
                    ExerciseItemCard(
                        exercise = exercise,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                completedCount++
                            } else {
                                completedCount--
                            }
                        }
                    )
                }
            }
            // Botón para finalizar rutina
            Button(
                onClick = {
                    navController.navigate("trainCompletedScreen")
                },
                enabled = completedCount == exercises.size,  // Se habilita solo cuando todos los ejercicios están completados
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Finalizar rutina")
            }
        }
    }
}


@Composable
fun ExerciseItemCard(
    exercise: Exercise,
    onCheckedChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = exercise.imageRes),
                contentDescription = "Imagen del ejercicio",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = exercise.name), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Categoría: ${stringResource(id = exercise.category)}", fontSize = 14.sp)
            }
            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    onCheckedChange(checked)  // Llamar al callback
                }
            )
        }
    }
}