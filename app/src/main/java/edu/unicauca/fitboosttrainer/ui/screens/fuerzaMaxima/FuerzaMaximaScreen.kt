package edu.unicauca.fitboosttrainer.ui.screens.fuerzaMaxima

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuerzaMaximaScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: FuerzaMaximaViewModel = viewModel()
) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Fuerza Máxima",
                scrollBehavior = scrollBehavior,
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
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Ejercicios (Series X Repeticiones)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            uiState.exercises.forEach { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onWeightChange = { newWeight ->
                        viewModel.updateExerciseWeight(exercise.id, newWeight)
                    },
                    onCheckedChange = { isChecked ->
                        viewModel.updateExerciseChecked(exercise.id, isChecked)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.isWorkoutComplete()) {
                            viewModel.sendWorkoutToFirebase()
                            navController.navigate("trainCompletedScreen")
                        } else {
                            snackbarHostState.showSnackbar("Aún no has completado tu entrenamiento")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(text = "Finalizar")
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onWeightChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = exercise.imageRes),
                contentDescription = "Imagen de ejercicio",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = exercise.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Peso
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Peso:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        TextField(
                            value = exercise.weight,
                            onValueChange = { onWeightChange(it) },
                            placeholder = {
                                Text(
                                    text = "0",
                                    fontSize = 12.sp
                                )
                            },
                            modifier = Modifier
                                .width(60.dp)
                                .padding(end = 8.dp),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                        Text(text = "kg", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Reps: ${exercise.reps}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }

            Checkbox(
                checked = exercise.isChecked,
                onCheckedChange = { onCheckedChange(it) },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



