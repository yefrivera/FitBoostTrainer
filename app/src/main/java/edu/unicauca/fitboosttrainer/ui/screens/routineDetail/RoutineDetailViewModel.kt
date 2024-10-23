package edu.unicauca.fitboosttrainer.ui.screens.routineDetail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class Exercise(
    @StringRes val name: Int = 0,
    @StringRes val category: Int = 0,
    @DrawableRes val imageRes: Int = 0
)

class RoutineDetailViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var exercises by mutableStateOf<List<Exercise>>(emptyList())
        private set

    var routineName by mutableStateOf("") // Nombre de la rutina
        private set

    var isLoading by mutableStateOf(true) // Estado de carga
        private set

    // Cargar los ejercicios de la rutina seleccionada
    fun loadRoutineExercises(routineId: String) {
        viewModelScope.launch {
            try {
                isLoading = true  // Empieza la carga
                val routineDoc = firestore.collection("rutinasGuardadas").document(routineId).get().await()

                // Obtener el nombre de la rutina
                routineName = routineDoc.getString("name") ?: "Rutina"

                val exercisesList = routineDoc.get("exercises") as List<Map<String, Any>>

                exercises = exercisesList.map { exerciseMap ->
                    Exercise(
                        name = (exerciseMap["nameExercise"] as Long).toInt(),
                        category = (exerciseMap["categoryExercise"] as Long).toInt(),
                        imageRes = (exerciseMap["imageRes"] as Long).toInt()
                    )
                }
            } catch (e: Exception) {
                // Manejar errores si es necesario
            } finally {
                isLoading = false  // Termina la carga
            }
        }
    }
}