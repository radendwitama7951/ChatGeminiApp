package com.example.chatgeminiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.chatgeminiapp.presentation._app.AppRouter
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import dagger.hilt.android.AndroidEntryPoint

/*
* @NavGraph
* */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ChatGeminiAppTheme (dynamicColor = false) {
                //Add fillMaxSize()
                AppRouter()
            }
        }
    }

}