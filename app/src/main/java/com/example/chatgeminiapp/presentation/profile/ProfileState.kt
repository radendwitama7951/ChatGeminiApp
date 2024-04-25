package com.example.chatgeminiapp.presentation.profile

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.profile.InternalProfile

data class ProfileState (
    val profiles: List<InternalProfile> = emptyList(),
    val activeProfileId: Long? = null,

    val isLoading: Boolean = false,

    val currentProfileName: String? = null,
    val currentProfileApiKey: String? = null,
    val currentProfileAvatar: Bitmap? = null
)

