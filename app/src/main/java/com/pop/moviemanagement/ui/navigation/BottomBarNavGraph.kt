package com.pop.moviemanagement.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraIndoor
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.pop.moviemanagement.ui.HomeScreen
import com.pop.moviemanagement.ui.authScreen.LoginScreen
import com.pop.moviemanagement.ui.MoreScreen
import com.pop.moviemanagement.ui.MoviesScreen
import com.pop.moviemanagement.ui.StoreScreen
import com.pop.moviemanagement.ui.TheatersScreen
import com.pop.moviemanagement.ui.authScreen.ForgotPasswordScreen
import com.pop.moviemanagement.ui.authScreen.SignUpScreen
import com.pop.moviemanagement.utils.AnalyticsManager
import com.pop.moviemanagement.utils.AuthManager

@Composable
fun BottomBarNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    analytics: AnalyticsManager,
    authManager: AuthManager
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomBarNavItem.Home.route
    ) {
        composable(route = BottomBarNavItem.Home.route) {
            HomeScreen(onClickLoginSession = {
                navController.navigate(
                    AuthGraph.LOGIN
                )
            }, authManager, navController)
        }
        composable(route = BottomBarNavItem.Movies.route) { MoviesScreen() }
        composable(route = BottomBarNavItem.Theaters.route) { TheatersScreen() }
        composable(route = BottomBarNavItem.Store.route) { StoreScreen() }
        composable(route = BottomBarNavItem.More.route) { MoreScreen() }
        authNavGraph(
            navController = navController,
            analytics = analytics,
            authManager = authManager
        )
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    analytics: AnalyticsManager,
    authManager: AuthManager
) {
    navigation(route = Graph.AUTH, startDestination = AuthGraph.LOGIN) {
        composable(route = AuthGraph.LOGIN) {
            LoginScreen(
                onSignUp = {
                    navController.navigate(
                        AuthGraph.SIGN_UP
                    )
                },
                authManager = authManager,
                analyticsManager = analytics,
                navigation = navController,
                onClickForgotPassword = { navController.navigate(AuthGraph.FORGOT_PASSWORD) }
            )
        }
        composable (route = AuthGraph.SIGN_UP){
                    SignUpScreen(
                        analytics,
                        authManager = authManager,
                        navigation = navController
                    )
        }
        composable (route = AuthGraph.FORGOT_PASSWORD){
                    ForgotPasswordScreen(
                        analytics,
                        authManager = authManager,
                        navigation = navController
                    )
                }
        }
    }

    @Composable
    fun TrackScreen(name: String, analytics: FirebaseAnalytics) {
        DisposableEffect(Unit) {
            onDispose {
                analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                    param(FirebaseAnalytics.Param.SCREEN_NAME, name)
                }
            }
        }
    }

    sealed class BottomBarNavItem(
        var title: String,
        var imageVector: ImageVector,
        var route: String
    ) {
        object Home : BottomBarNavItem("Inicio", Icons.Filled.Home, "home")
        object Movies : BottomBarNavItem("Peliculas", Icons.Filled.Theaters, "movies")
        object Theaters : BottomBarNavItem("Cines", Icons.Filled.CameraIndoor, "theaters")
        object Store : BottomBarNavItem("Dulceria", Icons.Filled.Storefront, "dulceria")
        object More : BottomBarNavItem("Mas", Icons.Filled.MoreHoriz, "more")
        //object Login: BottomBarNavItem("Login", Icons.Filled.MoreHoriz, "login")
    }