package edu.unicauca.fitboosttrainer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.unicauca.fitboosttrainer.R
import androidx.compose.material3.Button
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@Composable
fun CompletedScreen(
    navController: NavHostController,
    congratsText: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(start = 85.dp)
            )
        }


        Spacer(modifier = Modifier.height(42.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(400.dp)
                    .align(Alignment.Center)
                    .zIndex(1f)
            )
        }

        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = congratsText,
            style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(132.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(R.string.dr_sig))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    FitBoostTrainerTheme {
        val navController = rememberNavController()
        CompletedScreen( navController = navController, congratsText = stringResource(id = R.string.register_done))
    }
}