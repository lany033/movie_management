package com.pop.moviemanagement.ui.authScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.pop.moviemanagement.ui.navigation.AuthGraph
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager
import com.pop.moviemanagement.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(analytics: AnalyticsManager, authManager: AuthManager, navigation: NavController){

    analytics.logScreenView(screenName = AuthGraph.SIGN_UP)

    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(topBar = { MyTopBarAuth(title = "Unete", icon = Icons.Filled.ArrowBackIosNew)}) {
        Column(modifier = Modifier
            .padding(it)
            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
            TextField(
                label = { Text(text = "Password") },
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
            Button(onClick = {
                scope.launch {
                    signUp(email, password, authManager, analytics, context, navigation)
                }
            } ) {
                Text(text = "Registrar")
            }
        }

    }
}

private suspend fun signUp(email: String, password: String, authManager: AuthManager, analytics: AnalyticsManager, context: Context, navigation: NavController) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when(val result = authManager.createUserWithEmailAndPassword(email, password)){
            is AuthRes.Success -> {
                analytics.logButtonClicked(FirebaseAnalytics.Event.SIGN_UP)
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                navigation.popBackStack()
            }
            is AuthRes.Error -> {
                analytics.logButtonClicked("Error SignUp: ${result.errorMessage}")
                Toast.makeText(context, "Error: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Toast.makeText(context, "Existen campos vacios", Toast.LENGTH_SHORT).show()
    }
}


