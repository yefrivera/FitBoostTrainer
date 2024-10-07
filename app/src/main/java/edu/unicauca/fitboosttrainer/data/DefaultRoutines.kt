package edu.unicauca.fitboosttrainer.data


import androidx.annotation.StringRes
import edu.unicauca.fitboosttrainer.R


val defaultRoutineData = listOf(
    R.string.dr_fullbody to R.string.dr_fullbody_description,
    R.string.dr_tren_superior to R.string.dr_tren_superior_description,
    R.string.dr_tren_inferior to R.string.dr_tren_inferior_description,
    R.string.dr_abdomen to R.string.dr_abdomen_description
).map { RoutineDefaultPair(it.first, it.second) }

data class RoutineDefaultPair(
    @StringRes val text1: Int,
    @StringRes val text2: Int
)