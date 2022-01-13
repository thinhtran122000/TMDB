package com.example.tmdb.models.moviecredits

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("cast")
    var cast:ArrayList<MovieCast>?,
    @SerializedName("crew")
    var crew:ArrayList<MovieCrew>?
)
