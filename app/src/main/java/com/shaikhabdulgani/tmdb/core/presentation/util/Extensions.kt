package com.shaikhabdulgani.tmdb.core.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun NavOptionsBuilder.clearBackStack(controller: NavController) {
    this.popUpTo(controller.graph.startDestinationId){
        inclusive = true
    }
}