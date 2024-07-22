package com.pop.moviemanagement.ui.authScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.pop.moviemanagement.ui.navigation.AuthGraph
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager
import com.pop.moviemanagement.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(analyticsManager: AnalyticsManager, authManager: AuthManager, navigation: NavController){

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var scope = rememberCoroutineScope()

    Column {
        TextField(value = email, onValueChange = {email = it})
        Button(onClick = {
            scope.launch {
                when(val res = authManager.resetPassword(email)){
                    is AuthRes.Success -> {
                        analyticsManager.logButtonClicked(buttonName = "Forgot Password")
                        Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                        navigation.navigate(AuthGraph.LOGIN)
                    }
                    is AuthRes.Error -> {
                        analyticsManager.logError(error = "Reset Password ${res.errorMessage}")
                        Toast.makeText(context, "Error al enviar correo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }) {
            Text(text = "Recuperar contraseña")
        }
    }

}