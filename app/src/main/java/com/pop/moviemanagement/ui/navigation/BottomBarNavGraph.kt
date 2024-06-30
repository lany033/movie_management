package com.pop.moviemanagement.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraIndoor
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pop.moviemanagement.MainScreen
import com.pop.moviemanagement.ui.HomeScreen

@Composable
fun BottomBarNavGraph(navController: NavHostController, modifier: Modifier){
    NavHost(modifier= modifier, navController = navController, startDestination = BottomBarNavItem.Home.route){
        composable(route = BottomBarNavItem.Home.route){ HomeScreen() }
    }
}

sealed class BottomBarNavItem (
    var title: String,
    var imageVector: ImageVector,
    var route: String
){
    object Home : BottomBarNavItem("Inicio", Icons.Filled.Home, "home")
    object Movies: BottomBarNavItem("Peliculas", Icons.Filled.Theaters, "movies")
    object Theaters: BottomBarNavItem("Cines", Icons.Filled.CameraIndoor, "theaters")
    object Store: BottomBarNavItem("Dulceria", Icons.Filled.Storefront, "dulceria")
    object More: BottomBarNavItem("Mas", Icons.Filled.MoreHoriz, "more")
}
