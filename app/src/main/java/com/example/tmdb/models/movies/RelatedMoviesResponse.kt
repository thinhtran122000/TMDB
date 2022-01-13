package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class RelatedMoviesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<RelatedMovie>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
