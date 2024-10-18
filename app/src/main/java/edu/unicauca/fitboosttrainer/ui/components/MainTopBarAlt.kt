package edu.unicauca.fitboosttrainer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
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
fun DrawerContent(){
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(124.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
            Text(text="Usuario")
            Text(text="correo@gmail.com")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Acción al hacer clic */ },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Cerrar Sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview() {
    DrawerContent()
}
