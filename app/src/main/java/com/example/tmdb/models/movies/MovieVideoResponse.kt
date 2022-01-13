package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("results")
    var results:ArrayList<MovieVideo>?
)
