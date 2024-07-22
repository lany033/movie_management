package com.pop.moviemanagement.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pop.moviemanagement.ui.navigation.AuthGraph
import com.pop.moviemanagement.ui.navigation.BottomBarNavItem
import com.pop.moviemanagement.ui.navigation.TrackScreen
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager

@Composable
fun HomeScreen(
    onClickLoginSession: () -> Unit,
    authManager: AuthManager,
    navigation: NavController
) {

    var showDialog by remember { mutableStateOf(false) }

    var user = authManager.getCurrentUser()

    var onLogoutConfirmed: () -> Unit = {
        authManager.signOut()
        navigation.navigate(AuthGraph.LOGIN) {
            popUpTo(BottomBarNavItem.Home.route) {
                inclusive = true
            }
        }
    }

    Scaffold(topBar = { MyTopBar("Bienvenido", user = user?.email, onClickLoginSession, onLogoutConfirmed) }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
        ) {
            Text(text = "Home")
            Box() {
                if (showDialog) {
                    LogoutDialog(onConfirmLogout = {
                        onLogoutConfirmed()
                        showDialog = false
                    }, onDismiss = { showDialog = false })
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cerrar sesión") },
        text = { Text("¿Estás seguro que deseas cerrar sesión?") },
        confirmButton = {
            Button(
                onClick = onConfirmLogout
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun MyTopBar(title: String, user: String?, onClickLoginSession: () -> Unit, onConfirmLogout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                text = if (user != null) "$user" else "Usuario",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onClickLoginSession() }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
            }
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            IconButton(onClick = { onConfirmLogout() }) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Account")
            }
        }

    }
}

