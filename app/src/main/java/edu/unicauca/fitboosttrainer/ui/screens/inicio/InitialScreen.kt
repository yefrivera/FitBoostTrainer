package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.fitboosttrainer.R
import edu.unicauca.fitboosttrainer.ui.theme.FitBoostTrainerTheme

@Composable
fun InitialScreen(
    navController: NavHostController,
    viewModel: InitialViewModel = viewModel()
) {
    val imageIndex = viewModel.imageIndex.value
    val images = viewModel.images

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.dr_name),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
        )

        Text(
            text = stringResource(id = R.string.dr_description),
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = images[(imageIndex - 1 + images.size) % images.size]),
                contentDescription = "Previous Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-50).dp)
            )

            Image(
                painter = painterResource(id = images[imageIndex]),
                contentDescription = "Main Image",
                modifier = Modifier
                    .size(450.dp)
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = images[(imageIndex + 1) % images.size]),
                contentDescription = "Next Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (50).dp)
                    .zIndex(-1f)
            )

            IconButton(
                onClick = { viewModel.previousImage() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Previous Image")
            }

            IconButton(
                onClick = { viewModel.nextImage() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Next Image")
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.dr_login))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("creditosScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.dr_mas))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    FitBoostTrainerTheme {
        InitialScreen(navController = rememberNavController())
    }
}

