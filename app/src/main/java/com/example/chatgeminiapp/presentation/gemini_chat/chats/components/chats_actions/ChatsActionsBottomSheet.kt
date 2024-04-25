package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_actions

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.chatgeminiapp.presentation._common.interfaces.ActionItem
import com.example.chatgeminiapp.presentation._common.resources.Dimens.extraLargePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import kotlinx.coroutines.CoroutineScope

private data class ChatsActionItem(
    val prop: ActionItem,
    val action: (Long) -> Unit,
    val color: Color?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsActionsBottomSheet(
    modifier: Modifier = Modifier
        .alpha(.75f),
    chatGroupId: Long,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
    windowInsets: WindowInsets = WindowInsets.displayCutout,


    onNewChatGroup: (Long) -> Unit,
    onRenameChatsAction: (Long) -> Unit = {},
    onDeleteChatsAction: (Long) -> Unit = {},
    onDismiss: () -> Unit = {},

    ) {
    val chatsItemActions = listOf(
        ChatsActionItem(
            ActionItem(
                Icons.Outlined.AddCircleOutline,
                "New conversation",
                "Start new conversation"
            ),
            color = null,
            action = onNewChatGroup
        ),
        ChatsActionItem(
            ActionItem(
                Icons.Outlined.Edit,
                "Rename",
                "Rename current chats session title"
            ),
            color = null,
            action = onRenameChatsAction
        ),
        ChatsActionItem(
            ActionItem(
                icon = Icons.Outlined.Delete,
                "Delete",
                "Delete current chats session",
            ),
            action = onDeleteChatsAction,
            color = MaterialTheme.colorScheme.error
        )
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        windowInsets = windowInsets
    ) {
        Column(
            modifier = modifier
                .padding(bottom = extraLargePadding1),
        ) {
            chatsItemActions.forEach {
                NavigationDrawerItem(
                    colors = if (it.color != null) NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = it.color,
                        unselectedIconColor = it.color
                    )
                    else NavigationDrawerItemDefaults.colors(),
                    shape = RoundedCornerShape(percent = 10),
                    label = {
                        Text(
                            modifier = Modifier.padding(horizontal = smallPadding1),
                            text = it.prop.label,
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = it.prop.icon,
                            contentDescription = it.prop.description,
                        )
                    },
                    onClick = {
                        it.action(chatGroupId)
                        onDismiss()
                    }
                )
            }


        }

    }
}





