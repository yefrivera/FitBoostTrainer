package edu.unicauca.fitboosttrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import edu.unicauca.fitboosttrainer.ui.components.InitialScreen
import edu.unicauca.fitboosttrainer.ui.screens.FullBodyScreen
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import edu.unicauca.fitboosttrainer.ui.screens.RoutineCreationScreen
import edu.unicauca.fitboosttrainer.ui.screens.TrainCompletedScreen
import edu.unicauca.fitboosttrainer.ui.screens.FuerzaMaximaScreen


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitBoostTrainerTheme {

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    //RoutineCreationScreen()
                    //InitialScreen()
                    //TrainCompletedScreen()
                    //FullBodyScreen(drawerState = drawerState, scrollBehavior = scrollBehavior)
                    FuerzaMaximaScreen(drawerState = drawerState, scrollBehavior = scrollBehavior)
                }
                }
            }
        }
}

