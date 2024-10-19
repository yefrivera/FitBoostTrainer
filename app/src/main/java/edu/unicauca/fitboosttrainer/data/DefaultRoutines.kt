package edu.unicauca.fitboosttrainer.data

import androidx.annotation.StringRes
import edu.unicauca.fitboosttrainer.R

// Define las rutas para cada rutina
val defaultRoutineData = listOf(
    RoutineDefault(
        text1 = R.string.dr_fullbody,
        text2 = R.string.dr_fullbody_description,
        route = "fullBodyScreen"
    ),
    RoutineDefault(
        text1 = R.string.dr_tren_superior,
        text2 = R.string.dr_tren_superior_description,
        route = "fullBodyScreen"
    ),
    RoutineDefault(
        text1 = R.string.dr_tren_inferior,
        text2 = R.string.dr_tren_inferior_description,
        route = "fullBodyScreen"
    ),
    RoutineDefault(
        text1 = R.string.dr_abdomen,
        text2 = R.string.dr_abdomen_description,
        route = "fullBodyScreen"
    )
)

data class RoutineDefault(
    @StringRes val text1: Int,
    @StringRes val text2: Int,
    val route: String
)