package com.example.chatgeminiapp.presentation.gemini_chat._router

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsScreen
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsViewModel

@Composable
fun GeminiChatRouter(
    startDestination: String = GeminiChatRoute.ChatsScreen.route,
    onNavigateToProfile: (Long) -> Unit = {},
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = GeminiChatRoute.ChatsScreen.route) {
            val viewModel: ChatsViewModel = hiltViewModel()
            val state = viewModel.state
            ChatsScreen(
                state = state,
                event = viewModel::onEvent,

                onNavigateToProfile = onNavigateToProfile
            )
        }

    }

}
