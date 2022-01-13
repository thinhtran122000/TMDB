package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<TrendingMovie>?,
    @SerializedName("total_pages")
    var totalPage:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
