package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@Composable
fun CreditosScreen(
    navController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.Center)
                    .zIndex(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.creditos),
            style = TextStyle(
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.padding(24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text=stringResource(id = R.string.integrantes),
            style = TextStyle(
                textAlign = TextAlign.Justify
            ),
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = stringResource(id = R.string.andres),
            style = TextStyle(
                textAlign = TextAlign.Justify
            )
        )

        Text(
            text = stringResource(id = R.string.yefri),
            style = TextStyle(
                textAlign = TextAlign.Justify
            )
        )

        Text(
            text = stringResource(id = R.string.daniela),
            style = TextStyle(
                textAlign = TextAlign.Justify
            )
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.salir))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreditosScreenPre() {
    FitBoostTrainerTheme {
        val navController = rememberNavController()
        CreditosScreen(navController = navController)
    }
}
