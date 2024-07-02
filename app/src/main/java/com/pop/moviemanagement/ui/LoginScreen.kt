package com.pop.moviemanagement.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {

    Scaffold(topBar = { MyTopBarSession(title = "Inicia Sesion") }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(value = "N° de socio", onValueChange = {})
            TextField(value = "Contraseña", onValueChange = {})
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "¿Olvido su contraseña?")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ingresar")
            }
            Text(text = "¿Aun no eres socio?")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Registrarse")
            }
        }
    }
}