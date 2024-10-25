package edu.unicauca.fitboosttrainer.ui.screens.routineDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import edu.unicauca.fitboosttrainer.data.Exercise
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoutineDetailViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var exercises by mutableStateOf<List<Exercise>>(emptyList())
        private set

    var routineName by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadRoutineExercises(routineId: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                val routineDoc = firestore.collection("rutinasGuardadas").document(routineId).get().await()

                routineName = routineDoc.getString("name") ?: "Rutina"

                val exercisesList = routineDoc.get("exercises") as List<Map<String, Any>>

                exercises = exercisesList.map { exerciseMap ->
                    Exercise(
                        name = (exerciseMap["name"] as Long).toInt(),
                        category = (exerciseMap["category"] as Long).toInt(),
                        imageRes = (exerciseMap["imageRes"] as Long).toInt(),
                        numSeries = (exerciseMap["numSeries"] as Long).toInt(),
                        numReps = (exerciseMap["numReps"] as Long).toInt(),
                        weight = exerciseMap["weight"] as String
                    )
                }
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}