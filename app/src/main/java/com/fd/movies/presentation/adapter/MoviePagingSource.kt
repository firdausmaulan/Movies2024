package com.fd.movies.presentation.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fd.movies.data.models.Movie
import com.fd.movies.data.repositories.MovieRepository

class MoviePagingSource(
    private val repository: MovieRepository,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response = repository.searchMovies(query, position)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position < response.totalPages) position + 1 else null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // If data is empty, return null to signal that there's no refresh key available
        if (state.anchorPosition == null) {
            return null
        }

        // Find the closest page to the anchor position (current position) and return its key
        return state.closestPageToPosition(state.anchorPosition!!)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(state.anchorPosition!!)?.nextKey?.minus(1)
    }
}
