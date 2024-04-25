package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_layouts

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatgeminiapp.domain.models.gemini_chat.ChatGroup
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.presentation._common.interfaces.ActionItem
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationDrawerSheet(
    modifier: Modifier = Modifier,
    chatGroups: List<InternalChatGroup> = emptyList(),
    selectedChatGroup: Long? = null,

    onSelectedChatGroup: (Long) -> Unit = {},
    onSettingAction: () -> Unit = {},
    isSwitchChatGroupAllowed: Boolean
) {
    ModalDrawerSheet(
        modifier = modifier
            .fillMaxWidth(.85f)
    ) {
        Column(modifier = modifier) {
            Text(
                text = "Gemini",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(mediumPadding2)
            )
            Divider()
        }

        Column(
            modifier = Modifier
                .weight(.7f, fill = true)
                .padding(start = mediumPadding2, end = mediumPadding1)
        ) {
            if (chatGroups.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f, fill = false)
                ) {
                    Text(
                        text = "Recent",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(mediumPadding2)
                    )
                    chatGroups.sortedByDescending { it.editedAt }.forEach { chatGroup ->
                        NavigationDrawerItem(
                            modifier = Modifier,
                            colors =
                            NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = .5f
                                )
                            ),
                            label = {
                                Text(
                                    style = MaterialTheme.typography.labelLarge,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2,
                                    text = chatGroup.title ?: chatGroup.createdAt
                                )
                            },
                            selected = chatGroup.id == selectedChatGroup,
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.ChatBubbleOutline,
                                    contentDescription = chatGroup.title
                                )
                            },
                            onClick = {
                                if (chatGroup.id != selectedChatGroup && isSwitchChatGroupAllowed) {
                                    onSelectedChatGroup(chatGroup.id)
                                }
                            }
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(.3f, fill = true)
                .padding(horizontal = smallPadding1),
//                .defaultMinSize(minHeight = mediumCard1),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState(), enabled = false)
            ) {
                BottomMenu(
                    footnotes = listOf(
                        "● Created by Raden Dwitama Baliano",
                        "https://github.com/radendwitama7951/ChatGeminiApp.git"
                    ),
                    onSettingAction = onSettingAction
                )
            }
        }

    }

}


@Composable
private fun BottomMenu(
    modifier: Modifier = Modifier,
    footnotes: List<String> = emptyList(),
    onClick: (url: String) -> Unit = {},
    onSettingAction: () -> Unit
) {
    val menus = listOf(
        ActionItem(
            icon = Icons.Outlined.Settings,
            "Setting",
            "Chats settings action",
            action = onSettingAction
        ),
        ActionItem(
            icon = Icons.AutoMirrored.Outlined.HelpOutline,
            "Help",
            "Redirect to Google Help"
        ),
        ActionItem(
            icon = Icons.Outlined.History,
            "History",
            "Redirect to Google Account activity",
        )
    )
    menus.forEach { menu ->
        NavigationDrawerItem(
            shape = RoundedCornerShape(percent = 10),
            label = {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = menu.label
                )
            },
            selected = false,
            icon = {
                Icon(
                    imageVector = menu.icon,
                    contentDescription = menu.description
                )
            },
            onClick = {
                menu.action()
            }
        )
    }
    Column(
        modifier = Modifier.padding(vertical = largePadding1, horizontal = mediumPadding2),
    ) {
        footnotes.forEachIndexed { index, footnote ->
            if (index == 0) {
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = footnote
                )
            } else {
                Text(
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                    ),
                    text = footnote
                )
            }
        }
    }


}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun NavigationDrawerSheetPreview(
    modifier: Modifier = Modifier,
    chatGroups: List<ChatGroup> = emptyList()
) {
    ChatGeminiAppTheme(dynamicColor = false) {
        Surface {
            NavigationDrawerSheet(
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
                    ),
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
                    ),
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
                    ),
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
                    ),
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
                    ),
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
                    ),
                ),
                isSwitchChatGroupAllowed = true
            )
        }
    }

}