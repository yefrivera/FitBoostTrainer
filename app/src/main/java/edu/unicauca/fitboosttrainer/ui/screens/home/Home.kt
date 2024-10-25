package edu.unicauca.fitboosttrainer.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    drawerState: DrawerState,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    homeViewModel: HomeViewModel = viewModel()
) {
    val userName by homeViewModel.userName.collectAsState()
    val dailyGoalCalories by homeViewModel.dailyGoalCalories.collectAsState()
    val totalCalories by homeViewModel.totalCalories.collectAsState()
    val completedWorkouts by homeViewModel.completedWorkouts.collectAsState()
    val totalWorkouts by homeViewModel.totalWorkouts.collectAsState()



    var selectedNavItem by remember { mutableStateOf(BottomNavItem.HOME) }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "FitBoost Trainer",
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState,
                showBackIcon = false
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Hola, $userName \n¿Listo para entrenar hoy?",
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                        .zIndex(1f)
                )
            }

            ProgressSummary(
                dailyGoalCalories = dailyGoalCalories,
                totalCalories = totalCalories,
                completedWorkouts = completedWorkouts,
                totalWorkouts = totalWorkouts
            )

            NewWorkoutButton(navController)
        }
    }
}

@Composable
fun ProgressSummary(dailyGoalCalories: Int, totalCalories: Int, completedWorkouts: Int, totalWorkouts: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tu Progreso", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Objetivo calórico diario", fontSize = 16.sp)
                Text(text = "$dailyGoalCalories kcal", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Calorías consumidas", fontSize = 16.sp)
                Text(text = "$totalCalories kcal", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Entrenamientos completados", fontSize = 16.sp)
                Text(text = "$completedWorkouts/$totalWorkouts", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}



@Composable
fun NewWorkoutButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("crearRutinasHome") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(text = "Registrar Nuevo Entrenamiento")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FitBoostTrainerTheme {
        Home(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed), navController = NavHostController(LocalContext.current), scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior())
    }
}