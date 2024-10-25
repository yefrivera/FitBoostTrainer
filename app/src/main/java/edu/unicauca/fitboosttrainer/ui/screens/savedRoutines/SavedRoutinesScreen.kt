package edu.unicauca.fitboosttrainer.ui.screens.savedRoutines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRoutinesScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    viewModel: SavedRoutineViewModel = viewModel()
) {

    var selectedNavItem by remember { mutableStateOf(BottomNavItem.RUTINAS) }

    Scaffold(
        topBar = {
            MainTopAppBarAlt(
                title = stringResource(R.string.saved_routines),
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
    ) {
        innerPadding ->
        ScrollContent(innerPadding = innerPadding, viewModel = viewModel, navController = navController)
    }
}

@Composable
private fun ScrollContent(
    innerPadding: PaddingValues,
    viewModel: SavedRoutineViewModel = viewModel(),
    navController: NavHostController
) {
    val savedRoutines = viewModel.savedRoutines
    val isLoading = viewModel.isLoading

    var showDeleteDialog by remember { mutableStateOf(false) }
    var routineToDelete by remember { mutableStateOf<SavedRoutine?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(savedRoutines) { routine ->
                    SavedRoutineCard(
                        routine = routine,
                        onEditClick = {
                            navController.navigate("editRoutine/${routine.id}")
                        },
                        onDeleteClick = {
                            routineToDelete = routine
                            showDeleteDialog = true
                        },
                        onClickCard = {
                            navController.navigate("routineDetail/${routine.id}")
                        }
                    )
                }
            }
        }

        if (showDeleteDialog && routineToDelete != null) {
            DeleteRoutineDialog(
                routineName = routineToDelete?.name ?: "",
                onConfirmDelete = {
                    viewModel.deleteRoutine(routineToDelete!!.id)
                    showDeleteDialog = false
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    }
}


@Composable
fun SavedRoutineCard(
    routine: SavedRoutine,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClickCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickCard() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 10.dp)
        ){
            Text(
                text = routine.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar rutina")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar rutina")
                }
            }
        }
    }
}


@Composable
fun DeleteRoutineDialog(
    routineName: String,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(R.string.deleted_routine)) },
        text = { Text("¿Está seguro de que desea eliminar la rutina \"$routineName\"? Esta acción no se puede deshacer.") },
        confirmButton = {
            Button(onClick = onConfirmDelete) {
                Text(stringResource(R.string.deleted))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
