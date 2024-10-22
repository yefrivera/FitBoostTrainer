package edu.unicauca.fitboosttrainer.ui.screens.singIn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SingInDataViewModel : ViewModel() {
        private val _uiState = MutableStateFlow(SingInDataUIState())
        val uiState: StateFlow<SingInDataUIState> = _uiState

        fun updateField(field: String, value: String) {
            val newState = when (field) {
                "name" -> _uiState.value.copy(name = value)
                "email" -> _uiState.value.copy(email = value)
                "password" -> _uiState.value.copy(password = value)
                "day" -> _uiState.value.copy(day = value)
                "month" -> _uiState.value.copy(month = value)
                "year" -> _uiState.value.copy(year = value)
                "height" -> _uiState.value.copy(height = value)
                "weight" -> _uiState.value.copy(weight = value)
                "trainingFrequency" -> _uiState.value.copy(trainingFrequency = value)
                "trainingGoal" -> _uiState.value.copy(trainingGoal = value)
                else -> _uiState.value
            }
            _uiState.value = newState
        }
    }
