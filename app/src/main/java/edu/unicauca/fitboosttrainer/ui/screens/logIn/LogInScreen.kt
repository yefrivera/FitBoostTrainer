package edu.unicauca.fitboosttrainer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.components.TopBarTitle
//import edu.unicauca.fitboosttrainer.ui.screens.logIn.AuthManager
import edu.unicauca.fitboosttrainer.ui.screens.logIn.LoginUIState
import edu.unicauca.fitboosttrainer.ui.screens.logIn.LoginViewModel
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()
    val uiState by loginViewModel.uiState.collectAsState()

    Scaffold(
        topBar ={
            TopBarTitle(
                title = stringResource(R.string.app_name),

            )
        }
    ) { innerPadding ->
        InicioSesion(innerPadding=innerPadding, navController=navController,uiState = uiState, loginViewModel = loginViewModel)
    }
}

@Composable
fun InicioSesion(innerPadding: PaddingValues, navController: NavHostController, uiState: LoginUIState, loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding(), start = 40.dp, end = 40.dp,bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)

            )
        }

        OutlinedTextField(
            value = uiState.email,
            onValueChange = {loginViewModel.updateEmail(it)},
            label = {Text(stringResource(R.string.correo))},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {loginViewModel.updatePassword(it)},
            label = { Text(stringResource(R.string.contrasena)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {navController.navigate("home") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.iniciando_sesion))
            } else {
                Text(stringResource(R.string.iniciar_sesion))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.no_cuenta))
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("singIn")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.registrarse))
        }
    }
}

@Preview(showBackground = true, name = "Login Screen Preview")
@Composable
fun PreviewLoginScreen() {
    FitBoostTrainerTheme {
        LoginScreen(navController = rememberNavController())
    }
}