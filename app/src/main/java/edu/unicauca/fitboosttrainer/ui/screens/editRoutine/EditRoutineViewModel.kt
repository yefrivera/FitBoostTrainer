package edu.unicauca.fitboosttrainer.ui.screens.editRoutine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import edu.unicauca.fitboosttrainer.data.Exercise
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditRoutineViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var routineName by mutableStateOf("")
        private set

    var exercises by mutableStateOf<List<Exercise>>(emptyList())

    var isLoading by mutableStateOf(true)
        private set

    private var routineId: String = ""


    fun loadRoutine(routineId: String) {
        this.routineId = routineId
        viewModelScope.launch {
            try {
                isLoading = true
                val routineDoc = firestore.collection("rutinasGuardadas").document(routineId).get().await()
                routineName = routineDoc.getString("name") ?: ""
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

    fun updateRoutineName(newName: String) {
        routineName = newName
    }

    fun updateExercise(updatedExercise: Exercise) {
        exercises = exercises.map { exercise ->
            if (exercise.name == updatedExercise.name) {
                updatedExercise
            } else {
                exercise
            }
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            try {
                isLoading = true

                val updatedRoutine = mapOf(
                    "name" to routineName,
                    "exercises" to exercises.map { exercise ->
                        mapOf(
                            "name" to exercise.name,
                            "category" to exercise.category,
                            "imageRes" to exercise.imageRes,
                            "numSeries" to exercise.numSeries,
                            "numReps" to exercise.numReps,
                            "weight" to exercise.weight
                        )
                    }
                )

                firestore.collection("rutinasGuardadas").document(routineId)
                    .update(updatedRoutine)
                    .await()

            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}