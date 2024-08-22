package com.shaikhabdulgani.tmdb.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shaikhabdulgani.tmdb.ui.theme.Pink
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White
import com.shaikhabdulgani.tmdb.ui.theme.White50

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    selectedItem: Int,
    onSelect: (Int) -> Unit,
    gap: Dp = 20.dp,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        itemsIndexed(tabs) { i, str ->
            TabItem(
                modifier = when (i) {
                    tabs.size - 1 -> Modifier.padding(end = 20.dp)
                    0 -> Modifier.padding(start = 20.dp)
                    else -> Modifier
                },
                position = i,
                isActive = i == selectedItem,
                text = str,
                onClick = onSelect
            )
        }
    }
}

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    position: Int,
    isActive: Boolean,
    text: String,
    activeTextColor: Color = White,
    inactiveTextColor: Color = White50,
    activeBackgroundColor: Color = Pink,
    inActiveBackgroundColor: Color = Transparent,
    onClick: (Int) -> Unit
) {
    val selectedModifier = if (isActive) {
        Modifier
            .clip(Shapes.fullRoundedCorner)
            .background(color = activeBackgroundColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    } else {
        Modifier
            .background(color = inActiveBackgroundColor)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable { onClick(position) }
    }
    val selectedColor = if (isActive) {
        activeTextColor
    } else {
        inactiveTextColor
    }

    Text(
        text = text,
        color = selectedColor,
        modifier = modifier.then(selectedModifier)
    )
}