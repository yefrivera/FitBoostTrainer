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
    val name: String = ""
)

class SavedRoutineViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    var savedRoutines by mutableStateOf<List<SavedRoutine>>(emptyList())
        private set

    var isLoading by mutableStateOf(true) // Estado de carga
        private set

    init {
        loadSavedRoutines()
    }

    private fun loadSavedRoutines() {
        viewModelScope.launch {
            try {
                isLoading = true
                val routinesSnapshot = firestore.collection("rutinasGuardadas")
                    .get()
                    .await()

                val routines = routinesSnapshot.documents.map { doc ->
                    SavedRoutine(
                        id = doc.id,
                        name = doc.getString("name") ?: ""
                    )
                }
                savedRoutines = routines
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }


    // Función para eliminar rutina y recargar la lista
    fun deleteRoutine(routineId: String) {
        viewModelScope.launch {
            try {

                firestore.collection("rutinasGuardadas").document(routineId).delete().await()
                loadSavedRoutines()

            } catch (e: Exception) {

            }
        }
    }
}