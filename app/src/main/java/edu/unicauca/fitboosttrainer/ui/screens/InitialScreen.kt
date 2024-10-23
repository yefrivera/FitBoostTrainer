package edu.unicauca.fitboosttrainer.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun InitialScreen(navController: NavHostController) {
    var imageIndex by remember { mutableStateOf(0) }
    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3)
    )

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
            //modifier = Modifier.padding(top = 42.dp)
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
                painter = images[(imageIndex - 1 + images.size) % images.size],
                contentDescription = "Previous Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-50).dp)

            )

            Image(
                painter = images[imageIndex],
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
                    .zIndex(1f)

            )
            Image(
                painter = images[(imageIndex + 1) % images.size],
                contentDescription = "Next Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (50).dp)
                    .zIndex(-1f)
            )
            IconButton(
                onClick = { imageIndex = (imageIndex - 1 + images.size) % images.size },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Previous Image")
            }
            IconButton(
                onClick = { imageIndex = (imageIndex + 1) % images.size },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Next Image")
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.dr_login))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Acción para ver más */ },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.dr_mas))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    FitBoostTrainerTheme  {
        InitialScreen(navController= rememberNavController())
    }
}


