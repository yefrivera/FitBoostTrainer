package edu.unicauca.fitboosttrainer.ui.screens.creationRoutine

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoutineScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController,
    routineViewModel: RoutineViewModel = viewModel()
) {

    val uiState by routineViewModel.uiState.collectAsState()
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTopAppBarAlt(
                title = stringResource(R.string.create_routine),
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
    ) { innerPadding ->
        ScrollContent(
            innerPadding = innerPadding,
            viewModel = routineViewModel,
            navController = navController,
            uiState = uiState
        )
    }
}

@Composable
private fun ScrollContent(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: RoutineViewModel,
    uiState: RoutineUiState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {

        // Campo de búsqueda
        OutlinedTextField(
            value = uiState.searchExercise,
            onValueChange = { viewModel.onSearchExerciseChange(it) },
            label = { Text("Buscar ejercicio") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Nombre de la rutina
        OutlinedTextField(
            value = uiState.routineName,
            onValueChange = { viewModel.onRoutineNameChange(it) },
            label = { Text("Nombre de la rutina") },
            placeholder = { Text("Ej: Rutina de pierna") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista filtrada de ejercicios
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.filteredExercises) { item ->
                // Aquí es donde puedes usar `stringResource()` para realizar comparaciones
                val exerciseName = stringResource(id = item.nameExercise)

                if (exerciseName.contains(uiState.searchExercise, ignoreCase = true)) {
                    ExerciseItem(
                        nameExercise = item.nameExercise,
                        categoryExercise = item.categoryExercise,
                        imageRes = item.imageRes,
                        onAddExercise = {
                            viewModel.addExercise(item)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Número de series
        OutlinedTextField(
            value = uiState.seriesNumber,
            onValueChange = { viewModel.onSeriesNumberChange(it) },
            label = { Text("Número de series a realizar") },
            placeholder = { Text("Ej: 3-4") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar número de ejercicios seleccionados
        Text(text = "Ejercicios seleccionados: ${uiState.selectedExercises.size}")

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Finalizar
        Button(
            onClick = {
                // Guardar rutina en Firebase
                val series = uiState.seriesNumber.toIntOrNull() ?: 0
                viewModel.saveRoutine(uiState.routineName, series)
                //navController.navigate("routineSummary")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(48.dp).align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Ver Resumen", color = Color.White)
        }
    }
}

@Composable
fun ExerciseItem(
    @StringRes nameExercise: Int,
    @StringRes categoryExercise: Int,
    @DrawableRes imageRes: Int,
    onAddExercise: () -> Unit // Pasar callback para agregar ejercicio
) {
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
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(nameExercise), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = stringResource(categoryExercise), fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = onAddExercise) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}