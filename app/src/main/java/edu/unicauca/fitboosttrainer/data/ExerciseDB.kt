package edu.unicauca.fitboosttrainer.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import edu.unicauca.fitboosttrainer.R


val ExerciseData = listOf(
        Exercise(R.string.ic_deadlift, R.string.ce_leg, R.drawable.muerto),
        Exercise(R.string.bd_squad, R.string.ce_leg, R.drawable.sentadilla),
        Exercise(R.string.bd_barbel_row, R.string.ce_back, R.drawable.remo),
        Exercise(R.string.bd_biceps_curl, R.string.ce_bicep, R.drawable.curl),
        Exercise(R.string.bd_bench_press, R.string.ce_chest, R.drawable.banca)
)

data class Exercise(@StringRes val name: Int = 0,
                      @StringRes val category: Int = 0,
                      @DrawableRes val imageRes: Int = 0,
                      val numSeries :Int = 0,
                      val numReps: Int = 0,
                      val weight :String = "")