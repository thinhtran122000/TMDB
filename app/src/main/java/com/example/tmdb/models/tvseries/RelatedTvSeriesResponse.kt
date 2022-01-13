package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class RelatedTvSeriesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<RelatedTvSeries>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null,
)
