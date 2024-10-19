package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRoutinesScreen(navController: NavHostController) {

    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MainTopAppBarAlt(
            title = stringResource(R.string.saved_routines),
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            drawerState = drawerState,
            onBackClick = { navController.popBackStack() }
        )
        },
        bottomBar = { BottomNavigation(
            selectedItem = selectedNavItem,
            onItemSelected = { selectedNavItem = it },
            navController = navController) }
    ) { innerPadding ->

        ScrollContent(innerPadding = innerPadding)
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues) {

}

@Preview(showBackground = true)
@Composable
fun PreviewSavedRoutinesScreen() {
    FitBoostTrainerTheme {
        SavedRoutinesScreen(navController = NavHostController(LocalContext.current))
    }
}