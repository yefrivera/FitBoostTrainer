package edu.unicauca.fitboosttrainer.ui.screens.CreationRoutine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.data.Exercise


@Composable
fun RoutineSummaryScreen(
    navController: NavHostController,
    viewModel: RoutineViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Resumen de la rutina", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de ejercicios seleccionados
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.selectedExercises) { exercise ->
                SelectedExerciseItem(
                    exercise = exercise,
                    onRemoveExercise = { viewModel.removeExercise(exercise) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para finalizar
        Button(
            onClick = {
                // Volver o realizar acciones finales
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar y regresar")
        }
    }
}

@Composable
fun SelectedExerciseItem(
    exercise: Exercise,
    onRemoveExercise: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = exercise.nameExercise.toString() , fontSize = 18.sp)
                Text(text = "Categoría: ${exercise.categoryExercise}", fontSize = 14.sp)
            }
            IconButton(onClick = onRemoveExercise) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
