package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBarAlt(
    title: String,
    drawerState: DrawerState,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: (() -> Unit)? = null,
    showBackIcon: Boolean = true
) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBackIcon && onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        },
        actions = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Abrir menú"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun DrawerContent(userName: String, userEmail: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(top=16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        // Avatar del usuario
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Avatar de usuario",
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Nombre de usuario
        Text(text = userName, style = MaterialTheme.typography.titleLarge)
        // Correo electrónico del usuario
        Text(text = userEmail, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        // Botón de cerrar sesión
        Button(
            onClick = { /* Acción para cerrar sesión */ },
            modifier = Modifier.width(350.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cerrar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    DrawerContent(
        userName = "Usuario",
        userEmail = "usuario@correo.com",
    )
}
