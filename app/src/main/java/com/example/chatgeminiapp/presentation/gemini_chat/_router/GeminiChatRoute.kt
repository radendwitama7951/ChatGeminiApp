package com.example.chatgeminiapp.presentation.gemini_chat._router

import androidx.navigation.NamedNavArgument

sealed class GeminiChatRoute (
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object GeminiChatScreen: GeminiChatRoute(route = "geminiChatScreen")
    data object ChatsScreen: GeminiChatRoute(route = "chatsScreen")
    data object ImageSelector: GeminiChatRoute(route = "imageSelectorScreen")
    data object ProfileScreen: GeminiChatRoute(route = "profileScreen")

}