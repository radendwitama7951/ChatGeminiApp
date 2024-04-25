package com.example.chatgeminiapp.presentation.profile

import android.graphics.Bitmap
import androidx.navigation.NavController
import androidx.navigation.NavHostController

sealed class ProfileEvent {
    data class NavigateToProfile (val navController: NavController, val id: Long): ProfileEvent()
    data class GetSelectedProfile (val profileId: Long): ProfileEvent()

    data object ResetActiveProfile: ProfileEvent()
    data class UpdateCurrentProfile (val id: Long? = null, val name: String? = null, val apiKey: String? = null, val avatar: Bitmap? = null, val pushToProfiles: Boolean = false): ProfileEvent()

}
