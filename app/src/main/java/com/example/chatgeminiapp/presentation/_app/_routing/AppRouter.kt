package com.example.chatgeminiapp.presentation._app._routing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
            startDestination = AppRoute.ProfileScreen.route
        ) {
            composable(route = AppRoute.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                val state = viewModel.state
                ProfileScreen(state = state, event = viewModel::onEvent)
            }

            composable(route = AppRoute.GeminiChatScreen.route) {
                GeminiChatScreen()
            }
        }

    }
}