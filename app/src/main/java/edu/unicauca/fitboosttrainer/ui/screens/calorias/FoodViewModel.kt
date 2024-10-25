package edu.unicauca.fitboosttrainer.ui.screens.calorias

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(
    private val foodDB: FoodDB = FoodDB()
) : ViewModel() {

    var uiState = mutableStateOf(CaloriasUIState())
        private set

    init {
        fetchAddedFoods()
    }

    fun setFoodName(newName: String) {
        uiState.value = uiState.value.copy(foodName = newName)
        fetchFoodSuggestions(newName)
    }

    fun setFoodGrams(newGrams: String) {
        uiState.value = uiState.value.copy(foodGrams = newGrams)
        calculateCalories()
    }

    fun setFoodCalories(newCalories: String) {
        uiState.value = uiState.value.copy(foodCalories = newCalories)
    }

    fun clearFields() {
        uiState.value = uiState.value.copy(foodName = "", foodGrams = "", foodCalories = "")
    }

    fun addFoodToFirebase() {
        CoroutineScope(Dispatchers.IO).launch {
            val foodData = mapOf(
                "name" to uiState.value.foodName,
                "grams" to uiState.value.foodGrams,
                "calories" to uiState.value.foodCalories
            )
            val isSuccess = foodDB.addFood(foodData)
            if (isSuccess) {
                clearFields()
            }
        }
    }

    private fun fetchAddedFoods() {
        foodDB.fetchAddedFoods { foods ->
            val totalCalories = foods.sumOf { it["calories"].toString().toIntOrNull() ?: 0 }
            uiState.value = uiState.value.copy(addedFoods = foods, totalCalories = totalCalories)
        }
    }

    private fun calculateCalories() {
        val grams = uiState.value.foodGrams.toIntOrNull() ?: 100
        val baseCalories = uiState.value.suggestions.firstOrNull { it["name"] == uiState.value.foodName }?.get("calories").toString().toIntOrNull() ?: 0
        val updatedCalories = (baseCalories * grams) / 100
        uiState.value = uiState.value.copy(foodCalories = updatedCalories.toString())
    }

    fun updateFoodInFirebase(updatedFood: Map<String, Any>) {
        foodDB.updateFoodInFirebase(updatedFood) {
            val updatedList = uiState.value.addedFoods.map {
                if (it["id"] == updatedFood["id"]) updatedFood else it
            }
            uiState.value = uiState.value.copy(addedFoods = updatedList)
        }
    }

    fun deleteFoodFromFirebase(food: Map<String, Any>) {
        val foodId = food["id"].toString()
        foodDB.deleteFoodFromFirebase(foodId) {
            val updatedList = uiState.value.addedFoods.filter { it["id"] != foodId }
            uiState.value = uiState.value.copy(addedFoods = updatedList)
        }
    }

    // Buscar sugerencias de comida
    private fun fetchFoodSuggestions(query: String) {
        foodDB.fetchFoodSuggestions(query) { suggestions ->
            uiState.value = uiState.value.copy(suggestions = suggestions)
        }
    }

    fun selectSuggestedFood(food: Map<String, Any>) {
        uiState.value = uiState.value.copy(
            foodName = food["name"].toString(),
            foodGrams = "100",
            foodCalories = food["calories"].toString()
        )
    }

    fun updateDailyGoalCalories(newGoal: Int) {
        uiState.value = uiState.value.copy(dailyGoalCalories = newGoal)
    }

    fun addDailyGoalToFirebase(newGoal: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val isSuccess = foodDB.addDailyGoalToFirebase(newGoal)
            if (isSuccess) {
                updateDailyGoalCalories(newGoal)
            }
        }
    }

    fun fetchDailyGoalCalories() {
        CoroutineScope(Dispatchers.IO).launch {
            foodDB.fetchDailyGoalFromFirebase { dailyGoal ->
                uiState.value = uiState.value.copy(dailyGoalCalories = dailyGoal)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        foodDB.removeListener()
    }
}
