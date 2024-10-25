package edu.unicauca.fitboosttrainer.ui.screens.calorias

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class FoodDB() {

    private val db = FirebaseFirestore.getInstance()
    private var firestoreListener: ListenerRegistration? = null


    suspend fun addFood(foodData: Map<String, Any>): Boolean {
        return try {
            db.collection("comidas")
                .add(foodData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun fetchAddedFoods(onFoodAdded: (List<Map<String, Any>>) -> Unit) {
        firestoreListener = db.collection("comidas")
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener

                val foods = mutableListOf<Map<String, Any>>()
                var totalCalories = 0
                snapshots?.documents?.forEach { document ->
                    document.data?.let { food ->
                        foods.add(food.plus("id" to document.id))
                        totalCalories += (food["calories"].toString().toIntOrNull() ?: 0)
                    }
                }
                onFoodAdded(foods)
            }
    }

    fun updateFoodInFirebase(updatedFood: Map<String, Any>, onUpdateSuccess: () -> Unit) {
        val documentId = updatedFood["id"].toString()
        db.collection("comidas")
            .document(documentId)
            .update(
                "name", updatedFood["name"].toString(),
                "grams", updatedFood["grams"].toString(),
                "calories", updatedFood["calories"].toString()
            )
            .addOnSuccessListener { onUpdateSuccess() }
    }

    fun deleteFoodFromFirebase(foodId: String, onDeleteSuccess: () -> Unit) {
        db.collection("comidas")
            .document(foodId)
            .delete()
            .addOnSuccessListener { onDeleteSuccess() }
    }

    fun fetchFoodSuggestions(query: String, onSuggestionsFetched: (List<Map<String, Any>>) -> Unit) {
        if (query.isEmpty()) {
            onSuggestionsFetched(emptyList())
            return
        }

        db.collection("comidasGuardadas")
            .whereGreaterThanOrEqualTo("name", query)
            .whereLessThanOrEqualTo("name", "$query\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val suggestions = documents.map { it.data }
                onSuggestionsFetched(suggestions)
            }
            .addOnFailureListener {
                onSuggestionsFetched(emptyList())
            }
    }

    suspend fun addDailyGoalToFirebase(dailyGoal: Int): Boolean {
        return try {
            val dailyGoalData = mapOf(
                "dailyGoalCalories" to dailyGoal,
                "timestamp" to com.google.firebase.firestore.FieldValue.serverTimestamp()
            )
            db.collection("calorias")
                .add(dailyGoalData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun fetchDailyGoalFromFirebase(onDailyGoalFetched: (Int) -> Unit) {
        db.collection("calorias")  // Cambiado a la colección correcta
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)  // Obtener el último valor añadido
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null || snapshots.isEmpty) {
                    onDailyGoalFetched(2000)  // Valor predeterminado si hay un error o no hay datos
                    return@addSnapshotListener
                }

                val latestGoal = snapshots.documents.firstOrNull()?.get("dailyGoalCalories")?.toString()?.toIntOrNull() ?: 2000
                onDailyGoalFetched(latestGoal)
            }
    }

    fun removeListener() {
        firestoreListener?.remove()
    }
}