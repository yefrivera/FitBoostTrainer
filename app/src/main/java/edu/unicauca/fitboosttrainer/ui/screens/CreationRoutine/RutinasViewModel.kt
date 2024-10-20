package edu.unicauca.fitboosttrainer.ui.screens.CreationRoutine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.data.Exercise
import edu.unicauca.fitboosttrainer.data.ExerciseData


class RoutineViewModel : ViewModel() {
    // Mantiene el estado de la UI
    var uiState by mutableStateOf(RoutineUiState())
        private set

    var searchExercise by mutableStateOf("")
        private set
    var routineName by mutableStateOf("")
        private set
    var seriesNumber by mutableStateOf("")
        private set

    // Método para actualizar el nombre de la rutina
    fun onRoutineNameChange(newName: String) {
        uiState = uiState.copy(routineName = newName)
    }

    // Método para actualizar el número de series
    fun onSeriesNumberChange(newSeries: String) {
        uiState = uiState.copy(seriesNumber = newSeries)
    }

    // Método para actualizar el texto de búsqueda
    fun onSearchExerciseChange(newSearch: String) {
        uiState = uiState.copy(searchExercise = newSearch)
        filterExercises(newSearch)
    }

    // Filtra los ejercicios de acuerdo al texto de búsqueda
    private fun filterExercises(query: String) {
        // Como los ejercicios tienen `@StringRes`, necesitamos comparar los recursos de forma adecuada.
        // En el `Composable`, `stringResource()` convierte estos IDs en Strings, por lo que puedes filtrar
        // usando `query.lowercase()` y luego comparar los nombres traducidos en la UI.

        // Actualiza el estado con los ejercicios filtrados
        uiState = uiState.copy(
            filteredExercises = if (query.isEmpty()) {
                ExerciseData
            } else {
                ExerciseData.filter { exercise ->
                    // En este punto, solo puedes filtrar por recursos en la UI con `stringResource()`
                    true // Dejar la comparación vacía aquí ya que debe hacerse en el composable
                }
            }
        )
    }

    // Método para agregar un ejercicio seleccionado
    fun addExercise(exercise: Exercise) {
        uiState = uiState.copy(selectedExercises = uiState.selectedExercises + exercise)
    }
}

