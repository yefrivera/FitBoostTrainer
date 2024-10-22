package edu.unicauca.fitboosttrainer.ui.screens.singIn

data class SingInDataUIState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val height: String = "",
    val weight: String = "",
    val trainingFrequency: String = "",
    val trainingGoal: String = ""
)
