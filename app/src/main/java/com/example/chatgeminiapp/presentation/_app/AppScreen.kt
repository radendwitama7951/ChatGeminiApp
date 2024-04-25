package com.example.chatgeminiapp.presentation._app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatgeminiapp.presentation._app._routing.AppRouter
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun AppScreen() {

// Remember a SystemUiController
    ChatGeminiAppTheme(dynamicColor = false) {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        SideEffect {
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme

            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )

            if (useDarkIcons) {
                systemUiController.setNavigationBarColor(
                    color = Color(0xFFfafafa),
                    darkIcons = useDarkIcons
                )
            } else {
                systemUiController.setNavigationBarColor(
//                color = Color.Transparent,
                    color = Color(0xFF1F1F1F),
                    darkIcons = useDarkIcons
                )
            }



            // setStatusBarColor() and setNavigationBarColor() also exist

        }
        Surface(modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            .fillMaxSize()
            .safeDrawingPadding()
        )
        {
            AppRouter()
        }
    }
}