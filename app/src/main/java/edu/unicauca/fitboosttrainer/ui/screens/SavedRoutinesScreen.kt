package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRoutinesScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MainTopAppBar(modifier = Modifier, scrollBehavior,
            title = stringResource(R.string.saved_routines), drawerState = drawerState)
        },
        bottomBar = { BottomNavigation() }
    ) {
        innerPadding ->
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
        SavedRoutinesScreen()
    }
}