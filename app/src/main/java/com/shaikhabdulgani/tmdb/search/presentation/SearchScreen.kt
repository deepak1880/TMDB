package com.shaikhabdulgani.tmdb.search.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeviceUnknown
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.core.data.util.Result
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.global.Screen
import com.shaikhabdulgani.tmdb.home.presentation.components.SearchBar
import com.shaikhabdulgani.tmdb.home.presentation.components.TabLayout
import com.shaikhabdulgani.tmdb.search.domain.model.MediaType
import com.shaikhabdulgani.tmdb.search.domain.model.SearchResult
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import com.shaikhabdulgani.tmdb.search.presentation.util.SearchFilter
import com.shaikhabdulgani.tmdb.ui.theme.Black
import com.shaikhabdulgani.tmdb.ui.theme.Black50
import com.shaikhabdulgani.tmdb.ui.theme.DarkBg
import com.shaikhabdulgani.tmdb.ui.theme.GradientStart
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White50
import com.shaikhabdulgani.tmdb.ui.theme.spacing

@Composable
fun SearchScreen(
    controller: NavController,
    viewModel: SearchViewModel
) {
    val query = viewModel.query.collectAsState()
    val result = viewModel.result.collectAsState()
    val context: Context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBg)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, Transparent, Transparent)
                )
            )
            .padding(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query.value.query
        ) {
            viewModel.onEvent(SearchEvent.QueryChange(it))
        }

        if (query.value.query.isNotBlank()){
            Text(
                text = "Showing Results for term \"${query.value.query}\"",
                color = White50
            )
        }

        TabLayout(
            modifier = Modifier.fillMaxWidth(),
            tabs = stringArrayResource(R.array.filters).toList(),
            selectedItem = query.value.filter.ordinal,
            onSelect = {
                viewModel.onEvent(SearchEvent.FilerChange(SearchFilter.entries[it]))
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            itemsIndexed(items = result.value, key = { _, item -> item.id }) { i, item ->
                if (i==result.value.size-1){
                    viewModel.onEvent(SearchEvent.ReachedEnd)
                }
                SearchItem(
                    id = item.id,
                    title = item.title,
                    imageId = item.imageId,
                    type = item.type,
                    parentContext = context
                ){
                    controller.navigate(Screen.MovieDetail(it,item.type.getValue()))
                }
            }
        }
        if (query.value.query.isEmpty()){
            SearchSomething(
                message = stringResource(R.string.search_something_message)
            )
        }else if(result.value.isEmpty()){
            NoDataLayout(message = stringResource(R.string.search_no_data_message))
        }
    }
}

@Composable
fun NoDataLayout(modifier: Modifier = Modifier,message: String) {
    Column(
        modifier = Modifier
            .then(modifier)
            .padding(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        Image(
            modifier = Modifier
                .width(200.dp),
            painter = painterResource(R.drawable.img_no_data),
            contentDescription = "no data",
        )
        Text(
            text = message,
            color = White50,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchSomething(modifier: Modifier = Modifier, message: String) {
    Column(
        modifier = Modifier
            .then(modifier)
            .padding(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        Image(
            modifier = Modifier
                .width(200.dp),
            painter = painterResource(R.drawable.img_search_illus),
            contentDescription = "no data",
        )
        Text(
            text = message,
            color = White50,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun NoDatLayoutPrev() {
    NoDataLayout(message = stringResource(R.string.search_no_data_message))
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    imageId: String,
    type: MediaType,
    parentContext: Context,
    onClick: (Int)->Unit
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .height(180.dp)
            .clip(RoundedCornerShape(MaterialTheme.spacing.defaultSmall))
            .background(Color.Gray)
            .clickable {
                onClick(id)
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageId.isBlank()) {
            Image(
                modifier =
                Modifier
                    .size(50.dp),
                imageVector = Icons.Outlined.ImageNotSupported,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Transparent,
                                Black50
                            )
                        )
                    )
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
                ,
                text = title,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Black
            )
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "${Constants.IMAGE_BASE_URL}${imageId}",
                contentDescription = title,
                imageLoader = ImageLoader(parentContext),
                contentScale = ContentScale.Crop,
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Success) {
                    SubcomposeAsyncImageContent()
                    LaunchedEffect(Unit) { println(state.result.dataSource) }
                }
            }
        }

        val image = when(type){
            MediaType.PERSON -> Icons.Outlined.PersonOutline
            MediaType.MOVIE -> Icons.Outlined.Movie
            MediaType.SERIES -> Icons.Outlined.Tv
            MediaType.UNKNOWN -> Icons.Outlined.DeviceUnknown
        }

        Image(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .size(MaterialTheme.spacing.default)
                .align(Alignment.TopEnd)
            ,
            imageVector = image,
            contentDescription = type.getValue()
        )
    }
}

data class SearchData(
    val id: Int,
    val title: String = "Hero",
    val imageId: String = "",
    val type: String = "tv",
)

@Preview
@Composable
private fun SearchScreenPrev() {
    SearchScreen(NavController(LocalContext.current),viewModel = SearchViewModel(
        object : SearchRepository{
        override suspend fun searchAll(query: String, page: Int): Result<List<SearchResult>> {
            return Result.failure()
        }

        override suspend fun searchMovie(query: String, page: Int): Result<List<SearchResult>> {
            return Result.failure()
        }

        override suspend fun searchTv(query: String, page: Int): Result<List<SearchResult>> {
            return Result.failure()
        }

        override suspend fun searchPerson(query: String, page: Int): Result<List<SearchResult>> {
            return Result.failure()
        }
    }))
//    val context = LocalContext.current
//    SearchItem(title = "Hello", imageId = "", type = MediaType.PERSON, parentContext = context)
}

