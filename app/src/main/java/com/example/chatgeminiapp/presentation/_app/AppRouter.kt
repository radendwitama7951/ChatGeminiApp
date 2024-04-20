package com.example.chatgeminiapp.presentation._app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.chatgeminiapp.presentation.gemini_chat.GeminiChatScreen
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsScreen
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsViewModel

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
            startDestination = AppRoute.GeminiChatScreen.route
        ) {
            composable(route = AppRoute.GeminiChatScreen.route) {
//                val viewModel: ChatsViewModel = hiltViewModel()
//                val state = viewModel.state
//                ChatsScreen(state = state, event = viewModel::onEvent)
                GeminiChatScreen()
            }
        }

    }
}