package com.shaikhabdulgani.tmdb.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.ui.theme.SearchBarBg
import com.shaikhabdulgani.tmdb.ui.theme.Shapes
import com.shaikhabdulgani.tmdb.ui.theme.Transparent
import com.shaikhabdulgani.tmdb.ui.theme.White50

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onSearch: (String) -> Unit = {},
    onQueryChange: (String) -> Unit,
) {
    TextField(
            value = query,
    onValueChange = onQueryChange,
    colors = TextFieldDefaults.colors(
        unfocusedContainerColor = SearchBarBg,
        focusedContainerColor = SearchBarBg,
        focusedIndicatorColor = Transparent,
        unfocusedIndicatorColor = Transparent,
    ),
    modifier = Modifier
        .then(modifier)
        .clip(Shapes.fullRoundedCorner),
    trailingIcon = {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(R.string.placeholder_search),
            tint = White50,
            modifier = Modifier.clickable {
                onSearch(query)
            }
        )
    },
    placeholder = {
        Text(
            text = stringResource(R.string.placeholder_search),
            color = White50
        )
    },
    singleLine = true,
    maxLines = 1
    )
}