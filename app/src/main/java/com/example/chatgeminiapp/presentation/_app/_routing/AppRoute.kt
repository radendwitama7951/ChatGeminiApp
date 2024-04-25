package com.example.chatgeminiapp.presentation._app._routing

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppRoute (
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object AppRootNavigation: AppRoute(route = "appRootNavigation")
    data object AppProfile: AppRoute(
        route = "appProfile"
    )
    data object AppGeminiChat: AppRoute(route = "appGeminiChat")
    data object OnBoarding: AppRoute(route = "OnBoarding")
    data object GeminiChatScreen: AppRoute(route = "geminiChatScreen")
    data object AuthScreen: AppRoute(route = "authScreen")
    data object ProfileScreen: AppRoute(route = "profileScreen")
}