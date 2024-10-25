package edu.unicauca.fitboosttrainer.ui.screens.progreso

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProgresoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val auth = Firebase.auth
    val userID = auth.currentUser?.uid ?: ""

    val pecho = MutableLiveData(mutableListOf(90, 92, 94))
    val espalda = MutableLiveData(mutableListOf(85, 87, 89))
    val bicepIzq = MutableLiveData(mutableListOf(30, 32, 34))
    val bicepDer = MutableLiveData(mutableListOf(30, 32, 33))
    val cintura = MutableLiveData(mutableListOf(70, 72, 74))
    val cadera = MutableLiveData(mutableListOf(90, 91, 92))
    val musloIzq = MutableLiveData(mutableListOf(50, 51, 52))
    val musloDer = MutableLiveData(mutableListOf(50, 51, 53))
    val pantorrillaIzq = MutableLiveData(mutableListOf(63, 62, 61))
    val pantorrillaDer = MutableLiveData(mutableListOf(60, 61, 62))
    val weight = MutableLiveData(mutableListOf(65, 66, 67))
    val height = MutableLiveData(mutableListOf(170, 171))

    val selectedGraph = MutableLiveData(1)
    val selectedMeasure = MutableLiveData("pecho")

    private fun saveToFirestore(measureName: String, newValue: List<Int>) {
        if (userID.isNotEmpty()) {
            db.collection("users").document(userID).update(measureName, newValue)
                .addOnSuccessListener {
                    // Guardado exitoso
                }
                .addOnFailureListener { e ->
                    // Manejar error aquí
                }
        } else {

        }
    }


    fun addValueToMeasure(value: Int) {
        when (selectedMeasure.value) {
            "pecho" -> {
                pecho.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    pecho.value = updatedList
                    saveToFirestore("pecho", updatedList)
                }
            }
            "espalda" -> {
                espalda.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    espalda.value = updatedList
                    saveToFirestore("espalda", updatedList)
                }
            }
            "bicepIzq" -> {
                bicepIzq.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    bicepIzq.value = updatedList
                    saveToFirestore("bicepIzq", updatedList)
                }
            }
            "bicepDer" -> {
                bicepDer.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    bicepDer.value = updatedList
                    saveToFirestore("bicepDer", updatedList)
                }
            }
            "cintura" -> {
                cintura.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    cintura.value = updatedList
                    saveToFirestore("cintura", updatedList)
                }
            }
            "cadera" -> {
                cadera.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    cadera.value = updatedList
                    saveToFirestore("cadera", updatedList)
                }
            }
            "musloIzq" -> {
                musloIzq.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    musloIzq.value = updatedList
                    saveToFirestore("musloIzq", updatedList)
                }
            }
            "musloDer" -> {
                musloDer.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    musloDer.value = updatedList
                    saveToFirestore("musloDer", updatedList)
                }
            }
            "pantorrillaIzq" -> { // Nueva medida
                pantorrillaIzq.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    pantorrillaIzq.value = updatedList
                    saveToFirestore("pantorrillaIzq", updatedList)
                }
            }
            "pantorrillaDer" -> { // Nueva medida
                pantorrillaDer.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    pantorrillaDer.value = updatedList
                    saveToFirestore("pantorrillaDer", updatedList)
                }
            }
            "weight" -> {
                weight.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    weight.value = updatedList
                    saveToFirestore("weight", updatedList)
                }
            }
            "height" -> {
                height.value?.let {
                    val updatedList = it.toMutableList()
                    updatedList.add(value)
                    height.value = updatedList
                    saveToFirestore("height", updatedList)
                }
            }
        }
    }


}
