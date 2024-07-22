package com.pop.moviemanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.pop.moviemanagement.ui.authScreen.SignUpScreen
import com.pop.moviemanagement.ui.navigation.RootNavigationGraph
import com.pop.moviemanagement.ui.theme.MovieManagementTheme
import com.pop.moviemanagement.utils.AnalyticsManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MovieManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RootNavigationGraph(this)
                    //SignUpScreen()
                }
            }
        }
    }
}

