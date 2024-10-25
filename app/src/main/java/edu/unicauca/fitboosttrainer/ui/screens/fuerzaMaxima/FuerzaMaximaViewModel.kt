package edu.unicauca.fitboosttrainer.ui.screens.fuerzaMaxima

import androidx.lifecycle.ViewModel
import edu.unicauca.fitboosttrainer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

open class FuerzaMaximaViewModel : ViewModel() {
    val _uiState = MutableStateFlow(FuerzaMaximaUIState())
    val uiState: StateFlow<FuerzaMaximaUIState> = _uiState

    private val db = FirebaseFirestore.getInstance()

    init {
        val defaultExercises = listOf(
            Exercise(id = 1, title = "Press Banca", imageRes = R.drawable.banca, reps = "(3 x 10)"),
            Exercise(id = 2, title = "Press Militar", imageRes = R.drawable.militar, reps = "(2 x 10)"),
            Exercise(id = 3, title = "Remo", imageRes = R.drawable.remo, reps = "(2 x 10)"),
            Exercise(id = 4, title = "Curl", imageRes = R.drawable.curl, reps = "(2 x 10)"),
            Exercise(id = 5, title = "Sentadilla", imageRes = R.drawable.sentadilla, reps = "(3 x 10)"),
            Exercise(id = 6, title = "Peso Muerto", imageRes = R.drawable.muerto, reps = "(2 x 10)")
        )
        _uiState.value = FuerzaMaximaUIState(exercises = defaultExercises)
    }

    fun updateExerciseWeight(exerciseId: Int, newWeight: String) {
        val updatedExercises = _uiState.value.exercises.map { exercise ->
            if (exercise.id == exerciseId) {
                exercise.copy(weight = newWeight)
            } else {
                exercise
            }
        }
        _uiState.value = _uiState.value.copy(exercises = updatedExercises)
    }

    fun updateExerciseChecked(exerciseId: Int, isChecked: Boolean) {
        val updatedExercises = _uiState.value.exercises.map { exercise ->
            if (exercise.id == exerciseId) {
                exercise.copy(isChecked = isChecked)
            } else {
                exercise
            }
        }
        _uiState.value = _uiState.value.copy(exercises = updatedExercises)
    }

    fun isWorkoutComplete(): Boolean {
        return _uiState.value.exercises.all { it.weight.isNotEmpty() && it.isChecked }
    }

    suspend fun sendWorkoutToFirebase() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val exercisesData = _uiState.value.exercises.map { exercise ->
            mapOf(
                "nombre" to exercise.title,
                "peso" to exercise.weight,
                "reps" to exercise.reps
            )
        }

        val workoutSession = mapOf(
            "date" to currentDate,
            "exercises" to exercisesData
        )

        try {
            db.collection("fuerzaMaxima").add(workoutSession).await()
        } catch (e: Exception) {

        }
    }
}
