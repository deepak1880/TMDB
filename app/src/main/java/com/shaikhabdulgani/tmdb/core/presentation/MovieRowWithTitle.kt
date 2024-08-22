package com.shaikhabdulgani.tmdb.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.home.domain.model.Movie
import com.shaikhabdulgani.tmdb.moviedetail.presentation.SectionWithTitle
import com.shaikhabdulgani.tmdb.ui.theme.Gray
import com.shaikhabdulgani.tmdb.ui.theme.Shapes

@Composable
fun MovieRowWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>,
    onLastReached: () -> Unit = {},
    onClick: (Int) -> Unit
) {
    SectionWithTitle(
        modifier = modifier,
        title = title,
        containsRow = true
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = movies, key = { i, movie -> "${movie.id}$i" }) { i, item ->
                if (i == movies.size - 1) {
                    onLastReached()
                }
                MovieItem(
                    modifier = when (i) {
                        movies.size - 1 -> Modifier.padding(end = 20.dp)
                        0 -> Modifier.padding(start = 20.dp)
                        else -> Modifier
                    },
                    position = i,
                    item = item,
                    onClick = {
                        onClick(movies[it].id)
                    }
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    position: Int,
    item: Movie,
    onClick: (Int) -> Unit,
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
                onClick.invoke(position)
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
            when(image.state){
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
                    CircularProgressIndicator()
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

@Preview
@Composable
private fun MovieItemPrev() {
    val context = LocalContext.current
    MovieItem(position = 0, item = Movie(0, "Hello", "")) {

    }
}