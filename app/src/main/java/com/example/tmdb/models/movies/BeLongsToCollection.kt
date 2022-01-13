package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class BeLongsToCollection(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("poster_path")
    var posterPath:String?=null,
    @SerializedName("backdrop_path")
    var backdropPath:String?=null
)
