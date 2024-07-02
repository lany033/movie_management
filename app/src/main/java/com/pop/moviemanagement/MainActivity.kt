package com.pop.moviemanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.pop.moviemanagement.ui.navigation.RootNavigationGraph
import com.pop.moviemanagement.ui.theme.MovieManagementTheme

class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        analytics = Firebase.analytics
        setContent {
            MovieManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RootNavigationGraph(navController = rememberNavController(), analytics = analytics)
                }
            }
        }
    }
}

