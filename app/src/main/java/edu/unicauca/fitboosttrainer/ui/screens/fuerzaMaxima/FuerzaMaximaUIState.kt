package edu.unicauca.fitboosttrainer.ui.screens.fuerzaMaxima

data class Exercise(
    val id: Int,
    val title: String,
    val imageRes: Int,
    val reps: String,
    val weight: String = "",
    val isChecked: Boolean = false
)

data class FuerzaMaximaUIState(
    val exercises: List<Exercise> = emptyList()
)