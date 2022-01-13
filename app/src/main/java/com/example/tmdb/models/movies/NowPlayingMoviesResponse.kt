package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesResponse(
    @SerializedName("dates")
    var dates: NowPlayingMovieDates?=null,
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<NowPlayingMovie>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
