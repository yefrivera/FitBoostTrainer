package edu.unicauca.fitboosttrainer.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    // Simulando la información del usuario y el progreso
    var userName = mutableStateOf("John")
    var caloriesBurned = mutableStateOf(1500)
    var completedWorkouts = mutableStateOf(3)
    var totalWorkouts = mutableStateOf(5)

    // Método para actualizar los datos de progreso
    fun updateProgress(calories: Int, completed: Int, total: Int) {
        caloriesBurned.value = calories
        completedWorkouts.value = completed
        totalWorkouts.value = total
    }
}