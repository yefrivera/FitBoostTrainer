package edu.unicauca.fitboosttrainer.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrerMessureScreen() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Scaffold(
        topBar ={
            MainTopAppBarAlt(
                title = stringResource(R.string.app_name),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                drawerState = drawerState
            )
        }
    ) { innerPadding ->
        RegistroMedidas(innerPadding=innerPadding)
    }
}

@Composable
fun RegistroMedidas(innerPadding: PaddingValues) {

    var hombro by remember { mutableStateOf("") }
    var bicepIzq by remember { mutableStateOf("") }
    var cintura by remember { mutableStateOf("") }
    var cadera by remember { mutableStateOf("") }
    var pantorrillaIzq by remember { mutableStateOf("") }
    var musloIzq by remember { mutableStateOf("") }
    var pecho by remember { mutableStateOf("") }
    var bicepDer by remember { mutableStateOf("") }
    var musloDer by remember { mutableStateOf("") }
    var pantorrillaDer by remember { mutableStateOf("") }
    val espaciado = 20.dp


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding(), start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {

        Image(
            painter = painterResource(id = R.drawable.medidas),
            contentDescription = "Referencia medidas corporales",
            modifier = Modifier.size(350.dp)
        )

        Text(
            text = stringResource(R.string.medidas),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = hombro,
                    onValueChange = {
                        if (it.length <= 2) {
                            hombro = it
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
                    value = pecho,
                    onValueChange = {
                        if (it.length <= 3) {
                            pecho = it
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
                    value = bicepIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                            bicepIzq= it
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
                    value = bicepDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            bicepDer = it
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
                    value = cintura,
                    onValueChange = {
                        if (it.length <= 2) {
                            cintura = it
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
                    value = cadera,
                    onValueChange = {
                        if (it.length <= 2) {
                            cadera = it
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
                    value = musloIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                            musloIzq = it
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
                    value = musloDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            musloDer = it
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
                    value = pantorrillaIzq,
                    onValueChange = {
                        if (it.length <= 2) {
                            pantorrillaIzq = it
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
                    value = pantorrillaDer,
                    onValueChange = {
                        if (it.length <= 2) {
                            pantorrillaDer = it
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
            onClick = { /* Lógica del botón */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Siguiente")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrerMessureScreenPreview() {
    FitBoostTrainerTheme {
        RegistrerMessureScreen()
    }
}