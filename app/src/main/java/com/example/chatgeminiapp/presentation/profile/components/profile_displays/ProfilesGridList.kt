package com.example.chatgeminiapp.presentation.profile.components.profile_displays

import android.content.res.Configuration
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp._common.resources.Constants.GEMINI_API_KEY
import com.example.chatgeminiapp._common.utils.formatTimeStamp
import com.example.chatgeminiapp.data.remote.gemini_chat.api.GeminiChatApiImpl_Factory
import com.example.chatgeminiapp.domain.models.profile.InternalProfile
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largePadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallCard2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallCard3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding3
import com.example.chatgeminiapp.ui.theme.ChatGeminiAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileGridList(
    modifier: Modifier = Modifier,
    profiles: List<InternalProfile>,
    onConfigProfile: (Long) -> Unit = {},
    onSelectProfile: (Long) -> Unit = {},
    onCreateProfile: () -> Unit = {},
    onAddProfile: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(horizontal = mediumPadding2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Adaptive(smallCard3),
            horizontalArrangement = Arrangement.Center
        ) {
            item (span = StaggeredGridItemSpan.FullLine) {
                Column (
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(smallCard2))
                    Image(
                        modifier = Modifier.size(largePadding2),
                        painter = painterResource(id = R.drawable.google_gemini_icon),
                        contentDescription = "Brand Logo"
                    )
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = "Who's going to use Gemini AI ?"
                    )
                    Text(
                        style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center),
                        text = "With Chat Gemini Android, you can separate your workspace with different profiles !"
                    )
                    Spacer(modifier = Modifier.height(smallCard2))

                }
            }
            itemsIndexed(items = profiles) { index, profile ->
                Card(
                    elevation = CardDefaults.cardElevation(Dimens.smallPadding2),
//                    onClick = {onSelectProfile(profile.id)},
                    modifier = modifier
                        .size(mediumCard1)
                        .padding(vertical = Dimens.smallPadding2, horizontal = Dimens.smallPadding2)
                ) {
                    Column(
                        modifier = modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(Dimens.smallPadding2),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Spacer(modifier = Modifier.weight(.5f))
                            Text(
                                modifier = Modifier.weight(1f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                text = profile.name,
                                style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center)
                            )
                            Row(
                                modifier = Modifier.weight(.5f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    modifier = Modifier.size(largePadding1),
                                    onClick = { onConfigProfile(profile.id) }
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(0.1.dp),                                        imageVector = Icons.Rounded.MoreVert,
                                        contentDescription = "Profile Settings"
                                    )
                                }
                            }

                        }
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(percent = 5))
                                .clickable { onSelectProfile(profile.id) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (profile.avatar != null) {
                                Image(
                                    modifier = Modifier
                                        .padding(vertical = smallPadding1)
                                        .fillMaxWidth()
                                        .size(smallCard1),
                                    bitmap = profile.avatar.asImageBitmap(),
                                    contentDescription = "User Avatar"
                                )
                            } else {
                                Image(
                                    modifier = Modifier
                                        .padding(vertical = smallPadding1)
                                        .fillMaxWidth()
                                        .size(smallCard1),
                                    painter = painterResource(id = R.drawable.default_user_avatar),
                                    contentDescription = "User Avatar"
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                overflow = TextOverflow.Visible,
                                maxLines = 2,
                                style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
                                text = "Viewed\n${formatTimeStamp(profile.editedAt)}"
                            )
                        }
                    }

                }
            }
            item {
                Card(
                    elevation = CardDefaults.cardElevation(Dimens.smallPadding2),
                    onClick = onAddProfile,
                    modifier = modifier
                        .size(mediumCard1)
                        .padding(vertical = Dimens.smallPadding2, horizontal = Dimens.smallPadding2)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(Dimens.smallPadding2),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier =
                            Modifier.fillMaxWidth(),
                            text = "Add",
                            style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center)
                        )
                        Icon(
                            modifier = Modifier
                                .size(smallCard1)
                                .padding(vertical = smallPadding1)
                                .fillMaxWidth(),
                            imageVector = Icons.Rounded.AddCircle,
                            contentDescription = "Add new profile"
                        )
                    }
                }
            }

        }
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
private fun ProfileGridListPreview() {
    ChatGeminiAppTheme(dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileGridList(
                profiles = profilesMock
            )
        }
    }

}

val profilesMock = listOf(
    InternalProfile(
        id = 1,
        name = "Dwitama",
        avatar = null, // Or provide a Bitmap object for the avatar
        apiKey = GEMINI_API_KEY,
        createdAt = "2024-04-20T10:00:00Z",
        editedAt = 1650374400000 // Timestamp in milliseconds (epoch time)
    ),
    InternalProfile(
        id = 2,
        name = "Jane Smith",
        avatar = null, // Or provide a Bitmap object for the avatar
        apiKey = "api_key_abcdef",
        createdAt = "2024-04-23T15:30:00Z",
        editedAt = 1650710200000 // Timestamp in milliseconds (epoch time)
    ),
    InternalProfile(
        id = 3,
        name = "Peter Parker",
        avatar = null, // Or provide a Bitmap object for the avatar
        apiKey = "api_key_zxcvbn",
        createdAt = "2024-04-25 08:10:00",
        editedAt = System.currentTimeMillis() // Use current time for editedAt
    ),
    InternalProfile(
        name = "Alice Walker",
        avatar = null,// Or provide a Bitmap object for the avatar
        apiKey = "api_key_qwerty",
// No ID provided (defaults to 0)
        createdAt = "2024-04-24 12:00:00",
        editedAt = 1650643200000 // Timestamp in milliseconds (epoch time)
    )
)
