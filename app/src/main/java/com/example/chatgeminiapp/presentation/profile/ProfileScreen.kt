package com.example.chatgeminiapp.presentation.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatgeminiapp.presentation.profile._routing.ProfileRoute
import com.example.chatgeminiapp.presentation.profile._routing.ProfileRouter

@Composable
fun ProfileScreen(
    startDestination: String = ProfileRoute.ProfileList.route,
    onNavigateToGeminiChat: (Long) -> Unit = {}
) {
    Scaffold {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateTopPadding())
        ) {
            ProfileRouter(
                startDestination = startDestination,
                onNavigateToGeminiChat = onNavigateToGeminiChat)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    Scaffold (
    ){
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateTopPadding())
        ) {
            ProfileRouter()
        }
    }
}
