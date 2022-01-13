package com.example.tmdb.models.tvseriescredits

import com.google.gson.annotations.SerializedName

data class TvSeriesCreditsResponse(
    @SerializedName("cast")
    var cast:ArrayList<TvSeriesCast>?,
    @SerializedName("crew")
    var crew:ArrayList<TvSeriesCrew>?,
    @SerializedName("id")
    var id:Int?=null
)