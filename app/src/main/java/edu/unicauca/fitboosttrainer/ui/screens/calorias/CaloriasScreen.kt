package edu.unicauca.fitboosttrainer.ui.screens.calorias

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.screens.home.HomeViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriasScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: FoodViewModel = viewModel()
) {
    val uiState = viewModel.uiState.value

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(BottomNavItem.ALIMENTACION) }

    LaunchedEffect(Unit) {
        viewModel.fetchDailyGoalCalories()
    }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Calorías",
                scrollBehavior = scrollBehavior,
                drawerState = drawerState,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedItem.value,
                onItemSelected = { selectedItem.value = it },
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
            DailyGoalSection(uiState, viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            AddFoodSection(viewModel, snackbarHostState, coroutineScope, uiState)
            Spacer(modifier = Modifier.height(16.dp))
            AddedFoodsSection(uiState, viewModel)
        }
    }
}

@Composable
fun DailyGoalSection(uiState: CaloriasUIState, viewModel: FoodViewModel) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val progress = (uiState.totalCalories / uiState.dailyGoalCalories.toFloat()).coerceIn(0f, 1f)
    val barColor = if (uiState.totalCalories > uiState.dailyGoalCalories) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    if (uiState.totalCalories > uiState.dailyGoalCalories && !showWarning) {
        showWarning = true
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(3000)
                showWarning = false
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Meta Diaria: ${uiState.dailyGoalCalories} kcal",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Meta Diaria")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${uiState.totalCalories} kcal")
                Spacer(modifier = Modifier.width(8.dp))

                LinearProgressIndicator(
                    progress = progress,
                    color = barColor,
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .border(1.dp, androidx.compose.ui.graphics.Color.Gray, RoundedCornerShape(6.dp))
                        .clip(RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${uiState.dailyGoalCalories} kcal")
            }

            if (showWarning) {
                Text(
                    text = "Has rebasado tu límite de calorías diarias",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                )
            }
        }
    }

    if (showEditDialog) {
        EditDailyGoalDialog(
            currentGoal = uiState.dailyGoalCalories,
            onConfirm = { newGoal ->
                showEditDialog = false
                coroutineScope.launch {
                    //viewModel.addDailyGoalToFirebase(newGoal)
                }
            },
            onCancel = { showEditDialog = false },
            viewModel = viewModel,
            coroutineScope = coroutineScope
        )
    }
}

