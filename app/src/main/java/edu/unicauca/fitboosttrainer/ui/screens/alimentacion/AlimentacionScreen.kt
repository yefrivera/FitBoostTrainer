package edu.unicauca.fitboosttrainer.ui.screens.alimentacion

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlimentacionScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    drawerState: DrawerState,
    viewModel: AlimentacionViewModel = viewModel()
) {
    val selectedNavItem by viewModel.selectedNavItem.collectAsState()
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = "Alimentación",
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = selectedNavItem,
                onItemSelected = { viewModel.onNavItemSelected(it) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta de información con imagen
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food),
                        contentDescription = "Imagen",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(text = stringResource(R.string.title_food), style = MaterialTheme.typography.titleLarge)
                        Text(text = stringResource(R.string.desc_food))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de categorías con acciones específicas
            CategoryItem(title = stringResource(R.string.snack)) {
                //navController.navigate("snackScreen")
            }
            CategoryItem(title = stringResource(R.string.desayuno)) {
                //navController.navigate("desayunoScreen")
            }
            CategoryItem(title = stringResource(R.string.almuerzo)) {
                //navController.navigate("almuerzoScreen")
            }
            CategoryItem(title = stringResource(R.string.cena)) {
                //navController.navigate("cenaScreen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de seguimiento calórico
            Button(
                onClick = { navController.navigate("caloriasScreen") },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.seguimiento_calorico))
            }
        }
    }
}

@Composable
fun CategoryItem(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),  // Cambiado para aceptar la acción personalizada
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Ir a detalles")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AlimentacionScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    AlimentacionScreen(scrollBehavior = scrollBehavior, navController = navController, drawerState = drawerState)
}
