package edu.unicauca.fitboosttrainer.ui.screens.logIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData("")


    fun login(email: String, password: String,onResult: (Boolean) -> Unit)
    = viewModelScope.launch{
        loading.value = true
        try {
               auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult(true)
                            Log.d("From Login", "signInWithEmailAndPassword: Exitoso")
                        } else {
                            errorMessage.value = task.exception?.message ?: "Error desconocido"
                            onResult(false)
                            Log.d(
                                "From Login",
                                "signInWithEmailAndPassword: Fallido"
                            )

                        }
                    }
            } catch (ex: Exception) {
                Log.d("From Login", "signInWithEmailAndPassword: ${ex.message}")
            }
    }

}
