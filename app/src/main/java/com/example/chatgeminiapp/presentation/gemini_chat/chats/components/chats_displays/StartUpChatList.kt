package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatgeminiapp.domain.models.gemini_chat.InternalChatGroup
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartUpChatList(
    modifier: Modifier = Modifier,
    chatGroups: List<InternalChatGroup> = emptyList(),
//    chatList: List<InternalChat> = emptyList(),

    onSelectedGroup: (Long) -> Unit = {},
    onStartChats: () -> Unit = {}
) {
    val geminiLinearGradient = Brush.linearGradient(
        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary),
        start = Offset.Zero,
        end = Offset(x = 340f, y = 340f)
    )

    Column(
        modifier = modifier
            .padding(vertical = mediumPadding2, horizontal = mediumPadding1),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                brush = geminiLinearGradient
            ),
            text = "Hello, \$User",
        )
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .25f)
            ),
            text = "How can I assist you today ?"
        )
        Spacer(modifier = Modifier.height(mediumPadding2))
        Text(
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = .5f
                )
            ),
            text = if (chatGroups.isNotEmpty()) "Recent activity" else ""
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            itemsIndexed(items = chatGroups) { index, chatGroup ->
                Card(
                    elevation = CardDefaults.cardElevation(smallPadding2),
                    onClick = { onSelectedGroup(chatGroup.id) },
                    modifier = modifier
                        .defaultMinSize(minHeight = mediumCard1)
                        .padding(vertical = smallPadding2, horizontal = smallPadding2)
                ) {
                    Column(
                        modifier = modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(smallPadding2)
                    ) {
                        Text(
                            modifier =
                            modifier.padding(bottom = mediumPadding1)
                                .background(MaterialTheme.colorScheme.surface)
                            ,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            text = chatGroup.title ?: chatGroup.createdAt,
                        )
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .clip(RoundedCornerShape(percent = 10))
                        ) {
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                text = "Created at: ${chatGroup.createdAt}\nLast edited: ${
                                    SimpleDateFormat(
                                        "HH:mm MM-dd-yyyy",
                                        Locale.getDefault()
                                    ).format(
                                        Date(
                                            chatGroup.editedAt
                                        )
                                    )
                                }",
                            )

                        }
                    }
                }
            }
        }
    }


//    Box(
//        modifier = modifier
//            .padding(bottom = largePadding2),
//    ) {
//        Text(
//            style = MaterialTheme.typography.displaySmall,
//            text = "What can I help you today ?"
//        )
//    }
//    ContentGrid(items = chatGroups, onSelected = onSelectedGroup)

}

@Composable
private fun ContentGrid(
    modifier: Modifier = Modifier,
    items: List<InternalChatGroup>,
    onSelected: (Long) -> Unit
) {

    LazyColumn(
        reverseLayout = true,
        modifier = modifier,
        contentPadding = PaddingValues(mediumPadding2),
    ) {
        itemsIndexed(
            items = items,
        ) { index, chatGroup ->
            Column(
                modifier = modifier

//                        modifier = modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2)
                    .defaultMinSize(minHeight = mediumCard1)
                    .clickable {
                        onSelected(chatGroup.id)
                    },
            ) {

                Text(
                    modifier = modifier,
                    style = MaterialTheme.typography.titleLarge,
                    text = "Chat Group",
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(percent = 10))
                ) {
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = "${chatGroup.id}. ${chatGroup.title}\n${
                            SimpleDateFormat(
                                "HH:mm MM-dd-yyyy",
                                Locale.getDefault()
                            ).format(Date(chatGroup.editedAt))
                        }",
                    )

                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun StartupChatListPreview() {
    ChatGeminiAppTheme(dynamicColor = false) {

        Surface {

            Column(modifier = Modifier.fillMaxSize()) {
                StartUpChatList(
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
                    )
                )
            }
        }
    }
}

/*


        itemsIndexed(
            items = items,
        ) { index, chatGroup ->
            Surface(
                modifier = modifier
                    .clickable {
                        onSelected(chatGroup.id)
                    }
                    .defaultMinSize(minHeight = mediumCard1),
                shadowElevation = smallPadding3
            ) {
                Column(
                    modifier = modifier
                        .padding(smallPadding2)
                ) {
                    Text(
                        modifier = modifier,
                        style = MaterialTheme.typography.titleLarge,
                        text = "Chat Group",
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(percent = 10))
                    ) {
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            text = "${chatGroup.id}. ${chatGroup.title}\n${formatTimestamp(chatGroup.editedAt)}",
                        )

                    }
                }
            }
        }
* */
