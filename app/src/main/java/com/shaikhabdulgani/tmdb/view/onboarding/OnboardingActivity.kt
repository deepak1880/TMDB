package com.shaikhabdulgani.tmdb.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import com.shaikhabdulgani.tmdb.view.home.HomeActivity

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
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
                    MainContent(
                        appName = resources.getString(R.string.app_name),
                        title = "",
                        subtitle = stringResource(R.string.onboard_subtitle),
                        buttonText = stringResource(R.string.explore),
                        onClick = {
                            Intent(this, HomeActivity::class.java).also {
                                startActivity(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    title: String,
    backgroundImage: Int = R.drawable.bg_onboarding,
    appName: String = "",
    subtitle: String = "",
    buttonText: String = "",
    onClick: () -> Unit = {}
) {
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = backgroundImage),
            contentDescription = appName,
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00000000),
                            Color(0x88000000)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Text(
                text = appName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 10.sp
            )
            Text(
                modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 0.dp),
                text = title,
                fontSize = 32.sp,
                lineHeight = 37.sp
            )
            Text(
                modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 18.dp),
                text = subtitle,
                fontSize = 15.sp,
                lineHeight = 18.sp
            )
            GradientButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                ,
                onClick = onClick
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RectangleShape,
        modifier = Modifier
            .then(modifier)
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF8000FF),
                        Color(0xFF540BA1)
                    )
                ),
            ),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    TMDBTheme {
        val title = buildAnnotatedString {
            append("Everything about ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("movies, series, anime and much more.")
            }
        }
        MainContent(
            appName = "TMDB",
            title = "",
            subtitle = "Inside you will find information on films, series, anime and much more.",
            buttonText = "Explore"
        )
    }
}