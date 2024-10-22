package edu.unicauca.fitboosttrainer.ui.screens.singIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.TopBarTitle
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasuresScreen(navController: NavHostController) {
    val viewModel: MeasuresViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar ={
            TopBarTitle(
                title = stringResource(R.string.app_name)
            )
        }
    ) { innerPadding ->
        RegistroMedidas(innerPadding = innerPadding, navController = navController, uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun RegistroMedidas(innerPadding: PaddingValues, navController: NavHostController,  uiState: MeasuresUIState, viewModel: MeasuresViewModel) {
    val espaciado = 20.dp
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = innerPadding.calculateTopPadding(), start = 12.dp, end = 12.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {

        Image(
            painter = painterResource(id = R.drawable.medidassombra),
            contentDescription = "Referencia medidas corporales",
            modifier = Modifier.size(350.dp)
        )

        Text(
            text = stringResource(R.string.medidas),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.infomedidas),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.hombro,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.updateMeasure("hombro", it)
                        }
                    },
                    label = { Text(stringResource(R.string.hombro)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.pecho,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.updateMeasure("pecho", it)
                        }
                    },
                    label = { Text(stringResource(R.string.ce_chest)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)

                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.bicepIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                            viewModel.updateMeasure("bicepIzq", it)
                        }
                    },
                    label = { Text(stringResource(R.string.BicepIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.bicepDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            viewModel.updateMeasure("bicepDer", it)
                        }
                    },
                    label = { Text(stringResource(R.string.bicepDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)

                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.cintura,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.updateMeasure("cintura", it)
                        }
                    },
                    label = { Text(stringResource(R.string.cintura))},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.cadera,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.updateMeasure("cadera", it)
                        }
                    },
                    label = { Text(stringResource(R.string.cadera)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)

                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.musloIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                           viewModel.updateMeasure("musloIzq", it)
                        }
                    },
                    label = { Text(stringResource(R.string.musloIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)

                )
                OutlinedTextField(
                    value = uiState.musloDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            viewModel.updateMeasure("musloDer", it)
                        }
                    },
                    label = { Text(stringResource(R.string.musloDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.pantorrillaIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                            viewModel.updateMeasure("pantorrillaIzq", it)
                        }
                    },
                    label = { Text(stringResource(R.string.pantorrillaIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.pantorrillaDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            viewModel.updateMeasure("pantorrillaDer", it)
                        }
                    },
                    label = { Text(stringResource(R.string.pantorrillaDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f)

                )
            }

        }

        // Botón Siguiente
        Spacer(modifier = Modifier.height(espaciado))
        Button(
            onClick = { navController.navigate("RegisterDoneScreen")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Finalizar")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MeasuresScreenPreview() {
    FitBoostTrainerTheme {
        MeasuresScreen(navController= rememberNavController())
    }
}