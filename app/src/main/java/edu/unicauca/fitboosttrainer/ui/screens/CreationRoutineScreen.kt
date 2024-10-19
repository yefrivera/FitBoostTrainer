package edu.unicauca.fitboosttrainer.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.data.ExerciseData
import edu.unicauca.fitboosttrainer.data.defaultRoutineData
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoutineScreen(scrollBehavior: TopAppBarScrollBehavior,
                        drawerState: DrawerState,
                        navController: NavHostController
) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MainTopAppBar(modifier = Modifier, scrollBehavior,
            title = stringResource(R.string.create_routine), drawerState = drawerState)
        },
        bottomBar = { BottomNavigation(
            selectedItem = selectedNavItem,
            onItemSelected = { selectedNavItem = it },
            navController = navController) }
    ) { innerPadding ->
        ScrollContent(innerPadding = innerPadding, navController= navController)
    }
}

@Composable
private fun ScrollContent(innerPadding: PaddingValues,navController: NavHostController,modifier: Modifier = Modifier) {
    var routineName by remember { mutableStateOf(TextFieldValue("")) }
    var seriesNumber by remember { mutableStateOf(TextFieldValue("")) }
    var searchExercise by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
        
    ) {

        OutlinedTextField(
            value = searchExercise,
            onValueChange = {searchExercise = it },
            label = { Text("Buscar ejercicio" ) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,  // Ícono de lupa
                    contentDescription = "Buscar"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Nombre de la rutina
        OutlinedTextField(
            value = routineName,
            onValueChange = { routineName = it },
            label = { Text("Nombre de la rutina") },
            placeholder = { Text("Ej: Rutina de pierna") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(ExerciseData) { item ->
                ExerciseItem(
                    item.nameExercise,
                    item.categoryExercise,
                    item.imageRes
                )
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
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(48.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Finalizar", color = Color.White)
        }
    }
}

@Composable
fun ExerciseItem(@StringRes nameExercise: Int,
                 @StringRes categoryExercise: Int,
                 @DrawableRes imageRes: Int) {
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
            IconButton(onClick = { /* Acción para agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewCreateRoutineScreen() {
    FitBoostTrainerTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val navController = rememberNavController()
        CreateRoutineScreen(scrollBehavior = scrollBehavior, drawerState = drawerState, navController = navController)
    }
}