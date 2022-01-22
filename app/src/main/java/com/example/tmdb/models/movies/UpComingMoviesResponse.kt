package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class UpComingMoviesResponse(
    @SerializedName("dates")
    var dates: UpComingMovieDates?=null,
    @SerializedName("page")
    var page:Int?=null,
    @SerializedName("results")
    var results:ArrayList<UpComingMovie>?,
    @SerializedName("total_pages")
    var totalPages:Int?=null,
    @SerializedName("total_results")
    var totalResults:Int?=null
)
