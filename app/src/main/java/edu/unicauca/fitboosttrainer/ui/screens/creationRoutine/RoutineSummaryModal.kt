package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.data.Exercise

@Composable
fun RoutineSummaryModal(
    isVisible: Boolean,
    exercises: List<Exercise>,
    onDismiss: () -> Unit,
    onRemoveExercise: (Exercise) -> Unit,
    onEditExercise: (Exercise) -> Unit
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Resumen de la rutina") },
            text = {
                LazyColumn {
                    items(exercises) { exercise ->
                        ExerciseSummaryItem(
                            exercise = exercise,
                            onRemoveExercise = onRemoveExercise,
                            onEditExercise = onEditExercise
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun ExerciseSummaryItem(
    exercise: Exercise,
    onRemoveExercise: (Exercise) -> Unit,
    onEditExercise: (Exercise) -> Unit
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = stringResource(exercise.name))
                Text(text = "${stringResource(R.string.category_exercise)} ${stringResource(exercise.category)}")
                Text(text = "${stringResource(R.string.series)}: ${exercise.numSeries}, ${stringResource(R.string.reps)} ${exercise.numReps}, ${stringResource(R.string.peso)} ${exercise.weight}")
            }
            Row {
                IconButton(onClick = { onEditExercise(exercise) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { onRemoveExercise(exercise) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}