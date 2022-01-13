package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class NowPlayingMovieDates(
    @SerializedName("maximum")
    var maximum:String?=null,
    @SerializedName("minimum")
    var minimum:String?=null
)
