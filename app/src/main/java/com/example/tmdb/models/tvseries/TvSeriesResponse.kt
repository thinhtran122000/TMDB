package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<TvSeries>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
