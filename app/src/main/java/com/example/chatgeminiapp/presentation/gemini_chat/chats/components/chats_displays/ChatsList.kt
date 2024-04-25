package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChat
import com.example.chatgeminiapp.presentation._common.resources.Dimens.extraLargePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallCard3
import com.example.chatgeminiapp.presentation.gemini_chat.chats.ChatsEvent
import kotlinx.coroutines.CoroutineScope

@Composable
fun ChatsList(
    modifier: Modifier = Modifier,
    chatList: List<InternalChat>,

    isRequesting: Boolean,

    state: LazyListState = rememberLazyListState(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
        LazyColumn(
            modifier = modifier
            ,
            state = state,
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(largePadding1))
            }
            item {
                if (isRequesting) {
                    ModelChatShimmerSkeleton()

                }
            }
            itemsIndexed(items = chatList) { index, chat ->
                if (chat.isFromUser) {
                    UserChatItem(
                        prompt = chat.prompt, bitmapSourceUri = chat.bitmapUri
                    )
                } else {
                    ModelChatItem(response = chat.prompt)
                }
            }
        }

}