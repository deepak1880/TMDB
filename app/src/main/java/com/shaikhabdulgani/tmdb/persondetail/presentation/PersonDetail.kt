package com.shaikhabdulgani.tmdb.persondetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.core.presentation.MediaRow
import com.shaikhabdulgani.tmdb.core.presentation.TextWithStartIcon
import com.shaikhabdulgani.tmdb.global.Screen
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType
import com.shaikhabdulgani.tmdb.ui.theme.DarkBg
import com.shaikhabdulgani.tmdb.ui.theme.Gray
import com.shaikhabdulgani.tmdb.ui.theme.LightGray
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun PersonDetail(
    controller: NavController
) {
    MaterialTheme{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.bg_onboarding),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Transparent,
                                    DarkBg,
                                )
                            )
                        )
                        .align(Alignment.BottomCenter)
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = MaterialTheme.spacing.default),
                text = "Mark Richard Hamill",
                color = Gray,
                fontSize = 22.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                TextWithStartIcon(
                    imageVector = Icons.Outlined.Cake,
                    text = "1951-09-25"
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .background(
                            color = Gray
                        )
                )
                TextWithStartIcon(imageVector = Icons.Filled.Movie, text = "Acting")
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .background(
                            color = Gray
                        )
                )
                TextWithStartIcon(imageVector = Icons.Filled.Accessibility, text = "Male")
            }

            Text(
                modifier = Modifier
                    .padding(top = 20.dp),
                text = "description",
                color = LightGray
            )

            MediaRow(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "Appearance in movies",
                movies = listOf()
            ) {
                controller.navigate(Screen.MovieDetail(
                    id = it.id,
                    mediaType = MediaType.MOVIE.getValue()
                ))
            }
            MediaRow(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "Appeared in series",
                movies = listOf()
            ) {
                controller.navigate(Screen.MovieDetail(
                    id = it.id,
                    mediaType = MediaType.SERIES.getValue()
                ))
            }
        }
    }
}

@Preview
@Composable
private fun PersonDetailPreview() {
    PersonDetail(
        controller = NavController(LocalContext.current)
    )
}