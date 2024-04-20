package com.example.chatgeminiapp.presentation.gemini_chat.chats

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatgeminiapp.presentation.gemini_chat._common.components.ChatTopBar
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.ChatPromptInput
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.ChatsList
import com.example.chatgeminiapp.presentation.gemini_chat.chats.components.ImageSelector
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    state: ChatsState,
    event: (ChatsEvent) -> Unit,
    navigateToImageSelector: (String) -> Unit = {},
) {
    // Init
    var showBottomSheet: Boolean by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ChatTopBar(
                    onDrawerToggle = {
                        scope.launch {
                            drawerState.apply {
                                open()
                            }
                        }
                    }

                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ChatsList(modifier = Modifier.weight(1f), chatList = state.chatList)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    ChatPromptInput(
                        initialValue = state.prompt,
                        onValueChange = {
                            event(ChatsEvent.UpdatePrompt(it))
                        },
                        onSendPrompt = {
                            event(ChatsEvent.SendPrompt(state.prompt, state.bitmap))
                            event(ChatsEvent.ResetBitmapUri)
                        },
                        onImageSelection = {
                            showBottomSheet = true
                        }
                    )

                }

            }


            if (showBottomSheet) {
                Log.d("test", "ImageDisplay ${state.bitmap}")
                ImageSelector(
                    bitmapDisplay = state.bitmap,
                    onResultBitmap = {
                        event(ChatsEvent.UpdateBitmap(it))
                    },
                    onResultUri = {
                        event(ChatsEvent.UpdateBitmapUri(it.toString()))
                    },
                    onDismiss = {
                        showBottomSheet = false
                    },
                    onCancelSelection = {
                        event(ChatsEvent.ResetBitmapUri)
                        event(ChatsEvent.UpdateBitmap(null))
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
    ChatGeminiAppTheme(dynamicColor = false) {
        ChatsScreen(
            state = ChatsState(

            ),
            event = {}
        )
    }
}
