package com.pop.moviemanagement

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pop.moviemanagement.ui.navigation.BottomBarNavGraph
import com.pop.moviemanagement.ui.navigation.BottomBarNavItem

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(bottomBar = { MyBottomBar(navController = navController) }
    ) {
        BottomBarNavGraph(
            navController = navController, modifier = Modifier.padding(it)
        )
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