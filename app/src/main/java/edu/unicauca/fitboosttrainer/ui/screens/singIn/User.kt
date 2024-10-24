package edu.unicauca.fitboosttrainer.ui.screens.singIn

data class User(
    val userID: String,
    val name: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val height: Int,
    val weight: Int,
    val trainingFrequency: String,
    val trainingGoal: String,
    val hombro: Int,
    val pecho: Int,
    val bicepIzq: Int,
    val bicepDer: Int,
    val cintura: Int,
    val cadera: Int,
    val musloIzq: Int,
    val musloDer: Int,
    val pantorrillaIzq: Int,
    val pantorrillaDer: Int
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "name" to this.name,
            "day" to this.day,
            "month" to this.month,
            "year" to this.year,
            "height" to this.height,
            "weight" to this.weight,
            "trainingFrequency" to this.trainingFrequency,
            "trainingGoal" to this.trainingGoal,
            "hombro" to this.hombro,
            "pecho" to this.pecho,
            "bicepIzq" to this.bicepIzq,
            "bicepDer" to this.bicepDer,
            "cintura" to this.cintura,
            "cadera" to this.cadera,
            "musloIzq" to this.musloIzq,
            "musloDer" to this.musloDer,
            "pantorrillaIzq" to this.pantorrillaIzq,
            "pantorrillaDer" to this.pantorrillaDer
        )


    }
}
