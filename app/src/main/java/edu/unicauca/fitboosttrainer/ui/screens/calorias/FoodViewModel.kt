package edu.unicauca.fitboosttrainer.ui.screens.calorias

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class FoodViewModel : ViewModel() {
    var foodName = mutableStateOf("")
    var foodGrams = mutableStateOf("")
    var foodCalories = mutableStateOf("")
    //var mealType = mutableStateOf("Desayuno")

    var addedFoods = mutableStateListOf<Map<String, Any>>()
    var suggestions = mutableStateListOf<Map<String, Any>>()

    private val db = FirebaseFirestore.getInstance()
    private var firestoreListener: ListenerRegistration? = null

    var dailyGoalCalories = mutableStateOf(1000)
    var totalCalories = mutableStateOf(0)

    init {
        fetchAddedFoods()
    }

    fun setFoodName(newName: String) {
        foodName.value = newName
        fetchFoodSuggestions(newName)
    }

    fun setFoodGrams(newGrams: String) {
        foodGrams.value = newGrams
        calculateCalories()
    }

    fun setFoodCalories(newCalories: String) {
        foodCalories.value = newCalories
    }

    /*fun setMealType(newMealType: String) {
        mealType.value = newMealType
    }*/

    fun clearFields() {
        foodName.value = ""
        foodGrams.value = ""
        foodCalories.value = ""
        //mealType.value = "Desayuno"
    }

    suspend fun addFoodToFirebase(): Boolean {
        val foodData = hashMapOf(
            "name" to foodName.value,
            "grams" to foodGrams.value,
            "calories" to foodCalories.value,
            //"mealType" to mealType.value
        )

        return try {
            db.collection("comidas")
                .add(foodData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun fetchAddedFoods() {
        firestoreListener = db.collection("comidas")
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener
                addedFoods.clear()
                var total = 0

                snapshots?.documents?.forEach { document ->
                    document.data?.let { food ->
                        addedFoods.add(food.plus("id" to document.id))
                        total += (food["calories"].toString().toIntOrNull() ?: 0)
                    }
                }

                totalCalories.value = total
            }
    }

    private fun calculateCalories() {
        val grams = foodGrams.value.toIntOrNull() ?: 100
        val baseCalories = suggestions.firstOrNull { it["name"] == foodName.value }?.get("calories").toString().toIntOrNull() ?: 0
        val updatedCalories = (baseCalories * grams) / 100
        foodCalories.value = updatedCalories.toString()
    }

    fun updateFoodInFirebase(updatedFood: Map<String, Any>) {
        val documentId = updatedFood["id"].toString()
        db.collection("comidas")
            .document(documentId)
            .update(
                "name", updatedFood["name"].toString(),
                "grams", updatedFood["grams"].toString(),
                "calories", updatedFood["calories"].toString(),
                //"mealType", updatedFood["mealType"].toString()
            )
            .addOnSuccessListener {
                val index = addedFoods.indexOfFirst { it["id"] == documentId }
                if (index != -1) {
                    addedFoods[index] = updatedFood
                }
            }
    }

    fun deleteFoodFromFirebase(food: Map<String, Any>) {
        val documentId = food["id"].toString()
        db.collection("comidas")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                addedFoods.remove(food)
            }
            .addOnFailureListener {

            }
    }

    override fun onCleared() {
        super.onCleared()
        firestoreListener?.remove()
    }

    fun updateDailyGoalCalories(newGoal: Int) {
        dailyGoalCalories.value = newGoal
    }

    // Buscar sugerencias de comida por nombre
    private fun fetchFoodSuggestions(query: String) {
        if (query.isEmpty()) {
            suggestions.clear()
            return
        }

        db.collection("comidasGuardadas")
            .whereGreaterThanOrEqualTo("name", query)
            .whereLessThanOrEqualTo("name", "$query\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                suggestions.clear()
                for (document in documents) {
                    suggestions.add(document.data)
                }
            }
            .addOnFailureListener {
                suggestions.clear()
            }
    }

    fun selectSuggestedFood(food: Map<String, Any>) {
        foodName.value = food["name"].toString()
        foodGrams.value = "100" // Siempre es 100 gramos por defecto
        foodCalories.value = food["calories"].toString()
    }
}

