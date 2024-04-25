package com.example.chatgeminiapp.presentation._app._routing

import androidx.navigation.NamedNavArgument

sealed class AppRoute (
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object AppRootNavigation: AppRoute(route = "appRootNavigation")
    data object OnBoarding: AppRoute(route = "OnBoarding")
    data object GeminiChatScreen: AppRoute(route = "geminiChatScreen")
    data object AuthScreen: AppRoute(route = "authScreen")
    data object ProfileScreen: AppRoute(route = "profileScreen")
}