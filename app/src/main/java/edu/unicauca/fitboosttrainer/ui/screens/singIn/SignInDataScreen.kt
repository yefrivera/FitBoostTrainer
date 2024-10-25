package edu.unicauca.fitboosttrainer.ui.screens.singIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.TopBarTitle



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInDataScreen(navController: NavHostController, viewModel: SignInViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopBarTitle(
                title = stringResource(R.string.app_name),
            )
        }
    ) { innerPadding ->
        Registro(innerPadding = innerPadding, navController = navController, viewModel = viewModel)
    }
}

@Composable
fun Registro(innerPadding: PaddingValues, navController: NavHostController, viewModel: SignInViewModel) {
    val frequencyOptions = listOf("1 a 2 días", "3 a 4 días", "5 a 6 días", "Todos los días")
    val goalOptions = listOf("Perder peso", "Ganar masa muscular", "Crear hábitos saludables")
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var trainingFrequency by remember { mutableStateOf("") }
    var trainingGoal by remember { mutableStateOf("") }
    var hombro by remember { mutableStateOf("") }
    var pecho by remember { mutableStateOf("") }
    var bicepIzq by remember { mutableStateOf("") }
    var bicepDer by remember { mutableStateOf("") }
    var cintura by remember { mutableStateOf("") }
    var cadera by remember { mutableStateOf("") }
    var musloIzq by remember { mutableStateOf("") }
    var musloDer by remember { mutableStateOf("") }
    var pantorrillaIzq by remember { mutableStateOf("") }
    var pantorrillaDer by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }

    val espaciado = 20.dp
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = innerPadding.calculateTopPadding(), start = 20.dp, end = 20.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                showError = false
            },
            label = { Text(stringResource(R.string.nombre)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = showError && name.isEmpty()
        )

        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                showError = false
            },
            label = { Text(stringResource(R.string.correo)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = showError && email.isEmpty()
        )

        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                showError = false
            },
            label = { Text(stringResource(R.string.contrasena)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = showError && password.isEmpty()
        )

        Spacer(modifier = Modifier.height(espaciado))
        Text(text = "Fecha de Nacimiento")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = day,
                onValueChange = {
                    if (it.length <= 2) day = it
                    showError = false
                },
                label = { Text("DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.weight(1f),
                isError = showError && day.isEmpty()
            )
            OutlinedTextField(
                value = month,
                onValueChange = {
                    if (it.length <= 2) month = it
                    showError = false
                },
                label = { Text("MM") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                isError = showError && month.isEmpty()
            )
            OutlinedTextField(
                value = year,
                onValueChange = {
                    if (it.length <= 4) year = it
                    showError = false
                },
                label = { Text("YYYY") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(2f),
                isError = showError && year.isEmpty()
            )
        }

        Spacer(modifier = Modifier.height(espaciado))
        Text(text = "Información Corporal")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = {
                    if (it.length <= 3) height = it
                    showError = false
                },
                label = { Text(stringResource(R.string.altura)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                isError = showError && height.isEmpty()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    if (it.length <= 3) weight = it
                    showError = false
                },
                label = { Text(stringResource(R.string.pesoKg)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                isError = showError && weight.isEmpty()
            )
        }

        Spacer(modifier = Modifier.height(espaciado))
        DropdownMenuField(
            label = "¿Cuántas veces a la semana desea entrenar?",
            options = frequencyOptions,
            selectedOption = trainingFrequency,
            onOptionSelected = { trainingFrequency = it }
        )

        Spacer(modifier = Modifier.height(espaciado))
        DropdownMenuField(
            label = "¿Cuál es su meta de entrenamiento?",
            options = goalOptions,
            selectedOption = trainingGoal,
            onOptionSelected = { trainingGoal = it }
        )

        Spacer(modifier = Modifier.height(espaciado))
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
                    value = hombro,
                    onValueChange = {
                        if (it.length <= 3) hombro = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.hombro)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && hombro.isEmpty()
                )
                OutlinedTextField(
                    value = pecho,
                    onValueChange = {
                        if (it.length <= 3) pecho = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.ce_chest)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && pecho.isEmpty()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = bicepIzq,
                    onValueChange = {
                        if (it.length <= 2) bicepIzq = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.BicepIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && bicepIzq.isEmpty()
                )
                OutlinedTextField(
                    value = bicepDer,
                    onValueChange = {
                        if (it.length <= 2) bicepDer = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.bicepDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && bicepDer.isEmpty()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = cintura,
                    onValueChange = {
                        if (it.length <= 3) cintura = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.cintura)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && cintura.isEmpty()
                )
                OutlinedTextField(
                    value = cadera,
                    onValueChange = {
                        if (it.length <= 3) cadera = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.cadera)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && cadera.isEmpty()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = musloIzq,
                    onValueChange = {
                        if (it.length <= 2) musloIzq = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.musloIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && musloIzq.isEmpty()
                )
                OutlinedTextField(
                    value = musloDer,
                    onValueChange = {
                        if (it.length <= 2) musloDer = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.musloDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && musloDer.isEmpty()
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = pantorrillaIzq,
                    onValueChange = {
                        if (it.length <= 2) pantorrillaIzq = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.pantorrillaIzq)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && pantorrillaIzq.isEmpty()
                )
                OutlinedTextField(
                    value = pantorrillaDer,
                    onValueChange = {
                        if (it.length <= 2) pantorrillaDer = it
                        showError = false
                    },
                    label = { Text(stringResource(R.string.pantorrillaDer)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    isError = showError && pantorrillaDer.isEmpty()
                )
            }

        }

        Spacer(modifier = Modifier.height(espaciado))
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && day.isNotBlank() &&
                    month.isNotBlank() && year.isNotBlank() && height.isNotBlank() && weight.isNotBlank() &&
                    hombro.isNotBlank() && pecho.isNotBlank() && bicepIzq.isNotBlank() && bicepDer.isNotBlank() &&
                    cintura.isNotBlank() && cadera.isNotBlank() && trainingFrequency.isNotBlank() && trainingGoal.isNotBlank()
                ) {
                    viewModel.registerUser(
                        email = email,
                        password = password,
                        name = name,
                        day = day.toIntOrNull() ?: 0,
                        month = month.toIntOrNull() ?: 0,
                        year = year.toIntOrNull() ?: 0,
                        height = height.toIntOrNull() ?: 0,
                        weight = weight.toIntOrNull() ?: 0,
                        trainingFrequency = trainingFrequency,
                        trainingGoal = trainingGoal,
                        hombro = hombro.toIntOrNull() ?: 0,
                        pecho = pecho.toIntOrNull() ?: 0,
                        bicepIzq = bicepIzq.toIntOrNull() ?: 0,
                        bicepDer = bicepDer.toIntOrNull() ?: 0,
                        cintura = cintura.toIntOrNull() ?: 0,
                        cadera = cadera.toIntOrNull() ?: 0,
                        musloIzq = musloIzq.toIntOrNull() ?: 0,
                        musloDer = musloDer.toIntOrNull() ?: 0,
                        pantorrillaIzq = pantorrillaIzq.toIntOrNull() ?: 0,
                        pantorrillaDer = pantorrillaDer.toIntOrNull() ?: 0
                    )
                    navController.navigate("RegisterDoneScreen")
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Finalizar")
        }

        if (showError) {
            Text(
                text = "Todos los campos son obligatorios.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
