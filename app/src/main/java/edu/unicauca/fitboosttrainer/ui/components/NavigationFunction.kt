package edu.unicauca.fitboosttrainer.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.screens.AlimentacionScreen
import edu.unicauca.fitboosttrainer.ui.screens.CaloriasScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationFunction() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Definir el NavHost para manejar las pantallas
    NavHost(navController = navController, startDestination = "alimentacionScreen") {
        // Pantalla de Alimentación
        composable("alimentacionScreen") {
            AlimentacionScreen(navController = navController,drawerState = drawerState, scrollBehavior = scrollBehavior
            )
        }
        // Pantalla de calorías
        composable("caloriasScreen") {
            CaloriasScreen(navController = navController)
        }
    }
}