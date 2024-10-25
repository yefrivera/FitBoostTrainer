package edu.unicauca.fitboosttrainer.ui.screens.calorias

data class CaloriasUIState(
    val foodName: String = "",
    val foodGrams: String = "",
    val foodCalories: String = "",
    val addedFoods: List<Map<String, Any>> = emptyList(),
    val suggestions: List<Map<String, Any>> = emptyList(),
    val dailyGoalCalories: Int = 0,
    val totalCalories: Int = 0
)