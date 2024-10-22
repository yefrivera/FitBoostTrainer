package edu.unicauca.fitboosttrainer.ui.screens.calorias

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriasScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: FoodViewModel = viewModel()
) {
    val selectedItem = remember { mutableStateOf(BottomNavItem.ALIMENTACION) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Calorías",
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
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
            DailyGoalSection(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            AddFoodSection(viewModel, snackbarHostState, coroutineScope)
            Spacer(modifier = Modifier.height(16.dp))
            AddedFoodsSection(viewModel)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DailyGoalSection(viewModel: FoodViewModel) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val progress = (viewModel.totalCalories.value / viewModel.dailyGoalCalories.value.toFloat()).coerceIn(0f, 1f)
    val barColor = if (viewModel.totalCalories.value > viewModel.dailyGoalCalories.value) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    if (viewModel.totalCalories.value > viewModel.dailyGoalCalories.value && !showWarning) {
        showWarning = true
        coroutineScope.launch {
            delay(3000)
            showWarning = false
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
                    text = "Meta Diaria: ${viewModel.dailyGoalCalories.value} kcal",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Meta Diaria")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${viewModel.totalCalories.value} kcal")
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
                Text(text = "${viewModel.dailyGoalCalories.value} kcal")
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
            currentGoal = viewModel.dailyGoalCalories.value,
            onConfirm = { newGoal ->
                viewModel.updateDailyGoalCalories(newGoal)
                showEditDialog = false
            },
            onCancel = {
                showEditDialog = false
            }
        )
    }
}

@Composable
fun EditDailyGoalDialog(
    currentGoal: Int,
    onConfirm: (Int) -> Unit,
    onCancel: () -> Unit
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
fun AddFoodSection(viewModel: FoodViewModel, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
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

            // Usamos ExposedDropdownMenuBox en lugar de DropdownMenu para evitar cerrar el teclado
            ExposedDropdownMenuBox(
                expanded = showSuggestions,
                onExpandedChange = {
                    showSuggestions = !showSuggestions
                }
            ) {
                OutlinedTextField(
                    value = viewModel.foodName.value,
                    onValueChange = {
                        viewModel.setFoodName(it)
                        showSuggestions = it.isNotEmpty() && viewModel.suggestions.isNotEmpty()
                    },
                    label = { Text("Comida", fontStyle = FontStyle.Italic) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Comida") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(), // Importante para anclar el menú
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        gramsFocusRequester.requestFocus() // Mover el foco al campo "Gramos"
                    })
                )

                // Mostrar sugerencias sin cerrar el teclado
                ExposedDropdownMenu(
                    expanded = showSuggestions,
                    onDismissRequest = { showSuggestions = false }
                ) {
                    viewModel.suggestions.forEach { suggestion ->
                        DropdownMenuItem(
                            text = { Text(suggestion["name"].toString()) },
                            onClick = {
                                viewModel.selectSuggestedFood(suggestion)
                                showSuggestions = false
                                gramsFocusRequester.requestFocus() // Mover el foco al campo Gramos
                                // Cerrar el teclado si lo deseas aquí
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Gramos
            OutlinedTextField(
                value = viewModel.foodGrams.value,
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
                keyboardActions = KeyboardActions(onNext = {
                    caloriesFocusRequester.requestFocus()
                })
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Calorías
            OutlinedTextField(
                value = viewModel.foodCalories.value,
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

            Button(onClick = {
                coroutineScope.launch {
                    if (viewModel.foodName.value.isEmpty() ||
                        viewModel.foodGrams.value.isEmpty() ||
                        viewModel.foodCalories.value.isEmpty()) {
                        keyboardController?.hide()

                        snackbarHostState.showSnackbar("Por favor, diligencie todos los campos")
                    } else {
                        val isSuccess = viewModel.addFoodToFirebase()
                        if (isSuccess) {
                            viewModel.clearFields()
                            keyboardController?.hide()
                            snackbarHostState.showSnackbar("Comida agregada correctamente")
                        } else {
                            snackbarHostState.showSnackbar("Error al agregar la comida")
                        }
                    }
                }
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "+ Añadir")
            }
        }
    }
}

/*@Composable
fun DropdownMenuExample(viewModel: FoodViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = viewModel.mealType.value)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Desayuno") }, onClick = {
                viewModel.setMealType("Desayuno")
                expanded = false
            })
            DropdownMenuItem(text = { Text("Almuerzo") }, onClick = {
                viewModel.setMealType("Almuerzo")
                expanded = false
            })
            DropdownMenuItem(text = { Text("Cena") }, onClick = {
                viewModel.setMealType("Cena")
                expanded = false
            })
            DropdownMenuItem(text = { Text("Snack") }, onClick = {
                viewModel.setMealType("Snack")
                expanded = false
            })
        }
    }
}*/

@Composable
fun AddedFoodsSection(viewModel: FoodViewModel) {
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

            if (viewModel.addedFoods.isEmpty()) {
                Text("No hay alimentos agregados")
            } else {
                viewModel.addedFoods.forEach { food ->
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
                            /*Text(
                                text = "${food["mealType"].toString()}",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(Alignment.CenterHorizontally)
                            )*/
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "${food["name"].toString()}", modifier = Modifier.padding(2.dp))
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
    //var mealType by remember { mutableStateOf(food["mealType"].toString()) }
    var expanded by remember { mutableStateOf(false) }
    //val mealTypes = listOf("Desayuno", "Almuerzo", "Cena", "Snack")
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
                /*Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { expanded = true }) {
                        Text(text = mealType)
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        mealTypes.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    mealType = option
                                    expanded = false
                                    showError = false
                                }
                            )
                        }
                    }
                }*/
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
                if (name.isNotEmpty() && grams.isNotEmpty() && calories.isNotEmpty() /*&& mealType.isNotEmpty()*/) {
                    val updatedFood = food.toMutableMap()
                    updatedFood["name"] = name
                    updatedFood["grams"] = grams
                    updatedFood["calories"] = calories
                    //updatedFood["mealType"] = mealType
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