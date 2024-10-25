package edu.unicauca.fitboosttrainer.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    // Propiedades existentes
    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _caloriesBurned = MutableStateFlow(0)
    val caloriesBurned: StateFlow<Int> = _caloriesBurned

    private val _completedWorkouts = MutableStateFlow(0)
    val completedWorkouts: StateFlow<Int> = _completedWorkouts

    private val _totalWorkouts = MutableStateFlow(7)  // Ejemplo: 7 entrenamientos por semana
    val totalWorkouts: StateFlow<Int> = _totalWorkouts

    // Propiedades para calorías diarias y consumidas
    private val _dailyGoalCalories = MutableStateFlow(2000)  // Meta calórica diaria predeterminada
    val dailyGoalCalories: StateFlow<Int> = _dailyGoalCalories

    private val _totalCalories = MutableStateFlow(0)  // Calorías consumidas actuales
    val totalCalories: StateFlow<Int> = _totalCalories

    // Actualiza las calorías
    fun updateDailyGoalCalories(newGoal: Int) {
        _dailyGoalCalories.value = newGoal
    }

    fun updateTotalCalories(consumedCalories: Int) {
        _totalCalories.value = consumedCalories
    }
}

