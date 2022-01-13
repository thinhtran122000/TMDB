package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesGenres(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("name")
    var name:String?=null,
)
