@file:Suppress("SameParameterValue")

package com.shaikhabdulgani.tmdb.moviedetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.core.presentation.DialogContent
import com.shaikhabdulgani.tmdb.global.Screen
import com.shaikhabdulgani.tmdb.core.presentation.GradientButton
import com.shaikhabdulgani.tmdb.ui.theme.ChipColor
import com.shaikhabdulgani.tmdb.ui.theme.DarkBg
import com.shaikhabdulgani.tmdb.ui.theme.LightGray
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.White
import com.shaikhabdulgani.tmdb.core.presentation.MovieRowWithTitle
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.Cast
import com.shaikhabdulgani.tmdb.moviedetail.domain.model.Genre
import com.shaikhabdulgani.tmdb.moviedetail.presentation.component.TopSummary
import com.shaikhabdulgani.tmdb.moviedetail.presentation.util.parseReleaserYear
import com.shaikhabdulgani.tmdb.moviedetail.presentation.util.toSingleDecimalPlaces
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType
import com.shaikhabdulgani.tmdb.ui.theme.ErrorColor
import com.shaikhabdulgani.tmdb.ui.theme.GradientStart
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun MovieDetailScreen(
    id: Int,
    mediaType: String = MediaType.MOVIE.getValue(),
    controller: NavHostController,
    viewModel: MovieDetailViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(context) {
        viewModel.getMovieDetails(id,MediaType.parse(mediaType))
    }
    val movieDetail = viewModel.movieDetail
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = DarkBg)
            .verticalScroll(rememberScrollState())
            .padding(bottom = MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        TopSummary(
            movieTitle = movieDetail.title,
            description = movieDetail.description,
            year = parseReleaserYear(movieDetail.releaseYear),
            posterPath = movieDetail.posterPath,
            backdropPath = movieDetail.backdropPath,
            runtime = stringResource(id = R.string.runtime_args, movieDetail.runtime),
            genre = if (movieDetail.genres.isNotEmpty()) movieDetail.genres[0].name else "",
            rating = movieDetail.rating.toSingleDecimalPlaces(),
            onBackClick = { controller.navigateUp() },
            onSaveClick = { println(id) }
        )

        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.default),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.PlayCircleOutline,
                contentDescription = stringResource(R.string.watch_trailer),
                tint = White
            )
            Spacer(Modifier.width(MaterialTheme.spacing.defaultSmall))
            Text(text = stringResource(id = R.string.watch_trailer))
        }

        SectionWithTitle(
            modifier = Modifier.padding(start = MaterialTheme.spacing.default),
            title = "Cast and crew"
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.defaultSmall)
            ) {
                items(movieDetail.cast) {
                    CastItem(cast = it)
                }
            }
        }

        SectionWithTitle(
            modifier = Modifier.padding(start = MaterialTheme.spacing.default),
            title = stringResource(R.string.category_s)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.defaultSmall)
            ) {
                items(movieDetail.genres) {
                    CategoryChip(it)
                }
            }
        }

        MovieRowWithTitle(
            title = stringResource(R.string.recommendations),
            movies = movieDetail.recommendations,
            onClick = { controller.navigate(Screen.MovieDetail(it)) }
        )
    }
    if (viewModel.isLoading) {
        DialogContent(message = stringResource(id = R.string.please_wait))
    }
}

@Composable
fun CategoryChip(genre: Genre) {
    Text(
        text = genre.name,
        fontSize = 15.sp,
        color = White,
        modifier = Modifier
            .clip(Shapes.fullRoundedCorner)
            .background(color = ChipColor)
            .padding(
                horizontal = MaterialTheme.spacing.defaultSmall,
                vertical = MaterialTheme.spacing.small
            )
    )
}

@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    cast: Cast
) {
    val context = LocalContext.current
    val castImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("${Constants.IMAGE_BASE_URL}${cast.imageId}")
            .size(Size.ORIGINAL)
            .build(),
        contentScale = ContentScale.Crop,
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .width(MaterialTheme.spacing.extraLarge)
            .then(modifier),
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(MaterialTheme.spacing.extraLarge)
                .border(
                    color = LightGray,
                    width = MaterialTheme.spacing.xxSmall,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            when (castImage.state) {
                AsyncImagePainter.State.Empty -> {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                color = LightGray,
                                width = MaterialTheme.spacing.xxSmall,
                                shape = CircleShape
                            ),
                        imageVector = Icons.Outlined.ImageNotSupported,
                        contentDescription = cast.id.toString()
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                color = LightGray,
                                width = MaterialTheme.spacing.xxSmall,
                                shape = CircleShape
                            ),
                        imageVector = Icons.Outlined.ErrorOutline,
                        colorFilter = ColorFilter.tint(ErrorColor),
                        contentDescription = cast.id.toString()
                    )
                }

                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(color = GradientStart)
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = modifier.fillMaxSize(),
                        painter = castImage,
                        contentDescription = cast.id.toString(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Text(
            text = cast.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = White,
            lineHeight = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
        )
    }
}

@Composable
fun SectionWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    containsRow: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
        modifier = modifier
    ) {
        Text(
            modifier = if (containsRow) Modifier.padding(start = MaterialTheme.spacing.default) else Modifier,
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
//    TMDBTheme {
//        MovieDetailScreen(
//            0,
//            NavHostController(LocalContext.current)
//            Mo,
//        )
//    }
}