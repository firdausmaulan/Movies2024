package com.fd.movies.ui.user.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("type")
    var type: String? = null,
)