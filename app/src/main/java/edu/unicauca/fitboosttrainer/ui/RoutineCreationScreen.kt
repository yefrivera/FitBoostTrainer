package edu.unicauca.fitboosttrainer.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import androidx.compose.ui.Modifier
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import edu.unicauca.fitboosttrainer.data.defaultRoutineData

@Composable
fun RoutineCreationScreen(){

    val mediumPadding = 3.dp
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column() {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding),
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    onClick = { /* Acción al hacer clic */ },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .size(width = 200.dp, height = 50.dp)

                ) {
                    Text(text = " + Crear Rutinas")
                }
                Button(
                    onClick = { /* Acción al hacer clic */ },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .size(width = 200.dp, height = 50.dp)

                ) {
                    Text(text = "Rutinas Guardadas")
                }
            }

            Text(
                text = stringResource(R.string.default_routines),
                style = typography.titleSmall,
                modifier = Modifier.padding(16.dp)
            )
            RoutinesList()
            SootheBottomNavigation()
        }
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
                    style = typography.titleLarge,
                )
                Text(
                    text = stringResource(text2),
                    style = typography.bodySmall,
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
private fun SootheBottomNavigation(
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.alimentacion))
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.rutinas))
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.estadisticas))
            },
            selected = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun RoutineCreationScreenPreview(){
    FitBoostTrainerTheme {
        RoutineCreationScreen()
    }
}
