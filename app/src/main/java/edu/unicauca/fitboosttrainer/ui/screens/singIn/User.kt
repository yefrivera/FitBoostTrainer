package edu.unicauca.fitboosttrainer.ui.screens.singIn

data class User(
    val userID: String,
    val name: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val height: List<Int>,
    val weight: List<Int>,
    val trainingFrequency: String,
    val trainingGoal: String,
    val hombro: List<Int>,
    val pecho: List<Int>,
    val bicepIzq: List<Int>,
    val bicepDer: List<Int>,
    val cintura: List<Int>,
    val cadera: List<Int>,
    val musloIzq: List<Int>,
    val musloDer: List<Int>,
    val pantorrillaIzq: List<Int>,
    val pantorrillaDer: List<Int>
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
