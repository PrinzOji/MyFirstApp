package com.Ojiem.myfirstapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Ojiem.myfirstapp.ui.theme.screens.homescreen.HomeScreen
import com.Ojiem.myfirstapp.ui.theme.screens.registerscreen.RegisterScreen
import com.Ojiem.myfirstapp.ui.theme.screens.loginscreen.LoginScreen
import com.Ojiem.myfirstapp.ui.theme.screens.dashboard.DashScreen
import com.Ojiem.myfirstapp.ui.theme.screens.splashscreen.SplashScreen
import com.Ojiem.myfirstapp.ui.theme.screens.intentscreen.IntentScreen
import com.Ojiem.myfirstapp.ui.theme.screens.calculatorScreen.CalculatorScreen
import com.Ojiem.myfirstapp.ui.theme.screens.SafaricomScreen.SafaricomScreen
import com.Ojiem.myfirstapp.ui.theme.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(ROUTE_SPLASH) { SplashScreen(navHostController) }
        composable(ROUTE_HOME) { HomeScreen(navHostController) }
        composable(ROUTE_REGISTER) { RegisterScreen(navHostController) }
        composable(ROUTE_LOGIN) { LoginScreen(navHostController) }
        composable(ROUTE_DASHBOARD) { DashScreen(navHostController) }
        composable(ROUTE_INTENT) { IntentScreen(navHostController) }
        composable(ROUTE_CALCULATOR) { CalculatorScreen(navHostController) }
        composable(ROUTE_SAFARICOM) { SafaricomScreen(navHostController) }
        composable(ROUTE_PROFILE) { ProfileScreen(navHostController) }
    }
}
