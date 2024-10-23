package edu.unicauca.fitboosttrainer.ui.screens.fuerzaMaxima

import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FuerzaMaximaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FuerzaMaximaUIState())
    val uiState: StateFlow<FuerzaMaximaUIState> = _uiState

    init {
        // Initialize with default exercises
        val defaultExercises = listOf(
            Exercise(id = 1, title = "Press Banca", imageRes = R.drawable.banca, reps = "(3 x 10)"),
            Exercise(id = 2, title = "Press Militar", imageRes = R.drawable.militar, reps = "(2 x 10)"),
            Exercise(id = 3, title = "Remo", imageRes = R.drawable.remo, reps = "(2 x 10)"),
            Exercise(id = 4, title = "Curl", imageRes = R.drawable.curl, reps = "(2 x 10)"),
            Exercise(id = 5, title = "Sentadilla", imageRes = R.drawable.sentadilla, reps = "(3 x 10)"),
            Exercise(id = 6, title = "Peso Muerto", imageRes = R.drawable.muerto, reps = "(2 x 10)")
        )
        _uiState.value = FuerzaMaximaUIState(exercises = defaultExercises)
    }

    // Function to update weight for an exercise
    fun updateExerciseWeight(exerciseId: Int, newWeight: String) {
        val updatedExercises = _uiState.value.exercises.map { exercise ->
            if (exercise.id == exerciseId) {
                exercise.copy(weight = newWeight)
            } else {
                exercise
            }
        }
        _uiState.value = _uiState.value.copy(exercises = updatedExercises)
    }

    // Function to update checked status for an exercise
    fun updateExerciseChecked(exerciseId: Int, isChecked: Boolean) {
        val updatedExercises = _uiState.value.exercises.map { exercise ->
            if (exercise.id == exerciseId) {
                exercise.copy(isChecked = isChecked)
            } else {
                exercise
            }
        }
        _uiState.value = _uiState.value.copy(exercises = updatedExercises)
    }
}