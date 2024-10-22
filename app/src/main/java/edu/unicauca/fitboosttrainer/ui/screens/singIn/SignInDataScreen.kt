package edu.unicauca.fitboosttrainer.ui.screens.singIn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.TopBarTitle
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingInDataScreen(navController: NavHostController) {
    val viewModel: SingInDataViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar ={
            TopBarTitle(
                title = stringResource(R.string.app_name),
            )
        }
    ) { innerPadding ->
            Registro(innerPadding = innerPadding, navController = navController, uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun Registro(innerPadding: PaddingValues, navController: NavHostController, uiState: SingInDataUIState, viewModel: SingInDataViewModel) {
    val frequencyOptions = listOf("1 a 2 días", "3 a 4 días", "5 a 6 días", "Todos los días")
    val goalOptions = listOf("Perder peso", "Ganar masa muscular", "Crear hábitos saludables")

    val espaciado = 20.dp
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = innerPadding.calculateTopPadding(), start = 20.dp, end = 20.dp,bottom = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {
        //-------- FALTA AGREGAR LAS RESTRICCIONES DE LOS DATOS DE ENTRADA --------
        // Nombre
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.updateField("name", it) },
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
            value = uiState.email,
            onValueChange = { viewModel.updateField("email", it) },
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
            value = uiState.password,
            onValueChange = { viewModel.updateField("password", it) },
            label = { Text(stringResource(R.string.contrasena)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Para ocultar la contraseña
        )

        // Fecha de nacimiento (día, mes, año)
        Spacer(modifier = Modifier.height(espaciado))
        Text(text = "Fecha de Nacimiento")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = uiState.day,
                onValueChange = {
                    if (it.length <= 2) {
                        viewModel.updateField("day", it)
                    } },
                label = { Text("DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiState.month,
                onValueChange = {
                    if (it.length <= 2) {
                        viewModel.updateField("month", it)
                    } },
                label = { Text("MM")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)

            )
            OutlinedTextField(
                value = uiState.year,
                onValueChange = {
                    if (it.length <= 4) {
                        viewModel.updateField("year", it)
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
                value = uiState.height,
                onValueChange = {
                    if (it.length <= 3) {
                        viewModel.updateField("height", it)}
                    },
                label = { Text(stringResource(R.string.altura)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = uiState.weight,
                onValueChange = {
                    if (it.length <= 3) {
                        viewModel.updateField("weight", it)
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
            selectedOption = uiState.trainingFrequency,
            onOptionSelected = { viewModel.updateField("training", it) }
        )

        // ¿Cuál es su meta de entrenamiento?
        Spacer(modifier = Modifier.height(espaciado))
        DropdownMenuField(
            label = "¿Cuál es su meta de entrenamiento?",
            options = goalOptions,
            selectedOption = uiState.trainingGoal,
            onOptionSelected = { viewModel.updateField("trainingGoal", it) }
        )

        // Botón Siguiente
        Spacer(modifier = Modifier.height(espaciado))
        Button(
            onClick = {navController.navigate("registrerMessureScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Siguiente")
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
@Preview(showBackground = true)
@Composable
fun SingInDataScreenPreview() {
    FitBoostTrainerTheme {
        SingInDataScreen(navController= rememberNavController())
    }
}