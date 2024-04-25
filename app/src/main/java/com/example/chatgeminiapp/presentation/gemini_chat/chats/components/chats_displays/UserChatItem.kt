package com.example.chatgeminiapp.presentation.gemini_chat.chats.components.chats_displays

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatgeminiapp.R
import com.example.chatgeminiapp.presentation._common.components.DEFAULT_MINIMUM_TEXT_LINE
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumIconButton3
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumPadding2
import com.example.chatgeminiapp.presentation._common.resources.Dimens.smallPadding2
import java.util.regex.Pattern

@Composable
fun UserChatItem(prompt: String, bitmapSourceUri: String?) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(vertical = smallPadding2, horizontal = mediumPadding2)
    ) {

        Spacer(modifier = Modifier.height(mediumPadding2)) // Spacing between avatar and message bubble
        Image(
            painter = painterResource(id = R.drawable.default_user_avatar), // Replace with your avatar image
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(mediumIconButton3) // Adjust avatar size as needed
                .clip(CircleShape) // Clip for rounded avatar
        )

        Spacer(modifier = Modifier.height(mediumPadding1)) // Spacing between avatar and message bubble


        Column(modifier = Modifier) {
            SelectionContainer {
                ExpandableText(
                    modifier = Modifier
                        .padding(bottom = smallPadding2)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    text = prompt,
                    style = MaterialTheme.typography.bodyLarge

                )
            }


            bitmapSourceUri?.let {
                Text(
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = .3f
                        )
                    ),
                    text = "Note: Prompt image might be changed "
                )
                AsyncImage(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(mediumCard1)
                        .padding(bottom = 2.dp)
                        .clip(RoundedCornerShape(mediumPadding1)),
                    model = ImageRequest.Builder(context)
                        .data(bitmapSourceUri)
                        .build(),
                    contentDescription = "Image to asking",
                    error = painterResource(R.drawable.default_image_error_1),
                    contentScale = ContentScale.Crop,
                )
            }


        }
    }
}


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    clickableStyle: SpanStyle = style.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary
    ).toSpanStyle()
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    val textLayoutResult = textLayoutResultState.value

    //first we match the html tags and enable the links
    val textWithLinks = buildAnnotatedString {
        val htmlTagPattern = Pattern.compile(
            "(?i)<a([^>]+)>(.+?)</a>",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
        val matcher = htmlTagPattern.matcher(text)
        var matchStart: Int
        var matchEnd = 0
        var previousMatchStart = 0

        while (matcher.find()) {
            matchStart = matcher.start(1)
            matchEnd = matcher.end()
            val beforeMatch = text.substring(
                startIndex = previousMatchStart,
                endIndex = matchStart - 2
            )
            val tagMatch = text.substring(
                startIndex = text.indexOf(
                    char = '>',
                    startIndex = matchStart
                ) + 1,
                endIndex = text.indexOf(
                    char = '<',
                    startIndex = matchStart + 1
                ),
            )
            append(
                beforeMatch
            )
            // attach a string annotation that stores a URL to the text
            val annotation = text.substring(
                startIndex = matchStart + 7,//omit <a hreh =
                endIndex = text.indexOf(
                    char = '"',
                    startIndex = matchStart + 7,
                )
            )
            pushStringAnnotation(tag = "link_tag", annotation = annotation)
            withStyle(
                SpanStyle(
                    color = clickableStyle.color,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(
                    tagMatch
                )
            }
            pop() //don't forget to add this line after a pushStringAnnotation
            previousMatchStart = matchEnd
        }
        //append the rest of the string
        if (text.length > matchEnd) {
            append(
                text.substring(
                    startIndex = matchEnd,
                    endIndex = text.length
                )
            )
        }
    }
    //then we create the Show more/less animation effect
    var textWithMoreLess by remember { mutableStateOf(textWithLinks) }
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                textWithMoreLess = buildAnnotatedString {
                    append(textWithLinks)
                    pushStringAnnotation(tag = "show_more_tag", annotation = "")
                    append("\n\n[")
                    withStyle(
                        clickableStyle
                    ) {
                        append("See less")
                    }
                    append("]")
                    pop()
                }
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {//Returns true if either vertical overflow or horizontal overflow happens.
                val lastCharIndex = textLayoutResult.getLineEnd(DEFAULT_MINIMUM_TEXT_LINE - 1)
                val showMoreString = " ...See more"
                val adjustedText = textWithLinks
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                textWithMoreLess = buildAnnotatedString {
                    append(adjustedText)
//                    append(" ...")
                    pushStringAnnotation(tag = "show_more_tag", annotation = "")
                    withStyle(clickableStyle) {
                        append(showMoreString)
                    }
                    pop()
                }

                isClickable = true
                //We basically need to assign this here so that the Text is only clickable if the state is not expanded,
                // but there is visual overflow. Otherwise, it means that the text given to the composable is not exceeding the max lines.
            }
        }
    }

    // UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

    //Composable container
    SelectionContainer {
        ClickableText(
            text = textWithMoreLess,
            style = style.copy(color = MaterialTheme.colorScheme.onBackground),
            onClick = { offset ->
                textWithMoreLess.getStringAnnotations(
                    tag = "link_tag",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
                if (isClickable) {
                    textWithMoreLess.getStringAnnotations(
                        tag = "show_more_tag",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        isExpanded = !isExpanded
                    }
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else DEFAULT_MINIMUM_TEXT_LINE,
            onTextLayout = { textLayoutResultState.value = it },
            modifier = modifier
                .animateContentSize()
        )
    }
}

