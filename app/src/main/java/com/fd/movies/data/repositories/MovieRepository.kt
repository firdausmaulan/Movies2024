package com.fd.movies.data.repositories

import com.fd.movies.data.remote.responses.GenreResponse
import com.fd.movies.data.remote.responses.MoviesResponse

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int): MoviesResponse
    suspend fun getGenres(): List<GenreResponse>?
}