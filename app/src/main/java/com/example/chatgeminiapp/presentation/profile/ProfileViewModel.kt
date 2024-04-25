package com.example.chatgeminiapp.presentation.profile

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgument
import com.example.chatgeminiapp.domain.models.profile.InternalProfile
import com.example.chatgeminiapp.domain.usecases.profile.CreateProfile
import com.example.chatgeminiapp.domain.usecases.profile.GetAllProfile
import com.example.chatgeminiapp.domain.usecases.profile.SelectProfile
import com.example.chatgeminiapp.presentation.profile._routing.ProfileRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val initProfilesUseCase: GetAllProfile,
    private val selectProfileUseCase: SelectProfile,
    private val createNewProfileUseCase: CreateProfile
) : ViewModel() {

    var state: ProfileState by mutableStateOf(ProfileState())

    init {
        viewModelScope.launch {
            toggleLoading(true)
            state = ProfileState(
                profiles = initProfilesUseCase()
            )
            toggleLoading(false)
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.NavigateToProfile -> {
                event.navController.navigate("edit/${event.id}")
            }

            is ProfileEvent.GetSelectedProfile -> {
                selectProfile(event.profileId)
            }

            is ProfileEvent.ResetActiveProfile -> {
                resetActiveProfile()
            }

            is ProfileEvent.UpdateCurrentProfile -> {
                updateCurrentProfile(
                    event.id,
                    event.name ?: state.currentProfileName,
                    event.apiKey ?: state.currentProfileApiKey,
                    event.avatar ?: state.currentProfileAvatar,
                    event.pushToProfiles
                )
            }
        }
    }

    private fun resetActiveProfile() {
       state = state.copy(
           activeProfileId = null,
           currentProfileAvatar = null,
           currentProfileApiKey = null,
           currentProfileName = null,
       )
    }

    private fun toggleLoading(isLoading: Boolean) {
        state = state.copy(isLoading = isLoading)
    }

    private fun updateCurrentProfile(
        id: Long?,
        newName: String?,
        newApiKey: String?,
        newAvatar: Bitmap?,
        pushToProfilesState: Boolean
    ) {
        viewModelScope.launch {
            toggleLoading(true)
            state = state.copy(
                currentProfileName = newName,
                currentProfileApiKey = newApiKey,
                currentProfileAvatar = newAvatar
            )
            if (pushToProfilesState) {
                if (id != null) {
                    state = state.copy(
                        profiles = state.profiles.map {
                            if (it.id == id) {
                                InternalProfile(
                                    id = id,
                                    name = newName ?: it.name,
                                    apiKey = newApiKey ?: it.apiKey,
                                    avatar = newAvatar,
                                    editedAt = System.currentTimeMillis()
                                )
                            } else {
                                it
                            }
                        }
                    )
                } else {
                    newApiKey?.let {
                        val newProfile = createNewProfileUseCase(
                            name = newName ?: "Anonymous",
                            apiKey = newApiKey,
                            avatar = newAvatar,
                        )
                        state = state.copy(
                            profiles = state.profiles.toMutableList().apply {
                                add(0, newProfile)
                            }
                        )
                    }
                }
            }
            toggleLoading(false)
        }
    }

    private fun selectProfile(id: Long) {
        viewModelScope.launch {
            toggleLoading(true)
            val profile = state.profiles.find { it.id == id }
            profile?.let {
                state = state.copy(
                    activeProfileId = id,
                    currentProfileName = profile.name,
                    currentProfileApiKey = profile.apiKey,
                    currentProfileAvatar = profile.avatar

                )
            }
            toggleLoading(false)
        }
    }

}