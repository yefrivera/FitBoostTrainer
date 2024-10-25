package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.data.Exercise

@Composable
fun ExerciseDetailsModal(
    isVisible: Boolean,               // Estado para mostrar el modal
    modalSeries: String,              // Campo para series
    exercise: Exercise?,              // Ejercicio que se va a editar (puede ser null si es nuevo)
    modalReps: String,                // Campo para repeticiones
    modalWeight: String,              // Campo para peso
    onSeriesChange: (String) -> Unit, // Función para cambiar series
    onRepsChange: (String) -> Unit,   // Función para cambiar repeticiones
    onWeightChange: (String) -> Unit, // Función para cambiar peso
    onConfirm: () -> Unit,            // Función para confirmar la adición
    onDismiss: () -> Unit,            // Función para cancelar o cerrar el modal
    isFormComplete: Boolean           // Habilitar botón si los campos están completos
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(R.string.add_details_exercise)) },
            text = {
                Column {
                    OutlinedTextField(
                        value = modalSeries,
                        onValueChange = { onSeriesChange(it) },
                        label = { Text(stringResource(R.string.number_of_series)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = modalReps,
                        onValueChange = { onRepsChange(it) },
                        label = { Text(stringResource(R.string.number_of_reps)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = modalWeight,
                        onValueChange = { onWeightChange(it) },
                        label = { Text(stringResource(R.string.weight)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,       // Llamar a onConfirm cuando se agrega el ejercicio
                    enabled = isFormComplete    // Habilitar solo si el formulario está completo
                ) {
                    Text(stringResource(R.string.modificar))
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss         // Llamar a onDismiss para cancelar o cerrar el modal
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}