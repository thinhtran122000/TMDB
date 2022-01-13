package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesProductionCountries(
    @SerializedName("iso_3166_1")
    var iso31661:String?=null,
    @SerializedName("name")
    var name:String?=null
)
