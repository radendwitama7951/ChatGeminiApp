package com.example.chatgeminiapp.presentation.profile._routing

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ProfileRoute(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object ProfileScreen : ProfileRoute(route = "profiles")
    data object ProfileList: ProfileRoute(route = "list")
    data object ActiveProfile : ProfileRoute(
        route = "activeProfile/{profileId}",
        arguments = listOf(navArgument("profileId") { type = NavType.LongType })
    )

    data object EditProfile : ProfileRoute(route = "edit")
    data object EditProfileApiKey : ProfileRoute(route = "editApiKey")
    data object CreateProfile : ProfileRoute(route = "create")

}