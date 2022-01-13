package com.example.tmdb.models.tvseriessearch

import com.example.tmdb.models.tvseries.TvSeries
import com.google.gson.annotations.SerializedName

data class TvSeriesSearchResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<TvSeriesSearch>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
