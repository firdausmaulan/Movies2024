package com.fd.movies.ui.movie.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.fd.movies.data.remote.responses.GenreResponse
import com.fd.movies.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _genreUiState = MutableLiveData<GenreUiState>()
    val genreUiState get() = _genreUiState

    private val genres = mutableListOf<GenreResponse>()

    data class GenreUiState(
        val isLoading: Boolean = true,
        val isSuccess: Boolean = false,
        val errorMessage: String? = null
    )

    init {
        viewModelScope.launch {
            _genreUiState.value = GenreUiState(isLoading = true)
            try {
                repository.getGenres()?.let { genres.addAll(it) }
                _genreUiState.value = GenreUiState(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _genreUiState.value = GenreUiState(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun searchMovies(query: String) =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviesPagingSource(repository, genres, query) }
        ).liveData
}