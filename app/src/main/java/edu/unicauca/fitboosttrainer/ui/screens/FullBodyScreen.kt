package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullBodyScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navController: NavHostController
) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Full Body",
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Lista de rutinas
            RoutineItem(
                title = stringResource(R.string.strength_max),
                description = stringResource(R.string.strength_max_d),
                onClick = { navController.navigate("fuerzaMaximaScreen") }
            )
            RoutineItem(
                title = stringResource(R.string.suelo_pie),
                description = stringResource(R.string.suelo_pie_d),
                onClick = { /*navController.navigate("sueloPieDetail")*/ }
            )
            RoutineItem(
                title = stringResource(R.string.desafio_explosivo),
                description = stringResource(R.string.desafio_explosivo_d),
                onClick = { /*navController.navigate("desafioExplosivoDetail")*/ }
            )
            RoutineItem(
                title = stringResource(R.string.cardio_letal),
                description = stringResource(R.string.cardio_letal_d),
                onClick = { /*navController.navigate("cardioLetalDetail")*/ }
            )
        }
    }
}

@Composable
fun RoutineItem(title: String, description: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor =  Color(0xFFDDE3E7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ir a detalles de rutina",
                tint = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FullBodyScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navController = rememberNavController()
    FullBodyScreen(scrollBehavior = scrollBehavior, drawerState = drawerState, navController= navController)

}
