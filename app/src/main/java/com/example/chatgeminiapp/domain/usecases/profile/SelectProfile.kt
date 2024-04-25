package com.example.chatgeminiapp.domain.usecases.profile

import com.example.chatgeminiapp.domain.models.profile.InternalProfile
import javax.inject.Inject

class SelectProfile @Inject constructor(

) {
    operator fun invoke(profileId: Long): InternalProfile {
        return InternalProfile(
            id = 0,
            name = "Anon",
            avatar = null,
            apiKey = "1234",
            createdAt = "30-11-1991",
            editedAt = 49490480
        )
    }
}