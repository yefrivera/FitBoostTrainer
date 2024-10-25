package edu.unicauca.fitboosttrainer.ui.screens.progreso

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.components.BottomNavItem
import edu.unicauca.fitboosttrainer.ui.components.BottomNavigation
import edu.unicauca.fitboosttrainer.ui.components.MainTopAppBarAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgresoScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    viewModel: ProgresoViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    var inputValue by remember { mutableStateOf("") }
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.ESTADISTICAS) }

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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            // Dropdown para seleccionar gráfica
            val graphOptions = listOf("Gráfica 1", "Gráfica 2", "Gráfica 3", "Gráfica 4", "Gráfica 5", "Gráfica 6")
            var selectedGraph by remember { mutableStateOf(graphOptions[0]) }
            DropdownMenuField(
                label = "Selecciona una gráfica",
                options = graphOptions,
                selectedOption = selectedGraph,
                onOptionSelected = {
                    selectedGraph = it
                    viewModel.selectedGraph.value = graphOptions.indexOf(it) + 1
                }
            )

            // Gráfica mostrada según la opción seleccionada
            ShowGraph(viewModel.selectedGraph.value ?: 1, viewModel)

            // Dropdown para seleccionar medida
            val measureOptions = listOf("pecho", "espalda", "bicepIzq", "bicepDer", "cintura", "cadera", "musloIzq", "musloDer", "weight", "height")
            var selectedMeasure by remember { mutableStateOf(measureOptions[0]) }
            DropdownMenuField(
                label = "Selecciona una medida",
                options = measureOptions,
                selectedOption = selectedMeasure,
                onOptionSelected = {
                    selectedMeasure = it
                    viewModel.selectedMeasure.value = it
                }
            )

            // Campo de texto para ingresar nuevo valor
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text("Nuevo valor") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Botón agregar
            Button(
                onClick = {
                    if (inputValue.isNotEmpty()) {
                        viewModel.addValueToMeasure(inputValue.toInt())
                        inputValue = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }
        }
    }
}

@Composable
fun ShowGraph(graphIndex: Int, viewModel: ProgresoViewModel) {
    when (graphIndex) {
        1 -> LineChartView(data1 = viewModel.pecho.value ?: listOf(), data2 = viewModel.espalda.value ?: listOf())
        2 -> LineChartView(data1 = viewModel.bicepIzq.value ?: listOf(), data2 = viewModel.bicepDer.value ?: listOf())
        3 -> LineChartView(data1 = viewModel.cintura.value ?: listOf(), data2 = viewModel.cadera.value ?: listOf())
        4 -> LineChartView(data1 = viewModel.musloIzq.value ?: listOf(), data2 = viewModel.musloDer.value ?: listOf())
        5 -> LineChartView(data1 = viewModel.weight.value ?: listOf())
        6 -> LineChartView(data1 = viewModel.height.value ?: listOf())
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

@Composable
fun LineChartView(data1: List<Int>, data2: List<Int>? = null) {
    AndroidView(factory = { context ->
        LineChart(context).apply {
            // Configuración básica
            description.isEnabled = false
            legend.isEnabled = true
            legend.form = Legend.LegendForm.LINE
            axisRight.isEnabled = false

            // Datos para la primera línea
            val entries1 = data1.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }
            val lineDataSet1 = LineDataSet(entries1, "Línea 1").apply {
                color = Color.BLUE
                lineWidth = 2f
                setCircleColor(Color.BLUE)
            }

            val lineData = LineData(lineDataSet1)

            // Si hay segunda línea, agregarla
            if (data2 != null) {
                val entries2 = data2.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }
                val lineDataSet2 = LineDataSet(entries2, "Línea 2").apply {
                    color = Color.RED
                    lineWidth = 2f
                    setCircleColor(Color.RED)
                }
                lineData.addDataSet(lineDataSet2)
            }

            this.data = lineData
            invalidate() // Refrescar la gráfica
        }
    })
}