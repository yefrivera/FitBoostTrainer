package edu.unicauca.fitboosttrainer.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _caloriesBurned = MutableStateFlow(0)
    val caloriesBurned: StateFlow<Int> = _caloriesBurned

    private val _completedWorkouts = MutableStateFlow(0)
    val completedWorkouts: StateFlow<Int> = _completedWorkouts

    private val _totalWorkouts = MutableStateFlow(7)
    val totalWorkouts: StateFlow<Int> = _totalWorkouts

    private val _dailyGoalCalories = MutableStateFlow(2000)
    val dailyGoalCalories: StateFlow<Int> = _dailyGoalCalories

    private val _totalCalories = MutableStateFlow(0)
    val totalCalories: StateFlow<Int> = _totalCalories

}

