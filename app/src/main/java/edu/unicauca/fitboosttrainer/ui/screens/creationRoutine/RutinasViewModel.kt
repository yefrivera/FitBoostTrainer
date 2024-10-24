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

    // Guardar temporalmente la rutina creada
    var currentRoutine: Routine? = null
        private set

    // Estado para controlar el modal de ejercicios
    var modalSeries by mutableStateOf("")
        private set
    var modalReps by mutableStateOf("")
        private set
    var modalWeight by mutableStateOf("")
        private set

    // Método para actualizar el nombre de la rutina
    fun onRoutineNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(routineName = newName)
    }

    // Método para actualizar el texto de búsqueda
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

    // Método para actualizar los campos del modal
    fun onSeriesChange(newSeries: String) {
        modalSeries = newSeries
    }

    fun onRepsChange(newReps: String) {
        modalReps = newReps
    }

    fun onWeightChange(newWeight: String) {
        modalWeight = newWeight
    }

    // Método para verificar si los campos están completos
    fun areModalFieldsComplete(): Boolean {
        return modalSeries.isNotEmpty() && modalReps.isNotEmpty() && modalWeight.isNotEmpty()
    }

    // Método para agregar un ejercicio seleccionado con los detalles del modal
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

            // Limpiar los campos del modal después de añadir el ejercicio
            resetModalFields()
        }
    }

    // Limpiar los campos del modal
    private fun resetModalFields() {
        modalSeries = ""
        modalReps = ""
        modalWeight = ""
    }

    // Guardar rutina en Firebase
    fun saveRoutine(name: String) {
        val newRoutine = Routine(
            name = name,
            exercises = _uiState.value.selectedExercises
        )

        viewModelScope.launch {
            firestore.collection("rutinasGuardadas")
                .add(newRoutine)
                .addOnSuccessListener {
                    // Acciones en caso de éxito
                }
                .addOnFailureListener { exception ->
                    // Manejo de errores
                }
        }
    }

    // Limpiar los campos de la rutina y los ejercicios seleccionados
    fun clearRoutineFields() {
        _uiState.value = _uiState.value.copy(
            routineName = "",
            selectedExercises = emptyList()
        )
    }
}



