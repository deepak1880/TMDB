package com.shaikhabdulgani.tmdb.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.global.Screen
import com.shaikhabdulgani.tmdb.core.presentation.GradientButton
import com.shaikhabdulgani.tmdb.ui.theme.Black50
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun OnboardingScreen(controller: NavHostController) {
    val title = buildAnnotatedString {
        append(stringResource(R.string.onboard_normal_title))
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.ExtraBold
            )
        ) {
            append(stringResource(R.string.onboard_bold_title))
        }
    }
    val appName = stringResource(R.string.app_name)
    val subtitle = stringResource(R.string.onboard_subtitle)
    val buttonText = stringResource(R.string.explore)
    val backgroundImage: Int = R.drawable.bg_onboarding
    val onClick = { controller.navigate(Screen.Home) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = backgroundImage),
            contentDescription = appName,
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium, Alignment.Bottom),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Transparent,
                            Black50
                        )
                    )
                )
                .padding(MaterialTheme.spacing.default)
        ) {
            Text(
                text = appName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing = 10.sp
            )
            Text(
                text = title,
                fontSize = 32.sp,
                lineHeight = 37.sp
            )
            Text(
                text = subtitle,
                fontSize = 15.sp,
                lineHeight = 18.sp
            )
            GradientButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onClick
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    TMDBTheme {
        OnboardingScreen(NavHostController(LocalContext.current))
    }
}