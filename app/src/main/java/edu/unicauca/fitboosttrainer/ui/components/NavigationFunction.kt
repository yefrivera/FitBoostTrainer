package edu.unicauca.fitboosttrainer.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.components.DrawerContent
import edu.unicauca.fitboosttrainer.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationFunction() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        },
    ) {
        // Definir el NavHost para manejar las pantallas
        NavHost(navController = navController, startDestination = "home") {

            // Pantalla de inicio (Home)
            composable("home") {
                Home(
                    userName = "John",
                    drawerState = drawerState,
                    navController = navController,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Alimentación
            composable("alimentacionScreen") {
                AlimentacionScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Calorías
            composable("caloriasScreen") {
                CaloriasScreen(navController = navController)
            }

            // Pantalla de Full Body
            composable("fullBodyScreen") {
                FullBodyScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Fuerza Máxima
            composable("fuerzaMaximaScreen") {
                FuerzaMaximaScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Entrenamiento Finalizado
            composable("trainCompletedScreen") {
                TrainCompletedScreen(navController = navController)
            }

            composable("crearRutinasHome") {
                HomeScreen(
                    navController = navController,
                    drawerState = drawerState
                )
            }

            // Pantalla de creación de Rutinas
            composable("creationRoutine") {
                CreateRoutineScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior)
            }

        }
    }
}
