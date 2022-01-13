package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesVideoResponse(
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("results")
    var results:ArrayList<TvSeriesVideo>?
)
