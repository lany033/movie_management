package com.pop.moviemanagement.ui.authScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.pop.moviemanagement.R
import com.pop.moviemanagement.ui.navigation.AuthGraph
import com.pop.moviemanagement.ui.navigation.BottomBarNavItem
import com.pop.moviemanagement.ui.navigation.Graph
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager
import com.pop.moviemanagement.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onSignUp: () -> Unit, authManager: AuthManager, analyticsManager: AnalyticsManager, navigation: NavHostController, onClickForgotPassword: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var scope = rememberCoroutineScope()
    var context = LocalContext.current

    val googleSignInLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){ result ->
        when(val account = authManager.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))){
            is AuthRes.Success -> {
                val credential = GoogleAuthProvider.getCredential(account?.data?.idToken, null)
                scope.launch {
                    val fireUser = authManager.signInWithGoogleCredential(credential)
                    if(fireUser != null){
                       Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                       navigation.navigate(BottomBarNavItem.Home.route){
                           popUpTo(AuthGraph.LOGIN){
                               inclusive = true
                           }
                       }
                    }
                }
                Log.d("USER LOGIN","${authManager.getCurrentUser()}")
            }
            is AuthRes.Error -> {
                analyticsManager.logError("Error SignIn: ${account.errorMessage}")
                Toast.makeText(context, "Error: ${account.errorMessage}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Error: Unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(topBar = { MyTopBarAuth(title = "Inicia Sesion", icon = Icons.Filled.ArrowBackIosNew) }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 30.dp, start = 12.dp, end = 12.dp, bottom = 70.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = "N° de socio",
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                supportingText = { Text(text = "N° DNI, RUC, CE, Pasaporte es requerido") }
            )
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = { email = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )

            TextField(
                label = { Text(text = "Contraseña") },
                value = password,
                onValueChange = { password = it },
                colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                supportingText = { Text(text = "")}
            )
            TextButton(onClick = { onClickForgotPassword() }) {
                Text(text = "¿Olvido su contraseña?")
            }
            Button(onClick = {
                scope.launch {
                    emailPassSignIn(email, password, authManager,analyticsManager,context,navigation)
                }
            }) {
                Text(text = "Ingresar")
            }
            Text(text = "¿Aun no eres socio?")
            Button(onClick = { onSignUp() }) {
                Text(text = "Registrarse")
            }
            SocialMediaButton(
                onClick = {
                    authManager.signInWithGoogle(googleSignInLauncher)
                },
                text = "Continuar con Google",
                icon = R.drawable.ic_google,
                color = Color(0xFFF1F1F1)
            )
        }
    }
}

private suspend fun emailPassSignIn(email: String, password: String, authManager: AuthManager, analyticsManager: AnalyticsManager, context: Context, navigation: NavController) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = authManager.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analyticsManager.logButtonClicked("Click: Iniciar Sesion")
                navigation.navigate(BottomBarNavItem.Home.route){
                    popUpTo(AuthGraph.LOGIN){
                        inclusive = true
                    }
                }

            }
            is AuthRes.Error -> {
                analyticsManager.logButtonClicked("Error SignUp: ${result.errorMessage}")
                Toast.makeText(context, "Error SignUp: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }else{
        Toast.makeText(context, "Existen campos vacios", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun MyTopBarAuth(title: String, icon: ImageVector){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Row(modifier = Modifier.align(Alignment.TopStart), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title)
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
    }
}

@Composable
fun SocialMediaButton(onClick: () -> Unit, text: String, icon: Int, color: Color, ) {
    var click by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(start = 40.dp, end = 40.dp).clickable { click = !click },
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = if(icon == R.drawable.ic_incognito) color else Color.Gray),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$text", color = if(icon == R.drawable.ic_incognito) Color.White else Color.Black)
            click = true
        }
    }
}