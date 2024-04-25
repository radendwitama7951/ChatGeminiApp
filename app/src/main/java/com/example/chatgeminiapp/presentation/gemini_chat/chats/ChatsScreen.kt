package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size
import com.example.chatgeminiapp._common.resources.Constants.GEMINI_API_KEY
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_layouts.ChatTopBar
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.ChatPromptInput
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.ChatsActionsBottomSheet
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays.ChatsList
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.DeleteChatsDialog
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.ImageSelector
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_layouts.NavigationDrawerSheet
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions.RenameChatsDialog
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays.ChatsEditProfileDialogForm
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays.ProfileDialog
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays.StartUpChatList
import com.example.chatgeminiapp.presentation.profile.ProfileEvent
import com.example.chatgeminiapp.presentation.profile.components.profile_actions.EditProfileDialog
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val fastAnim1 = 150
const val fastAnim2 = 300
const val mediumAnim1 = 500
const val mediumAnim2 = 800
const val slowAnim1 = 1200
const val slowAnim2 = 1600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    state: ChatsState,
    event: (ChatsEvent) -> Unit,

     onNavigateToProfile: (Long) -> Unit = {}
) {
    // Init
    var showChatsActionsBottomSheet: Boolean by remember { mutableStateOf(false) }
    var showRenameChatsDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var showProfileDialog: Boolean by remember { mutableStateOf(false) }
    var showEditProfileDialog: Boolean by remember { mutableStateOf(false) }
    var showDeleteChatsDialog: Boolean by remember { mutableStateOf(false) }
    var showImageSelectorBottomSheet: Boolean by remember { mutableStateOf(false) }
    val navigationDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val chatListState = rememberLazyListState()

    val scope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    if (!state.isRequest) {
        keyboardController?.show()
        LaunchedEffect(Unit) {
            chatListState.scrollToItem(
                3,
                -540
            )
        }
    }


    ModalNavigationDrawer(
        drawerState = navigationDrawerState,
        drawerContent = {
            NavigationDrawerSheet(
                isSwitchChatGroupAllowed = !state.isLoading && !state.isRequest,
                chatGroups = state.chatGroups,
                selectedChatGroup = state.selectedGroup,
                onSelectedChatGroup = {
                    scope.launch {
                        navigationDrawerState.animateTo(
                            DrawerValue.Closed,
                            anim = tween(mediumAnim2, easing = FastOutSlowInEasing)
                        )
                    }
                    event(ChatsEvent.SwitchChatGroup(it))
                },
                onSettingAction = {
                    if (state.selectedGroup != null) {
                        scope.launch {
                            keyboardController?.hide()
                            delay(fastAnim1.toLong())
                            navigationDrawerState.animateTo(
                                DrawerValue.Closed, anim = tween(
                                    mediumAnim2
                                )
                            )
                        }
                        showChatsActionsBottomSheet = true
                    }
                }
            )
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                ChatTopBar(
                    modifier = Modifier,
                    isLoading = state.isLoading,
                    showActionsButton = state.selectedGroup != null,
                    onNewChatGroup = {
                        event(ChatsEvent.StartNewChat)
                    },
                    onDrawerToggle = {
                        scope.launch {
                            navigationDrawerState.apply {
                                open()
                            }
                        }
                    },
                    onMoreAction = {
                        scope.launch {
                            keyboardController?.hide()
                            delay(fastAnim1.toLong())
                            showChatsActionsBottomSheet = true
                        }
                    },
                    onProfileAction = {
                        showProfileDialog = true
                    }
                )
            },
        ) {
            Column(
                modifier = modifier
                    .padding(top = it.calculateBottomPadding())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {

                if (state.selectedGroup == null) {
                    StartUpChatList(
                        modifier = Modifier.weight(1f),
                        chatGroups = state.chatGroups.sortedByDescending { it.editedAt }.take(4),
                        onSelectedGroup = {
                            event(ChatsEvent.SwitchChatGroup(it))
                        }
                    )
                } else {
                    ChatsList(
                        state = chatListState,
                        modifier = Modifier.weight(1f),
                        chatList = state.chatList,
                        isRequesting = state.isRequest
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = mediumPadding2,
                            start = smallPadding1,
                            end = smallPadding1
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Spacer(modifier = Modifier.height(8.dp))
                    ChatPromptInput(
                        isSendAllowed = !state.isRequest,
                        initialValue = state.prompt,
                        selectedImage = state.bitmapCroppedUri,
                        onSendPrompt = {
                            if (it.isNotBlank()) {
                                if (state.bitmapCroppedUri != null) {
                                    scope.launch {
                                        val bitmap = getBitmap(state.bitmapCroppedUri, context)
                                        event(
                                            ChatsEvent.SendPrompt(
                                                state.selectedGroup,
                                                it,
                                                bitmap,
                                                state.bitmapSourceUri
                                            )
                                        )
                                        chatListState.animateScrollToItem(
                                            0,
                                        )

                                    }
                                } else {
                                    event(
                                        ChatsEvent.SendPrompt(
                                            state.selectedGroup,
                                            it,
                                            null,
                                            state.bitmapSourceUri
                                        )
                                    )
                                    scope.launch {
                                        chatListState.animateScrollToItem(
                                            0,
                                        )
                                    }
                                }


                            }
                        },
                        onImageSelection = {
                            showImageSelectorBottomSheet = true
                        }
                    )

                }
}

            // Floating element
            if (showProfileDialog) {
                ProfileDialog(
                    username = "Dwitama",
                    apiKey = GEMINI_API_KEY,
                    onDismiss = { showProfileDialog = false },
                    onEdit = { showEditProfileDialog = true },
                    onLogOut = { onNavigateToProfile(1) }
                )
            }

            if (showEditProfileDialog) {
                ChatsEditProfileDialogForm(
                    onDismiss = { showEditProfileDialog = false }
                )
            }



            if (showChatsActionsBottomSheet && state.selectedGroup != null) {
                ChatsActionsBottomSheet(
                    chatGroupId = state.selectedGroup,
                    onRenameChatsAction = {
                        showRenameChatsDialog = true
                    },
                    onDeleteChatsAction = {
                        showDeleteChatsDialog = true
                    },
                    onDismiss = {
                        showChatsActionsBottomSheet = false
                    },
                    onNewChatGroup = {
                        event(ChatsEvent.StartNewChat)
                    }
                )
            }

            if (showRenameChatsDialog && state.selectedGroup != null) {
                val currentChatGroupTitle =
                    getSelectedGroupTitle(state.selectedGroup, state.chatGroups)
                RenameChatsDialog(
                    chatGroupId = state.selectedGroup,
                    chatGroupTitle = currentChatGroupTitle,
                    onRenameChats = { id, newTitle ->
                        event(ChatsEvent.RenameChatGroup(id, newTitle))
                        showRenameChatsDialog = false
                    },
                    onDismiss = {
                        showRenameChatsDialog = false
                    }
                )

            }

            if (showDeleteChatsDialog && state.selectedGroup != null) {
                DeleteChatsDialog(
                    chatGroupId = state.selectedGroup,
                    onDelete = { id ->
                        event(ChatsEvent.DeleteChatGroup(id))
                        event(ChatsEvent.StartNewChat)
                    },
                    onDismiss = {
                        showDeleteChatsDialog = false
                    }
                )
            }

            if (showImageSelectorBottomSheet) {
                ImageSelector(
                    bitmapDisplay = state.bitmapCroppedUri,
//                    onResultBitmap = {
//                        event(ChatsEvent.UpdateBitmapCropped(it))
//                    },
                    onResultUri = { sourceUri ->
                        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        context.contentResolver.takePersistableUriPermission(sourceUri, flag)
                        event(ChatsEvent.UpdateBitmapUri(sourceUri.toString()))
                    },
                    onResultBitmapCroppedUri = { croppedUri ->
                        event(ChatsEvent.UpdateBitmapCroppedUri(croppedUri.toString()))

                    },
                    onDismiss = {
                        showImageSelectorBottomSheet = false
                    },
                    onCancelSelection = {
                        event(ChatsEvent.ResetBitmapUri)
                        event(ChatsEvent.UpdateBitmapCroppedUri(null))
                    },
                    onConfirm = {
                        keyboardController?.show()
                    }
                )
            }

        }

    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
fun ChatsScreenPreview(
) {
//    lateinit var mockChatsState: ChatsState


    val mockChatsState = ChatsState(
        selectedGroup = 2, // Simulate a selected group with ID 2
        chatGroups = listOf(
            InternalChatGroup(
                id = 1,
                title = "Random Title 1",
                createdAt = "2024-04-20 10:00:00", // Replace with formatted timestamp function
                editedAt = System.currentTimeMillis() // Replace with your timestamp logic
            ),
            InternalChatGroup(
                id = 2,
                title = "Random Title 2",
                createdAt = "2024-04-19 15:30:00", // Replace with formatted timestamp function
                editedAt = System.currentTimeMillis() - 3600000 // Simulate edit 1 hour ago (adjust milliseconds as needed)
            ),
            InternalChatGroup(
                id = 3,
                title = "Random Title 3",
                createdAt = "2024-04-18 18:00:00", // Replace with formatted timestamp function
                editedAt = System.currentTimeMillis() - 86400000 // Simulate edit 1 day ago (adjust milliseconds as needed)
            ),
            InternalChatGroup(
                id = 4,
                title = "Random Title 4",
                createdAt = "2024-04-17 12:45:00", // Replace with formatted timestamp function
                editedAt = System.currentTimeMillis() - 172800000 // Simulate edit 2 days ago (adjust milliseconds as needed)
            )
        ),
        chatList = emptyList(), // Empty list for chatList (replace if needed)
        prompt = "Enter your message here...",
        bitmapSourceUri = "" // Empty bitmapUri
    )


    ChatGeminiAppTheme(dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChatsScreen(
                state = mockChatsState,
                event = {}
            )
        }
    }
}


suspend fun getBitmap(source: String, context: Context): Bitmap {
    val imageRequest = ImageRequest.Builder(context)
        .data(source)
        .size(Size.ORIGINAL)
        .build()

    val imageLoader = ImageLoader(context)

    val imageResult = (imageLoader.execute(imageRequest) as SuccessResult).drawable
    val imageBitmap = (imageResult as BitmapDrawable).bitmap
    return imageBitmap
}

private fun getSelectedGroupTitle(
    selectedGroupId: Long,
    chatGroups: List<InternalChatGroup>
): String {
    val selectedGroup = chatGroups.find {
        it.id == selectedGroupId
    }!!
    return selectedGroup.title ?: selectedGroup.createdAt
}
