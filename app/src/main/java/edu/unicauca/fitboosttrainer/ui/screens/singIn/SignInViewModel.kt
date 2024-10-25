package edu.unicauca.fitboosttrainer.ui.screens.singIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val loading = MutableLiveData(false)
    val errorMessage = MutableLiveData("")
    val userID = auth.currentUser?.uid

    fun registerUser(
        email: String, password: String, name: String, day: Int, month: Int, year: Int, height: Int,
        weight: Int, trainingFrequency: String, trainingGoal: String, hombro: Int, pecho: Int,
        bicepIzq: Int, bicepDer: Int, cintura: Int, cadera: Int, musloIzq: Int, musloDer: Int,
        pantorrillaIzq: Int, pantorrillaDer: Int
    ) {
        createUserEmailAndPassword(email, password) { isSuccess ->
            if (isSuccess) {
                // Si la creación del usuario es exitosa, crea el perfil del usuario en Firestore
                createUser(userID, name, day, month, year, listOf(height), listOf(weight), trainingFrequency, trainingGoal,
                    listOf(hombro), listOf(pecho), listOf(bicepIzq), listOf(bicepDer), listOf(cintura),
                    listOf(cadera), listOf(musloIzq), listOf(musloDer), listOf(pantorrillaIzq), listOf(pantorrillaDer))
            }
        }
    }



    fun createUserEmailAndPassword(email: String, password: String, onResult: (Boolean) -> Unit) {
        if (loading.value == false) {
            loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult(true)
                        Log.d("From Login", "createUserWithEmailAndPassword: Exitoso")
                    } else {
                        errorMessage.value = task.exception?.message ?: "Error desconocido"
                        onResult(false)
                        Log.d(
                            "From Login",
                            "createUserWithEmailAndPassword: Fallido"
                        )
                    }
                }
        }

    }

    private fun createUser(
        userID: String?,
        name: String,
        day: Int,
        month: Int,
        year: Int,
        height: List<Int>,
        weight: List<Int>,
        trainingFrequency: String,
        trainingGoal: String,
        hombro: List<Int>,
        pecho: List<Int>,
        bicepIzq: List<Int>,
        bicepDer: List<Int>,
        cintura: List<Int>,
        cadera: List<Int>,
        musloIzq: List<Int>,
        musloDer: List<Int>,
        pantorrillaIzq:List<Int>,
        pantorrillaDer: List<Int>,
    ){

        val user = User (
            userID = userID.toString(),
            name = name,
            day = day,
            month = month,
            year = year,
            height = height,
            weight = weight,
            trainingFrequency = trainingFrequency,
            trainingGoal = trainingGoal,
            hombro = hombro,
            pecho = pecho,
            bicepIzq = bicepIzq,
            bicepDer = bicepDer,
            cintura = cintura,
            cadera = cadera,
            musloIzq = musloIzq,
            musloDer = musloDer,
            pantorrillaIzq = pantorrillaIzq,
            pantorrillaDer = pantorrillaDer
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("From Login", "createUser: Exitoso")
            }
            .addOnFailureListener {
                Log.d("From Login", "createUser: Fallido")
            }
    }
}