package edu.unicauca.fitboosttrainer.ui.screens.savedRoutines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRoutinesScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    viewModel: SavedRoutineViewModel = viewModel()
) {

    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = stringResource(R.string.saved_routines),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it },
                navController = navController
            )
        }
    ) {
        innerPadding ->
        ScrollContent(innerPadding = innerPadding, viewModel = viewModel, navController = navController)
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues, viewModel: SavedRoutineViewModel = viewModel(),navController: NavHostController) {
    val savedRoutines = viewModel.savedRoutines
    val isLoading = viewModel.isLoading

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            // Mostrar el indicador de carga si los datos aún se están cargando
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Mostrar las rutinas guardadas si ya están cargadas
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(savedRoutines) { routine ->
                    SavedRoutineCard(routine = routine, onClick = {
                        // Navegar a la pantalla de detalles con el ID de la rutina
                        navController.navigate("routineDetail/${routine.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun SavedRoutineCard(routine: SavedRoutine,
                     onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {onClick()}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = routine.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
