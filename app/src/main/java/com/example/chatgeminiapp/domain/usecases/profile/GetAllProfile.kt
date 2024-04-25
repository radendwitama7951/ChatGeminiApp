package com.example.chatgeminiapp.domain.usecases.profile

import com.example.chatgeminiapp.domain.models.profile.InternalProfile
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.profilesMock
import javax.inject.Inject

class GetAllProfile @Inject constructor(

) {
    suspend operator fun invoke(): List<InternalProfile> {
        return profilesMock
    }
}