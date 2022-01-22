package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<PopularMovie>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
