package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

import edu.unicauca.fitboosttrainer.data.Exercise
import edu.unicauca.fitboosttrainer.data.ExerciseData

data class RoutineUiState(
    val routineName: String = "",
    val seriesNumber: String = "",
    val searchExercise: String = "",
    val selectedExercises: List<Exercise> = emptyList(),
    val filteredExercises: List<Exercise> = ExerciseData,
    val isRoutineCompleted: Boolean = false
)