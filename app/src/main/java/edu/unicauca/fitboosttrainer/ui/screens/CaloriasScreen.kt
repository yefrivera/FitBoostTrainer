
package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.FoodViewModel
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriasScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController,
    viewModel: FoodViewModel = viewModel() // Aquí obtenemos el ViewModel
) {
    val selectedItem = remember { mutableStateOf(BottomNavItem.ALIMENTACION) }

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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            DailyGoalSection()
            Spacer(modifier = Modifier.height(16.dp))
            AddFoodSection(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            AddedFoodsSection()
        }
    }
}

@Composable
fun DailyGoalSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Meta Diaria", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "750 kcal")
                Spacer(modifier = Modifier.width(8.dp))
                LinearProgressIndicator(
                    progress = 0.75f,
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .border(1.dp, androidx.compose.ui.graphics.Color.Gray, RoundedCornerShape(6.dp))
                        .clip(RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "1000 kcal")
            }
        }
    }
}

@Composable
fun AddFoodSection(viewModel: FoodViewModel) {
    val snackbarHostState = remember { SnackbarHostState() } // Estado del Snackbar
    val coroutineScope = rememberCoroutineScope() // Corutina para ejecutar operaciones asíncronas

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Agregar Comida", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de nombre de la comida
            OutlinedTextField(
                value = viewModel.foodName.value,
                onValueChange = { viewModel.setFoodName(it) },
                label = { Text("Comida", fontStyle = FontStyle.Italic) },
                leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Comida") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de gramos
            OutlinedTextField(
                value = viewModel.foodGrams.value,
                onValueChange = { viewModel.setFoodGrams(it) },
                label = { Text("Gramos", fontStyle = FontStyle.Italic) },
                leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Gramos") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de calorías
            OutlinedTextField(
                value = viewModel.foodCalories.value,
                onValueChange = { viewModel.setFoodCalories(it) },
                label = { Text("Calorías", fontStyle = FontStyle.Italic) },
                leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Calorías") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenuExample(viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            // Al hacer clic en el botón, llamamos a la función que agrega los datos a Firebase
            Button(onClick = {
                coroutineScope.launch {
                    val isSuccess = viewModel.addFoodToFirebase()
                    if (isSuccess) {
                        viewModel.clearFields() // Limpiar los campos después de agregar los datos
                        snackbarHostState.showSnackbar("Datos agregados correctamente")
                    } else {
                        snackbarHostState.showSnackbar("Error al agregar los datos")
                    }
                }
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "+ Añadir")
            }
        }
    }

    // Snackbar Host para mostrar mensajes
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
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
        }
    }
}

@Composable
fun AddedFoodsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Comidas Agregadas", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Comida ejemplo 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Caldo de pollo", modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Editar comida */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { /* Eliminar comida */ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }

            // Comida ejemplo 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Arroz", modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Editar comida */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { /* Eliminar comida */ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewCaloriasScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    CaloriasScreen(scrollBehavior = scrollBehavior, drawerState = drawerState, navController = navController)
}
