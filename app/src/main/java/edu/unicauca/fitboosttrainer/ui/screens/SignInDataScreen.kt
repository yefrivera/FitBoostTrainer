package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingInDataScreen() {

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
            Registro(innerPadding=innerPadding)
        }
}

@Composable
fun Registro(innerPadding: PaddingValues) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    // Dropdown menu states
    var trainingFrequency by remember { mutableStateOf("") }
    var trainingGoal by remember { mutableStateOf("") }

    val frequencyOptions = listOf("1 a 2 días", "3 a 4 días", "5 a 6 días", "Todos los días")
    val goalOptions = listOf("Perder peso", "Ganar masa muscular", "Crear hábitos saludables")

    var espaciado = 20.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding(), start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        //-------- FALTA AGREGAR LAS RESTRICCIONES DE LOS DATOS DE ENTRADA --------
        // Nombre
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.nombre)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Correo
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.correo)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        Spacer(modifier = Modifier.height(espaciado))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.contrasena)) },
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
                value = day,
                onValueChange = {
                    if (it.length <= 2) {
                        day = it
                    } },
                label = { Text("DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = month,
                onValueChange = {
                    if (it.length <= 2) {
                        month = it
                    } },
                label = { Text("MM")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)

            )
            OutlinedTextField(
                value = year,
                onValueChange = {
                    if (it.length <= 4) {
                        year = it
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
                value = height,
                onValueChange = {
                    if (it.length <= 3) {
                    height = it }},
                label = { Text(stringResource(R.string.altura)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    if (it.length <= 3) {
                        weight = it
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
            onOptionSelected = { trainingGoal = it }
        )

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // This allows you to create a composable with an exposed dropdown menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,  // Ensures that users can't type into the field
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // This attaches the menu to the text field
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
fun RegistrationScreenPreview() {
    FitBoostTrainerTheme {
        SingInDataScreen()
    }
}