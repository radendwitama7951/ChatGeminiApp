package com.example.chatgeminiapp.presentation.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.ActiveProfileDialog
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.CreateProfileDialog

@Composable
fun ProfileScreen(
    state: ProfileState,
    event: (ProfileEvent) -> Unit
) {
    Scaffold {
        Surface(
            modifier = Modifier.padding(bottom = it.calculateTopPadding())
        ) {
            CreateProfileDialog()
        }
    }
}