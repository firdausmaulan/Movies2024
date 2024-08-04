package com.fd.movies.data.remote

import com.fd.movies.data.remote.responses.GenresResponse
import com.fd.movies.data.remote.responses.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en"
    ): Response<GenresResponse>
}