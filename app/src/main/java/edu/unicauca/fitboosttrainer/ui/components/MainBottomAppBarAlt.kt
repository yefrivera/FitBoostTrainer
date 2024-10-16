package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R

enum class BottomNavItem {
    HOME,
    ALIMENTACION,
    RUTINAS,
    ESTADISTICAS
}

@Composable
fun BottomNavigation(
    selectedItem: BottomNavItem,  // Nuevo parámetro
    onItemSelected: (BottomNavItem) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.home))
            },
            selected = selectedItem == BottomNavItem.HOME,
            onClick = { onItemSelected(BottomNavItem.HOME)
                        navController.navigate("home")}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.rutinas))
            },
            selected = selectedItem == BottomNavItem.RUTINAS,
            onClick = { onItemSelected(BottomNavItem.RUTINAS)
                        navController.navigate("fullBodyScreen")}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.alimentacion))
            },
            selected = selectedItem == BottomNavItem.ALIMENTACION,
            onClick = { onItemSelected(BottomNavItem.ALIMENTACION)
                        navController.navigate("alimentacionScreen")}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.estadisticas))
            },
            selected = selectedItem == BottomNavItem.ESTADISTICAS,
            onClick = { onItemSelected(BottomNavItem.ESTADISTICAS)
                        navController.navigate("trainCompletedScreen")}
        )
    }
}
