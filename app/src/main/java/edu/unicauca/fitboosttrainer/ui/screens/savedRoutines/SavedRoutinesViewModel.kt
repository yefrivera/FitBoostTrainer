package edu.unicauca.fitboosttrainer.ui.screens.savedRoutines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class SavedRoutine(
    val id: String = "",
    val name: String = "",
    val series: Int = 0
)

class SavedRoutineViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var savedRoutines by mutableStateOf<List<SavedRoutine>>(emptyList())
        private set

    var isLoading by mutableStateOf(true) // Estado de carga
        private set

    init {
        // Cargar las rutinas al inicializar el ViewModel
        loadSavedRoutines()
    }

    // Función para cargar las rutinas guardadas del usuario
    fun loadSavedRoutines() {
        viewModelScope.launch {
            try {
                isLoading = true  // Comienza la carga
                val routinesSnapshot = firestore.collection("rutinasGuardadas")
                    .get()
                    .await()

                val routines = routinesSnapshot.documents.map { doc ->
                    SavedRoutine(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        series = doc.getLong("series")?.toInt() ?: 0
                    )
                }

                savedRoutines = routines
            } catch (e: Exception) {
                // Manejo de errores si es necesario
            } finally {
                isLoading = false  // Termina la carga
            }
        }
    }
}