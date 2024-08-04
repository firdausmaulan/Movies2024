package com.fd.movies.data.repositories

import com.fd.movies.data.remote.ApiService
import com.fd.movies.data.remote.NetworkCaller
import com.fd.movies.data.remote.NetworkResult
import com.fd.movies.data.remote.responses.GenreResponse
import com.fd.movies.data.remote.responses.GenresResponse
import com.fd.movies.data.remote.responses.MoviesResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService) : MovieRepository,
    NetworkCaller() {
    override suspend fun searchMovies(query: String, page: Int): MoviesResponse {
        val response = getResult { apiService.searchMovies(query = query, page = page) }
        return when (response) {
            is NetworkResult.Success -> response.data
            is NetworkResult.Error -> throw response.exception
        }
    }

    override suspend fun getGenres(): List<GenreResponse>? {
        val response = getResult { apiService.getGenres() }
        return when (response) {
            is NetworkResult.Success -> response.data.genres
            is NetworkResult.Error -> throw response.exception
        }
    }
}