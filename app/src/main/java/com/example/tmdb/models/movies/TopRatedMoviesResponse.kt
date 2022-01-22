package com.example.tmdb.models.movies

import com.example.tmdb.models.moviesearch.MovieSearch
import com.google.gson.annotations.SerializedName

data class TopRatedMoviesResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<TopRatedMovie>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
