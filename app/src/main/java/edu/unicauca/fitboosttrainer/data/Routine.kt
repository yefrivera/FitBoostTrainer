package edu.unicauca.fitboosttrainer.data

data class Routine(
    val name: String,
    val series: Int,
    val exercises: List<Exercise>
)