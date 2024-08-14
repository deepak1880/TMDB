@file:Suppress("SameParameterValue")

package com.shaikhabdulgani.tmdb.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import com.shaikhabdulgani.tmdb.view.home.Movie
import com.shaikhabdulgani.tmdb.view.home.MovieRowWithTitle
import com.shaikhabdulgani.tmdb.view.home.getBitmapFromImage
import com.shaikhabdulgani.tmdb.view.home.getMovieDummyData
import com.shaikhabdulgani.tmdb.view.onboarding.GradientButton

class MovieDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme {
                MovieDetail(
                    onBackClick = this::finish
                )
            }
        }
    }
}

@Composable
fun MovieDetail(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val movieList: List<Movie> = remember {
        getMovieDummyData(context, R.drawable.bg_onboarding)
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Color(0xFF22222E))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TopSummary(
            movieTitle = "Clash of the titans",
            year = "2004",
            cover = getBitmapFromImage(context,R.drawable.bg_onboarding).asImageBitmap(),
            poster = getBitmapFromImage(context,R.drawable.bg_onboarding).asImageBitmap(),
            runtime = "120 mins",
            genre = "Adventure",
            rating = "8.9",
            onBackClick = onBackClick,
            onSaveClick = {}
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(R.string.description_placeholder),
            color = Color(0xFFCCCCCC)
        )
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = stringResource(R.string.watch_trailer)
            )
            Spacer(Modifier.width(10.dp))
            Text(text = stringResource(id = R.string.watch_trailer))
        }

        SectionWithTitle(
            modifier = Modifier.padding(start = 20.dp),
            title = "Cast and crew"
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(getCastDummyData()) {
                    CastItem(name = it.name, image = it.image)
                }
            }
        }

        SectionWithTitle(
            modifier = Modifier.padding(start = 20.dp),
            title = stringResource(R.string.category_s)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(getDummyCategory()) {
                    CategoryChip(it)
                }
            }
        }

        MovieRowWithTitle(
            modifier = Modifier.padding(start = 20.dp),
            context = context,
            title = stringResource(R.string.recommendations),
            movies = movieList
        )
    }
}

private fun getDummyCategory() = listOf("Drama", "Adventure", "Comedy")

@Composable
fun CategoryChip(text: String) {
    Text(
        text = text,
        fontSize = 15.sp,
        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color = Color(0xFF303243))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    name: String,
    image: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .width(80.dp)
            .then(modifier),
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    color = Color(0xFFCCCCCC),
                    width = 2.dp,
                    shape = CircleShape
                ),
            painter = painterResource(id = image),
            contentDescription = name
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.White,
            lineHeight = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 5.dp),
        )
    }
}

private fun getCastDummyData(): List<Cast> {
    return listOf(
        Cast("Sandra Bullockasdasdasdada", R.drawable.ic_launcher_background),
        Cast("Sandra Bullockasdadadadadad", R.drawable.ic_launcher_background),
        Cast("Sandra Bullock", R.drawable.ic_launcher_background),
        Cast("Sandra Bullock", R.drawable.ic_launcher_background),
        Cast("Sandra Bullock", R.drawable.ic_launcher_background),
        Cast("Sandra Bullock", R.drawable.ic_launcher_background),
        Cast("Sandra Bullock", R.drawable.ic_launcher_background),
    )
}

data class Cast(
    val name: String,
    val image: Int,
)

@Composable
fun SectionWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        content()
    }
}

@Composable
private fun TopSummary(
    movieTitle: String,
    rating: String,
    year: String,
    runtime: String,
    genre: String,
    poster: ImageBitmap,
    cover: ImageBitmap,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF22222E))
    ) {
        val (
            iconBack,
            textBack,
            iconFavorite,
            imgCover,
            space,
            imgPoster,
            title,
            ratingRow,
            blurEffect,
            movieInfo
        ) = createRefs()
        val startGuideline = createGuidelineFromStart(18.dp)
        val endGuideline = createGuidelineFromEnd(18.dp)
        val topGuideline = createGuidelineFromTop(36.dp)
        val barrier = createBottomBarrier(title, imgPoster)
        //cover img
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(imgCover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            bitmap = cover,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Icon(
            modifier = Modifier
                .constrainAs(iconBack) {
                    top.linkTo(iconFavorite.top)
                    bottom.linkTo(iconFavorite.bottom)
                    start.linkTo(startGuideline)
                }
                .clickable { onBackClick() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(R.string.back),
            tint = Color.White
        )
        Text(
            modifier = Modifier
                .constrainAs(textBack) {
                    top.linkTo(iconFavorite.top)
                    bottom.linkTo(iconFavorite.bottom)
                    start.linkTo(iconBack.end, 10.dp)
                }
                .clickable { onBackClick() },
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            text = stringResource(R.string.back),
        )
        Icon(
            modifier = Modifier
                .constrainAs(iconFavorite) {
                    top.linkTo(topGuideline)
                    end.linkTo(endGuideline)
                }
                .clip(CircleShape)
                .background(color = Color(0x88303243))
                .padding(7.dp)
                .clickable { onSaveClick() },
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = stringResource(R.string.save),
            tint = Color.White
        )
        //gradient effect
        Spacer(
            modifier = Modifier
                .constrainAs(space) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(imgCover.bottom)
                }
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0x0022222E),
                            Color(0xFF22222E)
                        )
                    )
                )
        )

        //poster image
        Image(
            modifier = Modifier
                .constrainAs(imgPoster) {
                    top.linkTo(imgCover.bottom)
                    bottom.linkTo(imgCover.bottom)
                    start.linkTo(startGuideline)
                }
                .width(95.dp)
                .aspectRatio(0.8f)
                .clip(RoundedCornerShape(20.dp)),
            bitmap = poster,
            contentDescription = movieTitle,
            contentScale = ContentScale.Crop
        )
        Text(
            text = movieTitle,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(imgPoster.end, 10.dp)
                end.linkTo(endGuideline)
                top.linkTo(imgPoster.top)
                bottom.linkTo(imgPoster.bottom)
                width = Dimension.fillToConstraints
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        //blur effect
        Spacer(modifier = Modifier
            .constrainAs(blurEffect) {
                top.linkTo(ratingRow.top)
                bottom.linkTo(ratingRow.bottom)
                end.linkTo(ratingRow.end)
                start.linkTo(ratingRow.start)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .clip(RoundedCornerShape(30))
            .background(Color.White.copy(0.9f))
            .blur(40.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(ratingRow) {
                    end.linkTo(endGuideline)
                    bottom.linkTo(space.top)
                }
                .clip(RoundedCornerShape(30))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 5.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = movieTitle,
                tint = Color(0xFFFF8700),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = rating,
                color = Color(0xFFFF8700),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .constrainAs(movieInfo) {
                    top.linkTo(barrier)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 20.dp)
        ) {
            TextWithStartIcon(imageVector = Icons.Outlined.DateRange, text = year)
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(
                        color = Color(0xFF92929D)
                    )
            )
            TextWithStartIcon(imageVector = Icons.Outlined.Done, text = runtime)
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(
                        color = Color(0xFF92929D)
                    )
            )
            TextWithStartIcon(imageVector = Icons.Default.Info, text = genre)
        }
    }
}

@Composable
fun TextWithStartIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    color: Color = Color(0xFF92929D),
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
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
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    TMDBTheme {
        MovieDetail({})
    }
}