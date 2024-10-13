package edu.unicauca.fitboosttrainer.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import androidx.compose.ui.Modifier
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import edu.unicauca.fitboosttrainer.data.defaultRoutineData
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineCreationScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MainTopAppBar(modifier = Modifier, scrollBehavior,title = stringResource(R.string.app_name)) },
        bottomBar = { BottomNavigation() }
    ) { innerPadding ->
        ScrollContent(innerPadding = innerPadding)
    }

}

@Composable
private fun RoutinesCard(
    @StringRes text: Int,
    @StringRes text2: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(text),
                )
                Text(
                    text = stringResource(text2),
                )
            }
        }

    }
}

@Composable
private fun RoutinesList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(defaultRoutineData) { item ->
            RoutinesCard(item.text1, item.text2)  // Asegúrate de que item.text y item.text2 sean Int
        }
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {

    val mediumPadding = 8.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = { /* Acción al hacer clic */ },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(end = mediumPadding)
            ) {
                Text(text = " + Crear Rutinas")
            }
            Button(
                onClick = { /* Acción al hacer clic */ },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Rutinas Guardadas")
            }
        }

        Text(
            text = stringResource(R.string.default_routines),
            modifier = Modifier.padding(mediumPadding)
        )
        RoutinesList()
    }
}

@Preview
@Composable
fun RoutineCreationScreenPreview(){
    FitBoostTrainerTheme {
        RoutineCreationScreen()
    }
}
