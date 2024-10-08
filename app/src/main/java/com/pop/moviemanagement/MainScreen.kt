package com.pop.moviemanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.pop.moviemanagement.ui.navigation.BottomBarNavGraph
import com.pop.moviemanagement.ui.navigation.BottomBarNavItem
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager
import com.pop.moviemanagement.utils.FirestoreManager

@Composable
fun MainScreen(analytics: AnalyticsManager, authManager: AuthManager, firestoreManager: FirestoreManager) {

    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { MyBottomBar(navController = navController) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            BottomBarNavGraph(
                navController = navController, analytics = analytics, authManager = authManager, firestoreManager = firestoreManager
            )
        }

    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    val items = listOf(
        BottomBarNavItem.Home,
        BottomBarNavItem.Movies,
        BottomBarNavItem.Theaters,
        BottomBarNavItem.Store,
        BottomBarNavItem.More
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}