package com.example.chatgeminiapp.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(

): ViewModel () {

    val state: ProfileState by mutableStateOf(ProfileState())

    fun onEvent (event: ProfileEvent) {}
}