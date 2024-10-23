package edu.unicauca.fitboosttrainer.ui.screens.logIn

import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val errorMessage: String) : AuthResult<Nothing>()
}

class AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth}

    suspend fun singInAnonymously(): AuthResult<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            AuthResult.Success(result.user ?: throw Exception("Error al iniciar sesión"))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Error al iniciar sesión")
        }
    }
}