package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.res.stringResource
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriasScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf(BottomNavItem.ALIMENTACION) }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Calorías",
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState,
                onBackClick = { navController.popBackStack() }  // Usamos popBackStack para navegar hacia atrás
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
                .padding(16.dp)
        ) {

            DailyGoalSection()
            Spacer(modifier = Modifier.height(16.dp))
            AddFoodSection()
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
            Text(text = stringResource(R.string.meta_diaria), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "750 kcal")
                Spacer(modifier = Modifier.width(8.dp))
                LinearProgressIndicator(
                    progress = 0.75f, // Simulamos que se ha alcanzado el 75% de la meta
                    modifier = Modifier
                        .weight(1f)
                        .height(12.dp)
                        .border(1.dp, androidx.compose.ui.graphics.Color.Gray,RoundedCornerShape(6.dp))
                        .clip(RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "1000 kcal")
            }
        }
    }
}

@Composable
fun AddFoodSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.agregar_comida), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Buscar comida", fontStyle = FontStyle.Italic ) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,  // Ícono de lupa
                            contentDescription = "Buscar"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically, // Alineamos verticalmente en el centro
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuExample()  // No es necesario modificar el peso, solo alineación
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Calorías", fontStyle = FontStyle.Italic ) },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Añadir acción */ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "+ Añadir")
            }
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
            Text(text = stringResource(R.string.comidas_agregadas), style = MaterialTheme.typography.titleLarge)
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

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Desayuno") }

    Box {
        TextButton(
            onClick = { expanded = true }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = selectedText)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, // Ícono de flecha hacia abajo
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier.size(24.dp)
                )

            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Desayuno") },
                onClick = {
                    selectedText = "Desayuno"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Almuerzo") },
                onClick = {
                    selectedText = "Almuerzo"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Cena") },
                onClick = {
                    selectedText = "Cena"
                    expanded = false
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCaloriasScreen() {
    var navController = rememberNavController()
    CaloriasScreen(navController= navController)
}