package com.example.chatgeminiapp.presentation.gemini_chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatgeminiapp.presentation._app.AppRouter
import com.example.chatgeminiapp.presentation.gemini_chat._router.GeminiChatRouter

@Composable
fun GeminiChatScreen () {
    Scaffold {
        Column (
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            GeminiChatRouter()
        }
    }

}