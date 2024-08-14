package com.shaikhabdulgani.tmdb.view.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import com.shaikhabdulgani.tmdb.view.MovieDetailActivity
import com.shaikhabdulgani.tmdb.view.SectionWithTitle


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                Home()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    TMDBTheme {
        Home()
    }
}

@Composable
private fun Home() {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color(0xFF15151D))
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF8000FF),
                        Color(0x008000FF),
                        Color(0x008000FF),
                    )
                )
            )
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val context = LocalContext.current
        val movieList: List<Movie> = remember {
            getMovieDummyData(context, R.drawable.bg_onboarding)
        }
        HomeTopTitle()
        SearchBar()
        TabLayout(
            tabs = stringArrayResource(R.array.types).toList()
        )

        MovieRowWithTitle(
            context = context,
            title = stringResource(R.string.trending),
            movies = movieList
        )

        MovieRowWithTitle(
            context = context,
            title = stringResource(R.string.upcoming),
            movies = movieList
        )
    }
}

@Composable
fun MovieRowWithTitle(
    modifier: Modifier = Modifier,
    context: Context,
    title: String,
    movies: List<Movie>
) {
    SectionWithTitle(
        modifier = modifier,
        title = title
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(movies) { i, item ->
                MovieItem(
                    modifier = if (i == movies.size - 1) Modifier.padding(end = 20.dp)
                    else Modifier,
                    position = i, item = item, onClick = {
                    Intent(context, MovieDetailActivity::class.java).also {
                        context.startActivity(it)
                    }
                })
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
    Log.d("MovieItem","Recomposing MovieItem")
    Box(
        modifier
            .size(height = 180.dp, width = 120.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick.invoke(position)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            bitmap = item.coverImage
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0x00000000),
                            Color(0x88000000)
                        )
                    )
                )
                .padding(10.dp),
            text = item.title,
            textAlign = TextAlign.Center
        )
    }
}

fun getMovieDummyData(context: Context,dummyImage: Int): List<Movie> {
    val poster: ImageBitmap = getBitmapFromImage(context,dummyImage).asImageBitmap()
    return Array(10) {
        Movie(title = "Title $it", coverImage = poster)
    }.toList()
}

data class Movie(
    val title: String = "",
    val coverImage: ImageBitmap,
)

fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {
    val db = ContextCompat.getDrawable(context, drawable)
    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bit)
    db.setBounds(0, 0, canvas.width, canvas.height)
    db.draw(canvas)

    return bit
}

@Composable
private fun TabLayout(tabs: List<String>, gap: Dp = 20.dp) {
    var currentActive by remember { mutableIntStateOf(0) }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(gap),
    ) {
        itemsIndexed(tabs) { i, str ->
            TabItem(
                position = i,
                isActive = i == currentActive,
                text = str,
                onClick = {
                    currentActive = it
                }
            )
        }
    }
}

@Composable
fun TabItem(
    position: Int,
    isActive: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = Color.White.copy(alpha = 0.5f),
    activeBackgroundColor: Color = Color(0xFFFF1F8A),
    inActiveBackgroundColor: Color = Color.Transparent,
    onClick: (Int) -> Unit
) {
    val selectedModifier = if (isActive) {
        Modifier
            .clip(RoundedCornerShape(50))
            .background(color = activeBackgroundColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    } else {
        Modifier
            .background(color = inActiveBackgroundColor)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable { onClick(position) }
    }
    val selectedColor = if (isActive) {
        activeTextColor
    } else {
        inactiveTextColor
    }

    Text(
        text = text,
        color = selectedColor,
        modifier = modifier.then(selectedModifier)
    )
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf("") }
    TextField(
        value = state,
        onValueChange = { state = it },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF36076B),
            focusedContainerColor = Color(0xFF36076B),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                tint = Color.White.copy(alpha = 0.5f)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.placeholder_search),
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    )
}

@Composable
private fun HomeTopTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(
                weight = 1f
            ),
            text = stringResource(R.string.home_top_title),
            fontSize = 26.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "",
            modifier = Modifier
                .padding(5.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
        )
    }
}