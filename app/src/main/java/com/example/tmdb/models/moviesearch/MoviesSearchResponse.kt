package com.example.tmdb.models.moviesearch

import com.example.tmdb.models.movies.NowPlayingMovie
import com.google.gson.annotations.SerializedName

data class MoviesSearchResponse(
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<MovieSearch>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
