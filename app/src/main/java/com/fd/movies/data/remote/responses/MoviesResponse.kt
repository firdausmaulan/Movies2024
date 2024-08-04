package com.fd.movies.data.remote.responses

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieResponse?>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)