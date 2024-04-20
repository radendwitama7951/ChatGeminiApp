package com.example.chatgeminiapp.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthScreen () {
    Scaffold {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            Text(text = "Auth Screen Works !")
        }
    }
}