package edu.unicauca.fitboosttrainer.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import androidx.compose.ui.Modifier
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.data.defaultRoutineData
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBar
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.DrawerApp
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,  // Agregar el parámetro navController
    drawerState: DrawerState,
    scrollBehavior: TopAppBarScrollBehavior
) {

    //val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    //val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }


    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Crear Rutinas",
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
        ScrollContent2(innerPadding = innerPadding)
    }
    }

@Composable
 fun RoutinesCard(
    @StringRes text: Int,
    @StringRes text2: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {


        Card(

            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(text),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(text2),
                )
            }
        }

    }
}

@Composable
fun RoutinesList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(defaultRoutineData) { item ->
            RoutinesCard(item.text1, item.text2)  // Asegúrate de que item.text y item.text2 sean Int
        }
    }
}

@Composable
fun ScrollContent2(innerPadding: PaddingValues) {

    val mediumPadding = 8.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = { /* Acción al hacer clic */ },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(end = mediumPadding)
            ) {
                Text(text = " + Crear Rutinas")
            }
            Button(
                onClick = { /* Acción al hacer clic */ },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Rutinas Guardadas")
            }
        }

        Text(
            text = stringResource(R.string.default_routines),
            modifier = Modifier.padding(mediumPadding)
        )
        RoutinesList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    FitBoostTrainerTheme {
        HomeScreen(
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}
