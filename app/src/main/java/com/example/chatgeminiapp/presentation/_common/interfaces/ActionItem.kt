package com.example.chatgeminiapp.presentation._common.interfaces

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem (
    val icon: ImageVector,
    val label: String,
    val description: String? = null,
    val url: String = "",
    val action: () -> Unit = {}
)