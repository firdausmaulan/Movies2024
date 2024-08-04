package com.fd.movies.data.remote.responses

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenreResponse>? = null
)