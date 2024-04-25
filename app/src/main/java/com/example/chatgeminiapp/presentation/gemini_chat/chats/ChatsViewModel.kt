package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.domain.usecases.gemini_chat.CloseChatSession
import com.example.chatgeminiapp.domain.usecases.gemini_chat.DeleteSelectedChatGroup
import com.example.chatgeminiapp.domain.usecases.gemini_chat.GetAllChatGroup
import com.example.chatgeminiapp.domain.usecases.gemini_chat.GetChatsResponse
import com.example.chatgeminiapp.domain.usecases.gemini_chat.GetGroupChatTitle
import com.example.chatgeminiapp.domain.usecases.gemini_chat.StartNewChats
import com.example.chatgeminiapp.domain.usecases.gemini_chat.SwitchChatGroup
import com.example.chatgeminiapp.domain.usecases.gemini_chat.UpdateChatGroupTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getResponseUseCase: GetChatsResponse,
    private val getChatGroupsUseCase: GetAllChatGroup,
    private val startNewChatsUseCase: StartNewChats,
    private val getChatsTitleUseCase: GetGroupChatTitle,
    private val switchChatsUseCase: SwitchChatGroup,
    private val renameChatsUseCase: UpdateChatGroupTitle,
    private val closeChatsUseCase: CloseChatSession,
    private val deleteChatsUseCase: DeleteSelectedChatGroup
) : ViewModel() {
    var state: ChatsState by mutableStateOf(ChatsState())
        private set

    init {
        viewModelScope.launch {
            toggleLoading(true)
            getChatGroups()
            if (state.chatGroups.isNotEmpty()) {
                val recentChatsId = state.chatGroups.maxBy { it.editedAt }.id
                onEvent(ChatsEvent.SwitchChatGroup(recentChatsId))
            } else {
                onEvent(ChatsEvent.StartNewChat)
            }
            toggleLoading(false)
        }
    }

    override fun onCleared() {
        closeChatsUseCase()

        super.onCleared()
    }

    fun onEvent(event: ChatsEvent) {
        when (event) {
            is ChatsEvent.ToggleRequest -> {
                viewModelScope.launch {
                    toggleRequest(event.isRequest)
                }
            }

            is ChatsEvent.ToggleLoading -> {
                viewModelScope.launch {
                    toggleLoading(event.isLoading)
                }
            }
            is ChatsEvent.StartNewChat -> {
                // Reset state except chatGroups
                state = ChatsState(
                    chatGroups = state.chatGroups,
                )
            }

            is ChatsEvent.RefreshChatGroup -> {
                viewModelScope.launch {
                    toggleLoading(true)
                    getChatGroups()
                    toggleLoading(false)

                }
            }


            is ChatsEvent.SwitchChatGroup -> {
                switchChats(event.id)
            }

            is ChatsEvent.DeleteChatGroup -> {
                deleteChats(event.id)
            }

            is ChatsEvent.RenameChatGroup -> {
                renameChats(event.id, event.title)
            }

            is ChatsEvent.SendPrompt -> {
                sendPrompt(event.selectedGroup, event.prompt, event.bitmap, event.bitmapSource)
            }

            is ChatsEvent.UpdatePrompt -> {
                state = state.copy(prompt = event.newPrompt)
            }

            is ChatsEvent.ResetBitmapUri -> {
                state = state.copy(bitmapSourceUri = null)
            }

            is ChatsEvent.UpdateBitmapUri -> {
                state = state.copy(bitmapSourceUri = event.uri)
            }

            is ChatsEvent.UpdateBitmapCroppedUri -> {
                state = state.copy(bitmapCroppedUri = event.bitmapCroppedUri)
            }

            is ChatsEvent.ResetBitmap -> {
                state = state.copy(bitmapSourceUri = null)
                state = state.copy(bitmapCroppedUri = null)
            }

        }
    }


    private suspend fun toggleLoadingAndRequest(isLoading: Boolean) {
        state = state.copy(
            isLoading = isLoading,
            isRequest = isLoading
        )
    }

    private suspend fun toggleLoading(isLoading: Boolean? = null) {
        if (isLoading == false) delay(1000)
        state = state.copy(isLoading = isLoading ?: !state.isLoading)
    }
    private suspend fun toggleRequest(isRequesting: Boolean? = null) {
        if (isRequesting == true) delay(800)
        state = state.copy(isRequest = isRequesting ?: !state.isRequest)
    }

    private fun deleteChats(selectedGroup: Long) {
        viewModelScope.launch {
            toggleLoading(true)
            val deletedChatsId = deleteChatsUseCase(selectedGroup)
            state = state.copy(
                chatGroups = state.chatGroups.toMutableList().filter { it.id != deletedChatsId }
            )
            toggleLoading(false)
        }
    }

    private fun clearInputState() {
        state = state.copy(
            isLoading = false,
            bitmapSourceUri = null,
            bitmapCroppedUri = null,
            prompt = "",
        )
    }

    private fun renameChats(selectedChatGroup: Long, newTitle: String) {
        viewModelScope.launch {
            toggleLoading(true)
            val updatedChatGroup = renameChatsUseCase(selectedChatGroup, newTitle)
            state = state.copy(
                chatGroups = state.chatGroups.toMutableList().map {
                    if (it.id == selectedChatGroup) updatedChatGroup
                    else it
                }
            )
            toggleLoading(false)
        }
    }

    /*
    * SendPrompt also mean start new chats
    * if Event Reset Chats triggered
    *
    * */
    private fun sendPrompt(
        selectedGroupId: Long?,
        prompt: String,
        bitmap: Bitmap?,
        bitmapSource: String?
    ) {
        if (prompt.isNotEmpty()) {
            viewModelScope.launch {
                /*
                * Initiate ChatGroup and It's title
                * Happen when it's a first request
                * when no chat group selected
                * */
                toggleLoadingAndRequest(true)
                if (selectedGroupId == null) {
                    val newGroupChatId = startNewChats()
                    addPrompt(prompt, bitmap, bitmapSource)
                    val firstResponse = getResponse(newGroupChatId, prompt, bitmap, bitmapSource)
                    generateChatGroupTitle(newGroupChatId, firstResponse)
//                switchChats(state.selectedGroup!!)
                } else {
                    /*
                    * If it's not first request of selected chats group
                    * just get response and update the ChatList
                    * */
                    addPrompt(prompt, bitmap, bitmapSource)
                    getResponse(selectedGroupId, prompt, bitmap, bitmapSource)
                }
                toggleLoadingAndRequest(false)
            }
        }
    }


    private fun switchChats(id: Long) {
        viewModelScope.launch {
            toggleLoading(true)
            val selectedGroupChatList = switchChatsUseCase(id)
            state = state.copy(
                chatList = selectedGroupChatList,
                selectedGroup = id,
            )

            clearInputState()
            toggleLoading(false)
        }

    }

    private suspend fun generateChatGroupTitle(selectedGroup: Long, prompt: String): String? {
        val groupChatWithNewTitle = getChatsTitleUseCase(selectedGroup, prompt)
        state = state.copy(
            chatGroups = state.chatGroups.toMutableList().map { group ->
                if (group.id == state.selectedGroup) {
                    groupChatWithNewTitle
                } else {
                    group
                }

            },
        )
        return groupChatWithNewTitle.title

    }

    private suspend fun startNewChats(): Long {
        val newChatGroup = startNewChatsUseCase()
        state = state.copy(
            chatGroups = state.chatGroups.toMutableList().apply {
                add(0, newChatGroup)
            },
            chatList = emptyList(),
            selectedGroup = newChatGroup.id,
        )

        return newChatGroup.id

    }

    private suspend fun getChatGroups() {
        val chatGroups = getChatGroupsUseCase().sortedByDescending { it.createdAt }
        state = state.copy(chatGroups = chatGroups)
    }


    private fun addPrompt(prompt: String, bitmap: Bitmap?, bitmapSource: String?) {
        state = state.copy(
            chatList = state.chatList.toMutableList().apply {
                add(
                    0,
                    InternalChat(
                        prompt = prompt,
                        bitmap = bitmap,
                        bitmapUri = bitmapSource,
                        isFromUser = true
                    )
                )
            },
        )
        clearInputState()
    }

    private suspend fun getResponse(
        selectedChatGroup: Long?,
        prompt: String,
        bitmap: Bitmap?,
        bitmapSource: String?
    ): String {
        val response = getResponseUseCase(
            chatGroupId = selectedChatGroup ?: 0,
            prompt = prompt,
            bitmap = bitmap,
            bitmapSource = bitmapSource
        )
        state = state.copy(
            chatList = state.chatList.toMutableList().apply { add(0, response) },
            chatGroups = state.chatGroups.map {
                if (it.id == state.selectedGroup) {
                    it.copy(editedAt = System.currentTimeMillis())
                } else {
                    it
                }
            }
        )
        return response.prompt
    }
}

