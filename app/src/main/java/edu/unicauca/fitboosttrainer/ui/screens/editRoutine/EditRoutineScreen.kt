package edu.unicauca.fitboosttrainer.ui.screens.editRoutine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.data.Exercise
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoutineScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    routineId: String,
    viewModel: EditRoutineViewModel = viewModel()
) {

    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }
    // Cargar la rutina seleccionada
    LaunchedEffect(routineId) {
        viewModel.loadRoutine(routineId)
    }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = stringResource(R.string.edit_routine),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onBackClick = { navController.popBackStack() },
                drawerState = drawerState
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it },
                navController = navController
            )
        }
    ) { innerPadding -> paddingEditRoutine(innerPadding = innerPadding, viewModel = viewModel, navController = navController) }
}

@Composable
fun paddingEditRoutine(
    innerPadding: PaddingValues,
    viewModel: EditRoutineViewModel,
    navController: NavHostController
){
    val isLoading = viewModel.isLoading
    val routineName = viewModel.routineName
    val exercises = viewModel.exercises


    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Editar el nombre de la rutina
            TextField(
                value = routineName,
                onValueChange = { newName -> viewModel.updateRoutineName(newName) },
                label = { Text("Nombre de la Rutina") },
                modifier = Modifier.fillMaxWidth()
            )

            // Mostrar y editar los ejercicios
            LazyColumn (modifier = Modifier.weight(1f)){
                items(exercises) { exercise ->
                    EditableExerciseCard(
                        exercise = exercise,
                        onUpdateExercise = { updatedExercise ->
                            viewModel.updateExercise(updatedExercise)
                        }
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.saveChanges()
                    navController.navigate("savedRoutine")
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(stringResource(R.string.saved_changes))
            }
        }

    }
}

@Composable
fun EditableExerciseCard(
    exercise: Exercise,
    onUpdateExercise: (Exercise) -> Unit
) {

    var numSeries by remember { mutableStateOf(exercise.numSeries.toString()) }
    var numReps by remember { mutableStateOf(exercise.numReps.toString()) }
    var weight by remember { mutableStateOf(exercise.weight) }

    var seriesError by remember { mutableStateOf(false) }
    var repsError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Ejercicio: ${stringResource(exercise.name)}")

            Row {

                OutlinedTextField(
                    value = numSeries,
                    onValueChange = {
                        numSeries = it
                        seriesError = it.isEmpty()
                        if (!seriesError) {
                            onUpdateExercise(exercise.copy(numSeries = it.toIntOrNull() ?: 0))
                        }
                    },
                    label = { Text("Series") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (!seriesError) {
                                onUpdateExercise(exercise.copy(numSeries = numSeries.toIntOrNull() ?: 0))
                            }
                        }
                    ),
                    isError = seriesError,
                    modifier = Modifier.weight(1f)
                )
                if (seriesError) {
                    Text(
                        text = "Campo requerido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))


                OutlinedTextField(
                    value = numReps,
                    onValueChange = {
                        numReps = it
                        repsError = it.isEmpty()
                        if (!repsError) {
                            onUpdateExercise(exercise.copy(numReps = it.toIntOrNull() ?: 0))
                        }
                    },
                    label = { Text("Repeticiones") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done  // Acción de "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (!repsError) {
                                onUpdateExercise(exercise.copy(numReps = numReps.toIntOrNull() ?: 0))
                            }
                        }
                    ),
                    isError = repsError,
                    modifier = Modifier.weight(1f)
                )
                if (repsError) {
                    Text(
                        text = "Campo requerido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Editar el peso (no vacío)
                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        weightError = it.isEmpty()
                        if (!weightError) {
                            onUpdateExercise(exercise.copy(weight = it))
                        }
                    },
                    label = { Text("Peso") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done  // Acción de "Done"
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (!weightError) {
                                onUpdateExercise(exercise.copy(weight = weight))
                            }
                        }
                    ),
                    isError = weightError,
                    modifier = Modifier.weight(1f)
                )
                if (weightError) {
                    Text(
                        text = "Campo requerido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}