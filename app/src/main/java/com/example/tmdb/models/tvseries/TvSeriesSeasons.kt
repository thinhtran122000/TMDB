package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName


data class TvSeriesSeasons(
    @SerializedName("air_date")
    var airDate:String?=null,
    @SerializedName("episode_count")
    var episodeCount:Int?=null,
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("overview")
    var overview:String?=null,
    @SerializedName("poster_path")
    var posterPath:String?=null,
    @SerializedName("season_number")
    var seasonNumber:Int?=null
)
