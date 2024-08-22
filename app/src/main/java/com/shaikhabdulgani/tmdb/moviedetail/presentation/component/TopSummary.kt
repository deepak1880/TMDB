package com.shaikhabdulgani.tmdb.moviedetail.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.core.presentation.TextWithStartIcon
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.ui.theme.Black50
import com.shaikhabdulgani.tmdb.ui.theme.DarkBg
import com.shaikhabdulgani.tmdb.ui.theme.Gray
import com.shaikhabdulgani.tmdb.ui.theme.LightGray
import com.shaikhabdulgani.tmdb.ui.theme.Orange
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White

@Composable
fun TopSummary(
    movieTitle: String,
    rating: String,
    year: String,
    runtime: String,
    genre: String,
    description: String,
    backdropPath: String,
    posterPath: String,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    val fallBackImage = rememberVectorPainter(Icons.Outlined.ImageNotSupported)
    val errorImage = rememberVectorPainter(Icons.Outlined.ErrorOutline)
    Log.i("TopSummary","PosterPath: $posterPath and BackdropPath: $backdropPath")
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .size(Size.ORIGINAL)
            .data("${Constants.IMAGE_BASE_URL}$posterPath")
            .build()
    )
    val backdropImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .size(Size.ORIGINAL)
            .data("${Constants.IMAGE_BASE_URL}$backdropPath")
            .build()
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
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
            movieInfo,
            descriptionC
        ) = createRefs()
        val startGuideline = createGuidelineFromStart(18.dp)
        val endGuideline = createGuidelineFromEnd(18.dp)
        val topGuideline = createGuidelineFromTop(36.dp)
        val barrier = createBottomBarrier(title, imgPoster)
        //backdrop img
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(imgCover) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            when(backdropImage.state){
                AsyncImagePainter.State.Empty -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = fallBackImage,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                }
                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = errorImage,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                }
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = backdropImage,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Icon(
            modifier = Modifier
                .constrainAs(iconBack) {
                    top.linkTo(iconFavorite.top)
                    bottom.linkTo(iconFavorite.bottom)
                    start.linkTo(startGuideline)
                }
                .clickable { onBackClick() },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = White
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
                .background(color = Black50)
                .padding(7.dp)
                .clickable { onSaveClick() },
            imageVector = Icons.Filled.BookmarkBorder,
            contentDescription = stringResource(R.string.save),
            tint = White
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
                            Transparent,
                            DarkBg
                        )
                    )
                )
        )

        //poster image
        Box(
            modifier = Modifier
                .constrainAs(imgPoster) {
                    top.linkTo(imgCover.bottom)
                    bottom.linkTo(imgCover.bottom)
                    start.linkTo(startGuideline)
                }
                .width(95.dp)
                .aspectRatio(0.8f)
                .clip(Shapes.roundedCornerSmall),
            contentAlignment = Alignment.Center
        ) {
            when(posterImage.state){
                AsyncImagePainter.State.Empty -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = fallBackImage,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                }
                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = errorImage,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                }
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = posterImage,
                        contentDescription = movieTitle,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
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
            .clip(Shapes.roundedCorner30)
            .background(White.copy(0.9f))
            .blur(40.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .constrainAs(ratingRow) {
                    end.linkTo(endGuideline)
                    bottom.linkTo(space.top)
                }
                .clip(Shapes.roundedCorner30)
                .background(Black50)
                .padding(horizontal = 5.dp, vertical = 2.dp),
        ) {
            TextWithStartIcon(
                imageVector = Icons.Filled.StarOutline,
                color = Orange,
                text = rating,
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
            TextWithStartIcon(
                imageVector = Icons.Outlined.DateRange,
                text = year
            )
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(
                        color = Gray
                    )
            )
            TextWithStartIcon(imageVector = Icons.Filled.Timelapse, text = runtime)
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(
                        color = Gray
                    )
            )
            TextWithStartIcon(imageVector = Icons.Filled.Movie, text = genre)
        }

        Text(
            modifier = Modifier
                .constrainAs(descriptionC) {
                    top.linkTo(movieInfo.bottom)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 20.dp),
            text = description,
            color = LightGray
        )
    }
}