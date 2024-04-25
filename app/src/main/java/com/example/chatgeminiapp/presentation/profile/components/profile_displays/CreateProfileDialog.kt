package com.example.chatgeminiapp.presentation.profile.components.profile_displays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.example.chatgeminiapp.presentation._common.resources.Dimens
import com.example.chatgeminiapp.presentation._common.resources.Dimens.largeCard1
import com.example.chatgeminiapp.presentation._common.resources.Dimens.mediumCard1
import com.example.chatgeminiapp.presentation.profile.components.profile_actions.CreateProfileStepperForm


@Composable
fun CreateProfileDialog (
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { /*TODO*/ }) {
        Card (
            modifier = Modifier
                .defaultMinSize(minHeight = Dimens.mediumCard2, minWidth = largeCard1),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateProfileStepperForm()
            }
        }
    }
}