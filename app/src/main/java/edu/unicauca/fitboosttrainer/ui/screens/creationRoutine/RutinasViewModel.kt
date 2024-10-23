package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

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
    // Mantiene el estado de la UI
    private val _uiState = MutableStateFlow(RoutineUiState())
    val uiState: StateFlow<RoutineUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()

    // Guardar temporalmente la rutina creada
    var currentRoutine: Routine? = null
        private set

    // Método para actualizar el nombre de la rutina
    fun onRoutineNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(routineName = newName)
    }

    // Método para actualizar el número de series
    fun onSeriesNumberChange(newSeries: String) {
        _uiState.value = _uiState.value.copy(seriesNumber = newSeries)
    }

    // Método para actualizar el texto de búsqueda
    fun onSearchExerciseChange(newSearch: String) {
        _uiState.value = _uiState.value.copy(searchExercise = newSearch)
        filterExercises(newSearch)
    }
    // Filtra los ejercicios de acuerdo al texto de búsqueda
    private fun filterExercises(query: String) {
        // Como los ejercicios tienen `@StringRes`, necesitamos comparar los recursos de forma adecuada.
        // En el `Composable`, `stringResource()` convierte estos IDs en Strings, por lo que puedes filtrar
        // usando `query.lowercase()` y luego comparar los nombres traducidos en la UI.

        // Actualiza el estado con los ejercicios filtrados
        _uiState.value = _uiState.value.copy(
            filteredExercises = if (query.isEmpty()) {
                ExerciseData
            } else {
                ExerciseData.filter { exercise ->
                    // En este punto, solo puedes filtrar por recursos en la UI con `stringResource()`
                    true // Dejar la comparación vacía aquí ya que debe hacerse en el composable
                }
            }
        )
    }

        // Método para agregar un ejercicio seleccionado
    fun addExercise(exercise: Exercise) {
            _uiState.value = _uiState.value.copy(selectedExercises = _uiState.value.selectedExercises + exercise)
    }

    // Guardar rutina en Firebase
    fun saveRoutine(name: String, series: Int) {
        val newRoutine = Routine(
            name = name,
            series = series,
            exercises = _uiState.value.selectedExercises
        )

        viewModelScope.launch {
            firestore.collection("rutinasGuardadas")
                .add(newRoutine)
                .addOnSuccessListener {
                    // Acciones en caso de éxito, como resetear la UI
                }
                .addOnFailureListener { exception ->
                    // Manejo de errores
                }
        }
    }

    // Guardar la rutina antes de navegar a la pantalla de resumen
    fun saveRoutineData(name: String, series: Int) {
        currentRoutine = Routine(
            name = name,
            series = series,
            exercises = _uiState.value.selectedExercises
        )
        println("Rutina guardada: $currentRoutine")
    }

    fun removeExercise(exercise: Exercise) {
        _uiState.value = _uiState.value.copy(
            selectedExercises = _uiState.value.selectedExercises.filter { it != exercise }
        )
    }
}

