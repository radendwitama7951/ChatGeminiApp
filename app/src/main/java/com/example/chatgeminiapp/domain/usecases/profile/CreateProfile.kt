package com.example.chatgeminiapp.domain.usecases.profile

import android.graphics.Bitmap
import com.example.chatgeminiapp.domain.models.profile.InternalProfile
import javax.inject.Inject

class CreateProfile @Inject constructor () {
    suspend operator fun invoke (
        name: String,
        apiKey: String,
        avatar: Bitmap?,
    ): InternalProfile {
        return InternalProfile(
            id = 1234,
            name = name,
            apiKey = apiKey,
            avatar = avatar,
            editedAt = System.currentTimeMillis()
        )
    }
}