package edu.unicauca.fitboosttrainer.ui.screens.logIn

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
