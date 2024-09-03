package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.core.presentation.util.shimmer
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.home.domain.model.Movie
import com.shaikhabdulgani.tmdb.moviedetail.presentation.SectionWithTitle
import com.shaikhabdulgani.tmdb.ui.theme.Gray
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun MovieRowWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>,
    isLoading: Boolean = true,
    onLastReached: () -> Unit = {},
    onClick: (Movie) -> Unit
) {
    SectionWithTitle(
        modifier = modifier,
        title = title,
        containsRow = true
    ) {
        if (movies.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(items = movies, key = { i, movie -> "${movie.id}$i" }) { i, item ->
                    if (i == movies.size - 1) {
                        onLastReached()
                    }
                    MovieItem(
                        modifier = when {
                            i == movies.size - 1 && !isLoading -> Modifier.padding(end = 20.dp)
                            i == 0 -> Modifier.padding(start = 20.dp)
                            else -> Modifier
                        },
                        position = i,
                        item = item,
                        onClick = {
                            onClick(it)
                        }
                    )
                }
                val loadingItemCount = 3
                if (isLoading) {
                    items(loadingItemCount) {
                        LoadingItem(
                            modifier = when (it) {
                                loadingItemCount - 1 -> Modifier.padding(end = 20.dp)
                                0 -> Modifier.padding(start = 20.dp)
                                else -> Modifier
                            },
                        )
                    }
                }
            }
        } else {
            NoDataAvailable(
                modifier.padding(horizontal = MaterialTheme.spacing.default)
            )
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    position: Int,
    item: Movie,
    onClick: (Movie) -> Unit,
) {
    val context = LocalContext.current
    val image = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data("${Constants.IMAGE_BASE_URL}${item.imageId}")
            .size(Size.ORIGINAL)
            .build()
    )
    Box(
        modifier
            .size(height = 180.dp, width = 120.dp)
            .clip(Shapes.roundedCornerSmall)
            .background(Gray)
            .clickable {
                onClick.invoke(item)
            },
        contentAlignment = Alignment.Center
    ) {
        if (item.imageId.isBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = item.title,
                textAlign = TextAlign.Center
            )
        } else {
            when (image.state) {
                AsyncImagePainter.State.Empty -> {

                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                    )
                }

                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmer()
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = image,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingItem(
    modifier: Modifier = Modifier
) {

    Box(
        modifier
            .size(height = 180.dp, width = 120.dp)
            .clip(Shapes.roundedCornerSmall)
            .background(Gray)
            .shimmer(),
    )
}

@Composable
private fun NoDataAvailable(
    modifier: Modifier = Modifier
) {

    Column(
        modifier
            .height(180.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = MaterialTheme.spacing.default,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.fillMaxHeight(0.7f),
            painter = painterResource(id = R.drawable.img_no_data),
            contentDescription = ""
        )
        Text(
            text = "No data available",
            color = Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun EmptyListPrev() {
    NoDataAvailable()
}

@Preview
@Composable
private fun MovieItemPrev() {
    val context = LocalContext.current
    MovieItem(position = 0, item = Movie(0, "Hello", "")) {

    }
}