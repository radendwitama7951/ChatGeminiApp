package com.example.chatgeminiapp.domain.models.profile

import android.graphics.Bitmap

data class InternalProfile(
    val id: Long = 0,
    val name: String,
    val avatar: Bitmap? = null,
    val apiKey: String,
    val createdAt: String = "",
    val editedAt: Long // TimeStamps
)
