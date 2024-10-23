package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.LoginScreen
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.screens.*
import edu.unicauca.fitboosttrainer.ui.screens.calorias.CaloriasScreen
import edu.unicauca.fitboosttrainer.ui.screens.creationRoutine.CreateRoutineScreen
import edu.unicauca.fitboosttrainer.ui.screens.creationRoutine.RoutineSummaryScreen
import edu.unicauca.fitboosttrainer.ui.screens.home.Home
import edu.unicauca.fitboosttrainer.ui.screens.logIn.AuthManager
//import edu.unicauca.fitboosttrainer.ui.screens.logIn.AuthManager
import edu.unicauca.fitboosttrainer.ui.screens.routineDetail.RoutineDetailScreen
import edu.unicauca.fitboosttrainer.ui.screens.savedRoutines.SavedRoutinesScreen
import edu.unicauca.fitboosttrainer.ui.screens.singIn.MeasuresScreen
import edu.unicauca.fitboosttrainer.ui.screens.singIn.SingInDataScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationFunction() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val authManager: AuthManager = AuthManager()

    // Lista de pantallas donde NO quieres que el drawer se muestre
    val screensWithoutDrawer = listOf(
        "welcome",
        "LoginScreen",
        "trainCompletedScreen",
        "RegistrerMessureScreen",
        "SingInDataScreen"
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
                InitialScreen(navController = navController, auth = authManager)
            }

            // Pantalla de Login (sin drawer)
            composable("login") {
                LoginScreen( navController = navController)
            }

            //Pantalla SingIn (sin drawer)
            composable("singIn") {
                SingInDataScreen(navController = navController)
            }

            // Pantalla registro de medidas (sin drawer)
            composable("RegistrerMessureScreen") {
                MeasuresScreen(navController = navController)
            }

            // Pantalla de inicio (Home) con drawer
            composable("home") {
                Home(
                    drawerState = drawerState,
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                    homeViewModel = viewModel()
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
                    navController = navController,
                    viewModel = viewModel()
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

            composable("sumaryRoutine"){
                RoutineSummaryScreen(
                    navController = navController
                )
            }


            // Pantalla de Rutinas Guardadas con drawer
            composable("savedRoutine") {
                SavedRoutinesScreen(
                    navController = navController,
                    drawerState = drawerState
                )
            }

            composable("routineDetail/{routineId}") { backStackEntry ->
                val routineId = backStackEntry.arguments?.getString("routineId") ?: ""
                RoutineDetailScreen(
                    navController = navController,
                    drawerState = drawerState,
                    routineId = routineId
                )
            }

            composable("RegisterDoneScreen") {
                CompletedScreen(congratsText = stringResource(R.string.register_done),navController = navController)
            }

            composable("RoutineCreationDoneScreen") {
                CompletedScreen(congratsText = stringResource(R.string.routine_done),navController = navController)
            }

        }
    }
}
