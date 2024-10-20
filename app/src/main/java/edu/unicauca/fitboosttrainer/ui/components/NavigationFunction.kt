package edu.unicauca.fitboosttrainer.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.LoginScreen
import edu.unicauca.fitboosttrainer.ui.components.DrawerContent
import edu.unicauca.fitboosttrainer.ui.components.InitialScreen
import edu.unicauca.fitboosttrainer.ui.screens.*
import edu.unicauca.fitboosttrainer.ui.screens.CreationRoutine.CreateRoutineScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationFunction() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Lista de pantallas donde NO quieres que el drawer se muestre
    val screensWithoutDrawer = listOf(
        "welcome",
        "login",
        "trainCompletedScreen"
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    val shouldShowDrawer = currentDestination !in screensWithoutDrawer

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (shouldShowDrawer) {
                DrawerContent()
            }
        },
        gesturesEnabled = shouldShowDrawer
    ) {
        NavHost(navController = navController, startDestination = "welcome") {

            // Pantalla de Bienvenida (sin drawer)
            composable("welcome") {
                InitialScreen(navController = navController)
            }

            // Pantalla de Login (sin drawer)
            composable("login") {
                LoginScreen( drawerState = drawerState, navController = navController)
            }

            // Pantalla de inicio (Home) con drawer
            composable("home") {
                Home(
                    userName = "John",
                    drawerState = drawerState,
                    navController = navController,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Alimentación con drawer
            composable("alimentacionScreen") {
                AlimentacionScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Calorías con drawer
            composable("caloriasScreen") {
                CaloriasScreen(
                    scrollBehavior = scrollBehavior,
                    drawerState = drawerState,
                    navController = navController
                )
            }

            // Pantalla de Full Body con drawer
            composable("fullBodyScreen") {
                FullBodyScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Fuerza Máxima con drawer
            composable("fuerzaMaximaScreen") {
                FuerzaMaximaScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Entrenamiento Finalizado (sin drawer)
            composable("trainCompletedScreen") {
                TrainCompletedScreen(navController = navController)
            }

            // Pantalla de Crear Rutinas con drawer
            composable("crearRutinasHome") {
                HomeScreen(
                    navController = navController,
                    drawerState = drawerState
                )
            }

            // Pantalla de Creación de Rutinas con drawer
            composable("creationRoutine") {
                CreateRoutineScreen(
                    navController = navController,
                    drawerState = drawerState,
                    scrollBehavior = scrollBehavior
                )
            }

            // Pantalla de Rutinas Guardadas con drawer
            composable("savedRoutine") {
                SavedRoutinesScreen(
                    navController = navController,
                    drawerState = drawerState
                )
            }
        }
    }
}
