package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shaikhabdulgani.tmdb.ui.theme.Gray

@Composable
fun TextWithStartIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    color: Color = Gray,
    drawablePadding: Dp = 5.dp,
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(drawablePadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = text,
            tint = color
        )
        Text(
            text = text,
            color = color,
//            style = TextStyle.Default
        )
    }
}