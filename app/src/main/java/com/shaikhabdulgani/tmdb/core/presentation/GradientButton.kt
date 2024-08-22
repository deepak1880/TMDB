package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shaikhabdulgani.tmdb.ui.theme.GradientEnd
import com.shaikhabdulgani.tmdb.ui.theme.GradientStart
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.Transparent

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Transparent
        ),
        shape = RectangleShape,
        modifier = Modifier
            .then(modifier)
            .clip(Shapes.fullRoundedCorner)
            .height(35.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        GradientStart,
                        GradientEnd
                    )
                ),
            ),
        content = content
    )
}

@Preview
@Composable
private fun ButtonPrev() {
    GradientButton(onClick = {  }) {
        Text(text = "Hello world")
    }
}