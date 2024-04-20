package com.example.chatgeminiapp.presentation.gemini_chat._common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.resources.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar (
    onDrawerToggle: () -> Unit = {},
    onProfileToggle: () -> Unit = {},
    onNewChatGroup: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())// Adjust avatar size as needed
    // Clip for rounded avatar
// Replace with your avatar image
    /* do something */
    /* do something */
    TopAppBar(
        title = {
            Text(
                "Gemini",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onDrawerToggle) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Localized description"
                )
            }

            IconButton(onClick = { /* do something */ }) {
                Image(
                    painter = painterResource(id = R.drawable.default_user_avatar), // Replace with your avatar image
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(Dimens.mediumIconButton3) // Adjust avatar size as needed
                        .clip(CircleShape) // Clip for rounded avatar
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        scrollBehavior = scrollBehavior
    )


//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.primary)
//            .height(largePadding2)
//            .padding(horizontal = mediumPadding2)
//    ) {
//        Text(
//            text = "Gemini", // Replace with username
//            color = MaterialTheme.colorScheme.onBackground,
//            fontSize = 16.sp,
//            modifier = Modifier.padding(start = mediumPadding2) // Padding for text
//        )
//        // Spacer(modifier = Modifier.weight(1f)) // Spacer for horizontal centering of icons
//        IconButton(onClick = { /* Handle back button click */ }) {
//            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Back Button", tint = Color.White)
//        }
//        IconButton(onClick = { /* Handle menu button click */ }) {
//            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu Icon", tint = Color.White)
//        }
//    }
}