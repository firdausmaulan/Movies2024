package com.fd.movies.data.repositories

import com.fd.movies.data.models.Genre
import com.fd.movies.data.remote.ApiService
import com.fd.movies.data.responses.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun searchMovies(query: String, page: Int): MovieResponse {
        return apiService.searchMovies(query, page = page)
    }

    suspend fun getGenres(): List<Genre> {
        return apiService.getGenres().genres
    }
}