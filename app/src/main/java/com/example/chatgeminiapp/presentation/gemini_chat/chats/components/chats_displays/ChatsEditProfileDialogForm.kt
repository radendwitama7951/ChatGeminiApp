package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.ChatsProfileApiKeyInput
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.ChatsProfileNameInput
import com.example.chatgeminiapp.presentation.profile.ProfileEvent

@Composable
fun ChatsEditProfileDialogForm(
    activeProfileId: Long? = null,
    initialProfileName: String = "",
    initialApiKey: String = "",

    editApiKeyOnly: Boolean = false,

    onDismiss: () -> Unit = {},
    onProfileNameInput: () -> Unit = {},
    onProfileApiKeyInput: () -> Unit = {},
    onDone: (String, String) -> Unit = { profileName, apiKey -> }
) {
    var showProfileNameInput by rememberSaveable {
        mutableStateOf(!editApiKeyOnly)
    }
    if (showProfileNameInput) {
        ChatsProfileNameInput(
            initialValue = initialProfileName,
            onDismiss = {
                onDismiss()
            },
            onNext = {
//                event(ProfileEvent.UpdateCurrentProfile(name = it))
                showProfileNameInput = false
            }
        )
    } else {
        ChatsProfileApiKeyInput(
            initialValue = initialApiKey,
            onDismiss = { showProfileNameInput = true },
            onNext = {
//                event(ProfileEvent.UpdateCurrentProfile(id = activeProfileId, apiKey = it, pushToProfiles = true))
//                event(ProfileEvent.ResetActiveProfile)
                onDismiss()
            }
        )
    }
}