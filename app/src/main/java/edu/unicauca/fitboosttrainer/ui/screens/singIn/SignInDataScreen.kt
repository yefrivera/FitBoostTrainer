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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.TopBarTitle
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingInDataScreen(navController: NavHostController, viewModel: SignInViewModel = viewModel()) {
    Scaffold(
        topBar ={
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
    var day by remember { mutableStateOf(0) }
    var month by remember { mutableStateOf(0) }
    var year by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var weight by remember { mutableStateOf(0) }
    var trainingFrequency by remember { mutableStateOf("") }
    var trainingGoal by remember { mutableStateOf("") }
    var hombro by remember { mutableStateOf(0) }
    var pecho by remember { mutableStateOf(0) }
    var bicepIzq by remember { mutableStateOf(0) }
    var bicepDer by remember { mutableStateOf(0) }
    var cintura by remember { mutableStateOf(0) }
    var cadera by remember { mutableStateOf(0) }
    var musloIzq by remember { mutableStateOf(0) }
    var musloDer by remember { mutableStateOf(0) }
    var pantorrillaIzq by remember { mutableStateOf(0) }
    var pantorrillaDer by remember { mutableStateOf(0) }


    val espaciado = 20.dp
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = innerPadding.calculateTopPadding(), start = 20.dp, end = 20.dp,bottom = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // Nombre
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.nombre)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Correo
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.correo)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = password,
            onValueChange = { password =it },
            label = { Text(stringResource(R.string.contrasena)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        // Fecha de nacimiento (día, mes, año)
        Spacer(modifier = Modifier.height(espaciado))
        Text(text = "Fecha de Nacimiento")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = day.toString(),
                onValueChange = {
                    if (it.length <= 2) {
                        day = it.toInt()
                    }
                },
                label = {Text("DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = month.toString(),
                onValueChange = {
                    if (it.length <= 2) {
                        month= it.toInt()
                    } },
                label = { Text("MM")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)

            )
            OutlinedTextField(
                value = year.toString(),
                onValueChange = {
                    if (it.length <= 4) {
                        year= it.toInt()
                    } },
                label = { Text("YYYY") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(2f)
            )
        }

        // Altura y peso

        Spacer(modifier = Modifier.height(espaciado))
        Text(text = "Información Corporal")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = height.toString(),
                onValueChange = {
                    if (it.length <= 3) {
                        height = it.toInt() }
                },
                label = { Text(stringResource(R.string.altura)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = weight.toString(),
                onValueChange = {
                    if (it.length <= 3) {
                        weight= it.toInt()
                    }
                },
                label = { Text(stringResource(R.string.pesoKg)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // ¿Cuántas veces a la semana desea entrenar?
        Spacer(modifier = Modifier.height(espaciado))
        DropdownMenuField(
            label = "¿Cuántas veces a la semana desea entrenar?",
            options = frequencyOptions,
            selectedOption = trainingFrequency,
            onOptionSelected = { trainingFrequency = it }
        )

        // ¿Cuál es su meta de entrenamiento?
        Spacer(modifier = Modifier.height(espaciado))
        DropdownMenuField(
            label = "¿Cuál es su meta de entrenamiento?",
            options = goalOptions,
            selectedOption = trainingGoal,
            onOptionSelected = { trainingGoal= it }
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
                    value = hombro.toString(),
                    onValueChange = {
                        if (it.length <= 3) {
                            hombro = it.toInt()
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
                    value = pecho.toString(),
                    onValueChange = {
                        if (it.length <= 3) {
                            pecho = it.toInt()
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
                    value = bicepIzq.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            bicepIzq = it.toInt()
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
                    value = bicepDer.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            bicepDer = it.toInt()
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
                    value = cintura.toString(),
                    onValueChange = {
                        if (it.length <= 3) {
                            cintura= it.toInt()
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
                    value = cadera.toString(),
                    onValueChange = {
                        if (it.length <= 3) {
                            cadera = it.toInt()
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
                    value = musloIzq.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            musloIzq = it.toInt()
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
                    value = musloDer.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            musloDer = it.toInt()
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
                    value = pantorrillaIzq.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            pantorrillaIzq = it.toInt()
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
                    value = pantorrillaDer.toString(),
                    onValueChange = {
                        if (it.length <= 2) {
                            pantorrillaDer = it.toInt()
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
            onClick = {
                viewModel.registerUser(
                    email = email,
                    password = password,
                    name = name,
                    day = day,
                    month = month,
                    year = year,
                    height = height,
                    weight = weight,
                    trainingFrequency = trainingFrequency,
                    trainingGoal = trainingGoal,
                    hombro = hombro,
                    pecho = pecho,
                    bicepIzq = bicepIzq,
                    bicepDer = bicepDer,
                    cintura = cintura,
                    cadera = cadera,
                    musloIzq = musloIzq,
                    musloDer = musloDer,
                    pantorrillaIzq = pantorrillaIzq,
                    pantorrillaDer = pantorrillaDer
                )
                navController.navigate("crearRutinasHome")


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Finalizar")
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
        // Menu options
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
