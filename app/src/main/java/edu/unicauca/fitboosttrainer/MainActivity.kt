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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.navigation.NavigationFunction
import edu.unicauca.fitboosttrainer.ui.components.InitialScreen
import edu.unicauca.fitboosttrainer.ui.screens.Home
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitBoostTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    /*Home(userName = "John", scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(), drawerState = rememberDrawerState(initialValue = DrawerValue.Closed), navController = NavHostController(
                        LocalContext.current)
                    )*/
                    NavigationFunction()
                }
                }
            }
        }
}

