package com.fd.movies.data.mapper

import com.fd.movies.data.remote.responses.GenreResponse
import com.fd.movies.data.remote.responses.MovieResponse
import com.fd.movies.data.remote.responses.MoviesResponse
import com.fd.movies.ui.movie.model.Movie
import com.fd.movies.utils.Constants

object MovieMapper {

    private var genres: List<GenreResponse>? = null
    suspend fun mapToUiModel(response: MoviesResponse?, genres: List<GenreResponse>): List<Movie> {
        this.genres = genres
        val list = mutableListOf<Movie>()
        response?.results?.forEach { list.add(mapToUiModel(it)) }
        return list
    }

    private suspend fun mapToUiModel(response: MovieResponse?): Movie {
        return Movie(
            id = response?.id,
            title = response?.title,
            backdropPath = response?.backdropPath,
            genres = getGenreText(response?.genreIds),
            overview = response?.overview,
            posterPath = response?.posterPath,
            posterUrl = "${Constants.IMAGE_URL}${response?.posterPath}",
            releaseDate = response?.releaseDate,
            voteAverage = response?.voteAverage
        )
    }

    private fun getGenreText(genreIds: List<Int?>?): String {
        if (genreIds != null) {
            return try {
                genreIds.joinToString(", ") { genreId ->
                    val genre = genres?.find { it.id == genreId }
                    genre?.name ?: ""
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
                "-"
            }
        }
        return "-"
    }
}