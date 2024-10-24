package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

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
    val currentRoutine = viewModel.currentRoutine

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Resumen de la rutina", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la rutina si está disponible
        if (currentRoutine != null) {
            Text(text = "Nombre: ${currentRoutine.name}")

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar la lista de ejercicios seleccionados
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(currentRoutine.exercises) { exercise ->
                    SelectedExerciseItem(
                        exercise = exercise,
                        onRemoveExercise = {  }
                    )
                }
            }
        } else {
            // Mensaje de error si no hay rutina guardada
            Text(text = "No hay rutina disponible", modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para finalizar y regresar
        Button(
            onClick = {
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
                Text(text = exercise.name.toString() , fontSize = 18.sp)
                Text(text = "Categoría: ${exercise.category}", fontSize = 14.sp)
            }
            IconButton(onClick = onRemoveExercise) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
