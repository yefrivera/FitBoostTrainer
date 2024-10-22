package edu.unicauca.fitboosttrainer.ui.screens.singIn
import edu.unicauca.fitboosttrainer.ui.screens.singIn.MeasuresUIState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MeasuresViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(MeasuresUIState())
    val uiState: StateFlow<MeasuresUIState> = _uiState

    fun updateMeasure(name: String, value: String) {
        val newState = when (name) {
            "hombro" -> _uiState.value.copy(hombro = value)
            "bicepIzq" -> _uiState.value.copy(bicepIzq = value)
            "cintura" -> _uiState.value.copy(cintura = value)
            "cadera" -> _uiState.value.copy(cadera = value)
            "pantorrillaIzq" -> _uiState.value.copy(pantorrillaIzq = value)
            "musloIzq" -> _uiState.value.copy(musloIzq = value)
            "pecho" -> _uiState.value.copy(pecho = value)
            "bicepDer" -> _uiState.value.copy(bicepDer = value)
            "musloDer" -> _uiState.value.copy(musloDer = value)
            "pantorrillaDer" -> _uiState.value.copy(pantorrillaDer = value)
            else -> _uiState.value
        }
        _uiState.value = newState
    }
}