package edu.unicauca.fitboosttrainer.ui.screens.savedRoutines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
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
        ScrollContent(innerPadding = innerPadding, viewModel = viewModel)
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues, viewModel: SavedRoutineViewModel = viewModel()) {
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
                    SavedRoutineCard(routine = routine)
                }
            }
        }
    }
}

@Composable
fun SavedRoutineCard(routine: SavedRoutine) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Número de series: ${routine.series}",
                fontSize = 16.sp
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSavedRoutinesScreen() {
    FitBoostTrainerTheme {
        SavedRoutinesScreen(
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}