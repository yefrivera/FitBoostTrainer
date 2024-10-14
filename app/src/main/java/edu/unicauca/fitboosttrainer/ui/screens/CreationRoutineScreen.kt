package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoutineScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MainTopAppBar(modifier = Modifier, scrollBehavior,
            title = stringResource(R.string.create_routine), drawerState = drawerState)
        },
        bottomBar = { BottomNavigation() }
    ) { innerPadding ->

        ScrollContent(innerPadding = innerPadding)
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues) {
    var routineName by remember { mutableStateOf(TextFieldValue("")) }
    var seriesNumber by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
        
    ) {

        // Nombre de la rutina
        OutlinedTextField(
            value = routineName,
            onValueChange = { routineName = it },
            label = { Text("Nombre") },
            placeholder = { Text("Ej: Rutina de pierna") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de ejercicios
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(getExercises()) { exercise ->
                ExerciseItem(exercise)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Número de series
        OutlinedTextField(
            value = seriesNumber,
            onValueChange = { seriesNumber = it },
            label = { Text("Número de series a realizar") },
            placeholder = { Text("Ej: 3-4") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Finalizar
        Button(
            onClick = { /* Acción al finalizar */ },
            modifier = Modifier
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = "Finalizar", color = Color.White)
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = exercise.imageRes),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = exercise.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = exercise.category, fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = { /* Acción para agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}

data class Exercise(val name: String, val category: String, val imageRes: Int)

fun getExercises(): List<Exercise> {
    return listOf(
        Exercise("Peso muerto", "Pierna", R.drawable.ic_deadlift),
        Exercise("Sentadilla con barra", "Pierna", R.drawable.ic_deadlift),
        Exercise("Bulgara", "Pierna", R.drawable.ic_deadlift),
        Exercise("Peso muerto", "Pierna", R.drawable.ic_deadlift),
        Exercise("Sentadilla con barra", "Pierna", R.drawable.ic_deadlift),
        Exercise("Bulgara", "Pierna", R.drawable.ic_deadlift),
        Exercise("Peso muerto", "Pierna", R.drawable.ic_deadlift),
        Exercise("Sentadilla con barra", "Pierna", R.drawable.ic_deadlift),
        Exercise("Bulgara", "Pierna", R.drawable.ic_deadlift),
        Exercise("Elevación de caderas", "Pierna", R.drawable.ic_deadlift)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateRoutineScreen() {
    FitBoostTrainerTheme {
        CreateRoutineScreen()
    }
}