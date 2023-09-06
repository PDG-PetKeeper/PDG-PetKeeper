package com.projet.petkeeper.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun LookupJob(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    BackHandler {
        onBackClick()
    }


}