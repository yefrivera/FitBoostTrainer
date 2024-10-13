package edu.unicauca.fitboosttrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import edu.unicauca.fitboosttrainer.ui.screens.RoutineCreationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitBoostTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RoutineCreationScreen()
                }
                }
            }
        }
}

