package com.shaikhabdulgani.tmdb.moviedetail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.moviedetail.domain.repository.MovieDetailRepository
import com.shaikhabdulgani.tmdb.moviedetail.presentation.util.emptyMovieDetail
import com.shaikhabdulgani.tmdb.search.domain.model.ContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository
) : ViewModel() {

    var movieDetail by mutableStateOf(emptyMovieDetail())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getMovieDetails(id: Int, contentType: ContentType) {
        viewModelScope.launch {
            isLoading = true
            repository.getMovieDetail(id,contentType).collect {
                when (it) {
                    is Resource.Error -> {
                        isLoading = false
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        isLoading = false
                        if (it.data != null) {
                            movieDetail = it.data
                        }
                    }
                }
            }
        }
    }

}