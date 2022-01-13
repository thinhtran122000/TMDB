package com.example.tmdb.models.movies

import com.google.gson.annotations.SerializedName

data class RelatedMovie(
    @SerializedName("adult")
    var adult:Boolean?=null,
    @SerializedName("backdrop_path")
    var backdropPath:String?=null,
    @SerializedName("genre_ids")
    var genre_ids:ArrayList<Int>?=null,
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("title")
    var title:String?=null,
    @SerializedName("original_language")
    var originalLanguage:String?=null,
    @SerializedName("original_title")
    var originalTitle:String?=null,
    @SerializedName("overview")
    var overview:String?=null,
    @SerializedName("popularity")
    var popularity:Double?=null,
    @SerializedName("poster_path")
    var posterPath:String?=null,
    @SerializedName("release_date")
    var releaseDate:String?=null,
    @SerializedName("video")
    var video:Boolean?=null,
    @SerializedName("vote_average")
    var voteAverage:Double?=null,
    @SerializedName("vote_count")
    var voteCount:Int?=null
)
