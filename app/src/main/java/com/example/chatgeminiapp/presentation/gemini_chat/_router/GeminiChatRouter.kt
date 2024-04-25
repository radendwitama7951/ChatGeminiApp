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
import com.example.chatgeminiapp.presentation.gemini_chat.image_selector.ImageSelectorScreen
import com.example.chatgeminiapp.presentation.gemini_chat.image_selector.ImageSelectorState
import com.example.chatgeminiapp.presentation.gemini_chat.image_selector.ImageSelectorViewModel

@Composable
fun GeminiChatRouter(
    startDestination: String = GeminiChatRoute.GeminiChatScreen.route
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = GeminiChatRoute.GeminiChatScreen.route,
            startDestination = GeminiChatRoute.ChatsScreen.route
        ) {
            composable(route = GeminiChatRoute.ChatsScreen.route) {
                val viewModel: ChatsViewModel = hiltViewModel()
                val state = viewModel.state
                ChatsScreen(
                    state = state,
                    event = viewModel::onEvent,
                )
            }

            composable(route = GeminiChatRoute.ImageSelector.route) {
                val viewModel: ImageSelectorViewModel = hiltViewModel()
                val state = viewModel.state
                ImageSelectorScreen(
                    state = state,
                    event = viewModel::onEvent,
                )

            }
        }

    }
}
