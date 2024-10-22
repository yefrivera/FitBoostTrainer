
package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FoodViewModel : ViewModel() {
    var foodName = mutableStateOf("")
    var foodGrams = mutableStateOf("")
    var foodCalories = mutableStateOf("")
    var mealType = mutableStateOf("Desayuno")

    // Instancia de Firebase Firestore
    private val db = FirebaseFirestore.getInstance()

    fun setFoodName(newName: String) {
        foodName.value = newName
    }

    fun setFoodGrams(newGrams: String) {
        foodGrams.value = newGrams
    }

    fun setFoodCalories(newCalories: String) {
        foodCalories.value = newCalories
    }

    fun setMealType(newMealType: String) {
        mealType.value = newMealType
    }

    // Función para limpiar los campos
    fun clearFields() {
        foodName.value = ""
        foodGrams.value = ""
        foodCalories.value = ""
        mealType.value = "Desayuno"
    }

    // Función para agregar los datos a Firebase Firestore
    suspend fun addFoodToFirebase(): Boolean {
        // Crear el mapa de datos a enviar
        val foodData = hashMapOf(
            "name" to foodName.value,
            "grams" to foodGrams.value,
            "calories" to foodCalories.value,
            "mealType" to mealType.value
        )

        return try {
            db.collection("comidas")
                .document("comidas")  // Refieres al documento llamado "comidas" dentro de la colección
                .set(foodData)        // En lugar de .add() usas .set() para establecer los datos en ese documento
                .await() // Espera que la tarea termine
            true
        } catch (e: Exception) {
            // En caso de error, regresa falso
            false
        }
    }
}
