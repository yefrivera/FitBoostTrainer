package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import edu.unicauca.fitboosttrainer.data.Exercise
import edu.unicauca.fitboosttrainer.data.ExerciseData
import edu.unicauca.fitboosttrainer.data.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RoutineViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoutineUiState())
    val uiState: StateFlow<RoutineUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()

    var currentRoutine: Routine? = null
        private set

    var modalSeries by mutableStateOf("")
        private set
    var modalReps by mutableStateOf("")
        private set
    var modalWeight by mutableStateOf("")
        private set

    fun onRoutineNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(routineName = newName)
    }

    fun onSearchExerciseChange(newSearch: String) {
        _uiState.value = _uiState.value.copy(searchExercise = newSearch)
        filterExercises(newSearch)
    }

    private fun filterExercises(query: String) {
        _uiState.value = _uiState.value.copy(
            filteredExercises = if (query.isEmpty()) {
                ExerciseData
            } else {
                ExerciseData.filter { exercise ->
                    true
                }
            }
        )
    }

    fun onSeriesChange(newSeries: String) {
        modalSeries = newSeries
    }

    fun onRepsChange(newReps: String) {
        modalReps = newReps
    }

    fun onWeightChange(newWeight: String) {
        modalWeight = newWeight
    }

    fun areModalFieldsComplete(): Boolean {
        return modalSeries.isNotEmpty() && modalReps.isNotEmpty() && modalWeight.isNotEmpty()
    }

    fun addExerciseWithDetails(exercise: Exercise) {
        if (areModalFieldsComplete()) {
            val exerciseWithDetails = exercise.copy(
                numSeries = modalSeries.toIntOrNull() ?: 0,
                numReps = modalReps.toIntOrNull() ?: 0,
                weight = modalWeight
            )

            _uiState.value = _uiState.value.copy(
                selectedExercises = _uiState.value.selectedExercises + exerciseWithDetails
            )
            resetModalFields()
        }
    }

    fun resetModalFields() {
        modalSeries = ""
        modalReps = ""
        modalWeight = ""
    }


    fun removeExercise(exercise: Exercise) {
        _uiState.value = _uiState.value.copy(
            selectedExercises = _uiState.value.selectedExercises.filter { it != exercise }
        )
        currentRoutine = currentRoutine?.copy(exercises = _uiState.value.selectedExercises)
    }

    fun saveRoutine(name: String) {
        val newRoutine = Routine(
            name = name,
            exercises = _uiState.value.selectedExercises
        )

        viewModelScope.launch {
            firestore.collection("rutinasGuardadas")
                .add(newRoutine)
                .addOnSuccessListener {
                }
                .addOnFailureListener { exception ->
                }
            clearRoutineFields()
        }
    }

    private fun clearRoutineFields() {
        _uiState.value = _uiState.value.copy(
            routineName = "",
            selectedExercises = emptyList()
        )
        currentRoutine = null
    }

    var editingExercise: Exercise? = null

    fun loadExerciseForEditing(exercise: Exercise) {
        editingExercise = exercise
        modalSeries = exercise.numSeries.toString()
        modalReps = exercise.numReps.toString()
        modalWeight = exercise.weight
    }

    fun updateExercise() {
        val updatedExercise = editingExercise?.copy(
            numSeries = modalSeries.toIntOrNull() ?: 0,
            numReps = modalReps.toIntOrNull() ?: 0,
            weight = modalWeight
        )

        if (updatedExercise != null) {
            _uiState.value = _uiState.value.copy(
                selectedExercises = _uiState.value.selectedExercises.map {
                    if (it == editingExercise) updatedExercise else it
                }
            )
            editingExercise = null
            resetModalFields()
        }
    }
}




