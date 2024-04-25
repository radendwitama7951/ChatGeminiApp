package com.example.chatgeminiapp.presentation._app._routing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.chatgeminiapp.presentation.gemini_chat.GeminiChatScreen
import com.example.chatgeminiapp.presentation.profile.ProfileScreen
import com.example.chatgeminiapp.presentation.profile.ProfileViewModel

/*
* NavGraph
* */
@Composable
fun AppRouter(
    startDestination: String = AppRoute.AppRootNavigation.route
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = AppRoute.AppRootNavigation.route,
            startDestination = AppRoute.AppProfile.route
        ) {
            composable(route = AppRoute.AppProfile.route) { navBackStackEntry ->
                ProfileScreen(
                    onNavigateToGeminiChat = {
                        navController.navigate(AppRoute.AppGeminiChat.route) {
                            popUpTo(AppRoute.AppProfile.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = AppRoute.AppGeminiChat.route) {
                GeminiChatScreen(
                    onNavigateToProfile = {
                        navController.navigate(AppRoute.AppProfile.route)
                    }
                )
            }
        }

    }
}