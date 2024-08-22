package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shaikhabdulgani.tmdb.core.presentation.util.noRippleClickable
import com.shaikhabdulgani.tmdb.ui.theme.Black
import com.shaikhabdulgani.tmdb.ui.theme.Black50
import com.shaikhabdulgani.tmdb.ui.theme.GradientStart
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.White
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun DialogContent(
    message: String = "",
    progressColor: Color = GradientStart,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { }
            .background(Black50),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .shadow(
                    elevation = MaterialTheme.spacing.small,
                    ambientColor = White,
                    spotColor = White,
                    shape = Shapes.roundedCorner30,
                )
                .clip(Shapes.roundedCorner30)
                .background(White)
                .padding(MaterialTheme.spacing.default)
        ) {
            CircularProgressIndicator(
                color = progressColor
            )
            if (message.isNotBlank()){
                Text(
                    text = message,
                    color = Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun DialogContentPreview() {
    DialogContent(
        message = "Please wait..."
    )
}