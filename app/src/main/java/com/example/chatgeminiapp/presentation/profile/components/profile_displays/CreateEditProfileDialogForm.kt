package com.example.chatgeminiapp.presentation.profile.components.profile_displays

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.chatgeminiapp.presentation.profile.ProfileEvent
import com.example.chatgeminiapp.presentation.profile.ProfileState
import com.example.chatgeminiapp.presentation.profile.components.profile_actions.ProfileApiKeyInput
import com.example.chatgeminiapp.presentation.profile.components.profile_actions.ProfileNameInput

@Composable
fun CreateEditProfileDialogForm(
    activeProfileId: Long? = null,
    initialProfileName: String = "",
    initialApiKey: String = "",
    event: (ProfileEvent) -> Unit,

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
        ProfileNameInput(
            initialValue = initialProfileName,
            onDismiss = {
                onDismiss()
            },
            onNext = {
                event(ProfileEvent.UpdateCurrentProfile(name = it))
                showProfileNameInput = false
            }
        )
    } else {
        ProfileApiKeyInput(
            initialValue = initialApiKey,
            onDismiss = { showProfileNameInput = true },
            onNext = {
                event(ProfileEvent.UpdateCurrentProfile(id = activeProfileId, apiKey = it, pushToProfiles = true))
                event(ProfileEvent.ResetActiveProfile)
                onDismiss()
            }
        )
    }
}