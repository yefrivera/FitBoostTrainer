package edu.unicauca.fitboosttrainer.ui.screens.progreso

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProgresoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid ?: ""

    val pecho = MutableLiveData(mutableListOf(90, 92, 94))
    val espalda = MutableLiveData(mutableListOf(85, 87, 89))
    val bicepIzq = MutableLiveData(mutableListOf(30, 32, 34))
    val bicepDer = MutableLiveData(mutableListOf(30, 32, 33))
    val cintura = MutableLiveData(mutableListOf(70, 72, 74))
    val cadera = MutableLiveData(mutableListOf(90, 91, 92))
    val musloIzq = MutableLiveData(mutableListOf(50, 51, 52))
    val musloDer = MutableLiveData(mutableListOf(50, 51, 53))
    val weight = MutableLiveData(mutableListOf(65, 66, 67))
    val height = MutableLiveData(mutableListOf(170, 171))

    val selectedGraph = MutableLiveData(1)
    val selectedMeasure = MutableLiveData("pecho")

    // Guardar una medida actualizada en Firestore
    private fun saveToFirestore(measureName: String, newValue: List<Int>) {
        db.collection("users").document(userID).update(measureName, newValue)
            .addOnSuccessListener {
                // Guardado exitoso
            }
            .addOnFailureListener { e ->
                // Error al guardar
            }
    }

    // Agregar un nuevo valor a la lista correspondiente y guardar en Firestore
    fun addValueToMeasure(value: Int) {
        when (selectedMeasure.value) {
            "pecho" -> {
                pecho.value?.add(value)
                saveToFirestore("pecho", pecho.value ?: listOf())
            }
            "espalda" -> {
                espalda.value?.add(value)
                saveToFirestore("espalda", espalda.value ?: listOf())
            }
            "bicepIzq" -> {
                bicepIzq.value?.add(value)
                saveToFirestore("bicepIzq", bicepIzq.value ?: listOf())
            }
            "bicepDer" -> {
                bicepDer.value?.add(value)
                saveToFirestore("bicepDer", bicepDer.value ?: listOf())
            }
            "cintura" -> {
                cintura.value?.add(value)
                saveToFirestore("cintura", cintura.value ?: listOf())
            }
            "cadera" -> {
                cadera.value?.add(value)
                saveToFirestore("cadera", cadera.value ?: listOf())
            }
            "musloIzq" -> {
                musloIzq.value?.add(value)
                saveToFirestore("musloIzq", musloIzq.value ?: listOf())
            }
            "musloDer" -> {
                musloDer.value?.add(value)
                saveToFirestore("musloDer", musloDer.value ?: listOf())
            }
            "weight" -> {
                weight.value?.add(value)
                saveToFirestore("weight", weight.value ?: listOf())
            }
            "height" -> {
                height.value?.add(value)
                saveToFirestore("height", height.value ?: listOf())
            }
        }
    }
}