@Composable
fun EditDailyGoalDialog(
    currentGoal: Int,
    onConfirm: (Int) -> Unit,
    onCancel: () -> Unit,
    viewModel: FoodViewModel,
    coroutineScope: CoroutineScope
) {
    var newGoal by remember { mutableStateOf(currentGoal.toString()) }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("FitBoostTrainer") },
        text = {
            Column {
                OutlinedTextField(
                    value = newGoal,
                    onValueChange = {
                        newGoal = it
                        showError = false
                    },
                    label = { Text("Nueva Meta de Calorías (kcal)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = showError
                )

                if (showError) {
                    Text(
                        text = "Por favor ingrese un valor válido.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val newGoalInt = newGoal.toIntOrNull()
                if (newGoalInt != null && newGoalInt > 0) {
                    coroutineScope.launch {
                        viewModel.addDailyGoalToFirebase(newGoalInt)
                    }

                    onConfirm(newGoalInt)
                } else {
                    showError = true
                }
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodSection(
    viewModel: FoodViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    uiState: CaloriasUIState
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val gramsFocusRequester = remember { FocusRequester() }
    val caloriesFocusRequester = remember { FocusRequester() }
    var showSuggestions by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Agregar Comida", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = showSuggestions,
                onExpandedChange = { showSuggestions = !showSuggestions }
            ) {
                OutlinedTextField(
                    value = uiState.foodName,
                    onValueChange = {
                        viewModel.setFoodName(it)
                        showSuggestions = it.isNotEmpty() && uiState.suggestions.isNotEmpty()
                    },
                    label = { Text("Comida", fontStyle = FontStyle.Italic) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Comida") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(onNext = { gramsFocusRequester.requestFocus() })
                )

                ExposedDropdownMenu(
                    expanded = showSuggestions,
                    onDismissRequest = { showSuggestions = false }
                ) {
                    uiState.suggestions.forEach { suggestion ->
                        DropdownMenuItem(
                            text = { Text(suggestion["name"].toString()) },
                            onClick = {
                                viewModel.selectSuggestedFood(suggestion)
                                showSuggestions = false
                                gramsFocusRequester.requestFocus()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.foodGrams,
                onValueChange = { viewModel.setFoodGrams(it) },
                label = { Text("Cantidad (Gramos)", fontStyle = FontStyle.Italic) },
                leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Gramos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(gramsFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { caloriesFocusRequester.requestFocus() })
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.foodCalories,
                onValueChange = { viewModel.setFoodCalories(it) },
                label = { Text("Calorías (kcal)", fontStyle = FontStyle.Italic) },
                leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Calorías") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(caloriesFocusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (uiState.foodName.isEmpty() ||
                            uiState.foodGrams.isEmpty() ||
                            uiState.foodCalories.isEmpty()) {
                            keyboardController?.hide()
                            snackbarHostState.showSnackbar("Por favor, diligencie todos los campos")
                        } else {
                            viewModel.addFoodToFirebase()
                            keyboardController?.hide()
                            snackbarHostState.showSnackbar("Comida agregada correctamente")
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "+ Añadir")
            }
        }
    }
}

@Composable
fun AddedFoodsSection(uiState: CaloriasUIState, viewModel: FoodViewModel) {
    var foodToEdit by remember { mutableStateOf<Map<String, Any>?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var foodToDelete by remember { mutableStateOf<Map<String, Any>?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Comidas Agregadas", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.addedFoods.isEmpty()) {
                Text("No hay alimentos agregados")
            } else {
                uiState.addedFoods.forEach { food ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = food["name"].toString(), modifier = Modifier.padding(2.dp))
                                Text(text = "${food["grams"].toString()} gr", modifier = Modifier.padding(2.dp))
                                Text(text = "${food["calories"].toString()} kcal", modifier = Modifier.padding(2.dp))
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {
                                    foodToEdit = food
                                    showEditDialog = true
                                }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = {
                                    foodToDelete = food
                                    showDeleteDialog = true
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog && foodToDelete != null) {
        DeleteConfirmationDialog(
            onConfirm = {
                viewModel.deleteFoodFromFirebase(foodToDelete!!)
                showDeleteDialog = false
                foodToDelete = null
            },
            onCancel = {
                showDeleteDialog = false
                foodToDelete = null
            }
        )
    }

    if (showEditDialog && foodToEdit != null) {
        EditFoodDialog(
            food = foodToEdit!!,
            onConfirm = { updatedFood ->
                viewModel.updateFoodInFirebase(updatedFood)
                showEditDialog = false
                foodToEdit = null
            },
            onCancel = {
                showEditDialog = false
                foodToEdit = null
            }
        )
    }
}

@Composable
fun EditFoodDialog(
    food: Map<String, Any>,
    onConfirm: (Map<String, Any>) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(food["name"].toString()) }
    var grams by remember { mutableStateOf(food["grams"].toString()) }
    var calories by remember { mutableStateOf(food["calories"].toString()) }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("FitBoostTrainer") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        showError = false
                    },
                    label = { Text("Comida") },
                    isError = name.isEmpty()
                )
                OutlinedTextField(
                    value = grams,
                    onValueChange = {
                        grams = it
                        showError = false
                    },
                    label = { Text("Cantidad (Gramos)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = grams.isEmpty()
                )
                OutlinedTextField(
                    value = calories,
                    onValueChange = {
                        calories = it
                        showError = false
                    },
                    label = { Text("Calorías (kcal)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = calories.isEmpty()
                )
                if (showError) {
                    Text(
                        text = "Todos los campos son obligatorios.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotEmpty() && grams.isNotEmpty() && calories.isNotEmpty()) {
                    val updatedFood = food.toMutableMap()
                    updatedFood["name"] = name
                    updatedFood["grams"] = grams
                    updatedFood["calories"] = calories
                    onConfirm(updatedFood)
                } else {
                    showError = true
                }
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("FitBoostTrainer") },
        text = { Text("¿Estás seguro de que quieres eliminar esta comida?") },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("Cancelar")
            }
        }
    )
}

