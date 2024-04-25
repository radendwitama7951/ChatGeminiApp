package com.example.chatgeminiapp.presentation.profile._routing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatgeminiapp.presentation.profile.ProfileEvent
import com.example.chatgeminiapp.presentation.profile.ProfileViewModel
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.ActiveProfileDialog
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.CreateEditProfileDialogForm
import com.example.chatgeminiapp.presentation.profile.components.profile_displays.ProfileGridList

@Composable
fun ProfileRouter(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ProfileRoute.ProfileList.route,

    onNavigateToGeminiChat: (Long) -> Unit = {},
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val state = viewModel.state
    val event = viewModel::onEvent

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ProfileRoute.ProfileList.route) { backstackEntry ->
            ProfileGridList(
                profiles = state.profiles,
                onConfigProfile = { profileId ->
                    navController.navigate("activeProfile/${profileId}")
                },
                onSelectProfile = { profileId ->
                    onNavigateToGeminiChat(profileId)
                },
                onAddProfile = {
                    navController.navigate(ProfileRoute.CreateProfile.route)
                }
            )
        }

        composable(
            route = ProfileRoute.ActiveProfile.route,
            arguments = ProfileRoute.ActiveProfile.arguments
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(
                ProfileRoute.ActiveProfile.arguments[0].name
            )?.let { profileId ->
                event(ProfileEvent.GetSelectedProfile(profileId))
                ActiveProfileDialog(
                    event = event,
                    profileId = profileId,
                    profileName = state.currentProfileName ?: "Anonymous",
                    apiKey = state.currentProfileApiKey ?: "1234",
                    onEdit = { navController.navigate(ProfileRoute.EditProfile.route) },
                    onEditApiKey = { navController.navigate(ProfileRoute.EditProfileApiKey.route) },
                    onDismiss = {
                        navController.popBackStack(
                            ProfileRoute.ProfileList.route,
                            inclusive = false
                        )
                    },
                    onLogIn = {
                        onNavigateToGeminiChat(it)
                    }
                )
            }
        }

        composable(
            route = ProfileRoute.EditProfile.route,
        ) {
            CreateEditProfileDialogForm(
                activeProfileId = state.activeProfileId!!,
                initialProfileName = state.currentProfileName!!,
                initialApiKey = state.currentProfileApiKey!!,
                event = event,
                onDismiss = { navController.popBackStack() },
            )
        }
        composable(
            route = ProfileRoute.EditProfileApiKey.route,
        ) {
            CreateEditProfileDialogForm(
                activeProfileId = state.activeProfileId!!,
                initialProfileName = state.currentProfileName!!,
                initialApiKey = state.currentProfileApiKey!!,
                editApiKeyOnly = true,
                event = event,
                onDismiss = { navController.popBackStack() },
            )
        }
        composable(
            route = ProfileRoute.CreateProfile.route,
        ) {
            CreateEditProfileDialogForm(
                event = event,
                onDismiss = {
                    navController.popBackStack(ProfileRoute.ProfileList.route, inclusive = false)
                },
            )
        }

    }
}