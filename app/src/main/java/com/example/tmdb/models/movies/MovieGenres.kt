package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class MovieGenres(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("name")
    var name:String?=null
)
