package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesDetailsResponse(
    @SerializedName("backdrop_path")
    var backdropPath:String?=null,
    @SerializedName("created_by")
    var createdBy:ArrayList<TvSeriesProducer>?,
    @SerializedName("episode_run_time")
    var episodeRunTime:ArrayList<Int>?=null,
    @SerializedName("first_air_date")
    var firstAirDate:String?=null,
    @SerializedName("genres")
    var genres:ArrayList<TvSeriesGenres>?,
    @SerializedName("homepage")
    var homepage:String?=null,
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("in_production")
    var inProduction:Boolean?=null,
    @SerializedName("languages")
    var languages:ArrayList<String>?=null,
    @SerializedName("last_air_date")
    var lastAirDate:String?=null,
    @SerializedName("last_episode_to_air")
    var lastEpisodeToAir: TvSeriesLastEpisodeToAir?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("networks")
    var networks:ArrayList<TvSeriesNetworks>?,
    @SerializedName("number_of_episodes")
    var numberOfEpisodes:Int?=null,
    @SerializedName("number_of_seasons")
    var numberOfSeasons:Int?=null,
    @SerializedName("origin_country")
    var originCountry:ArrayList<String>?=null,
    @SerializedName("original_language")
    var originalLanguage:String?=null,
    @SerializedName("original_name")
    var originalName:String?=null,
    @SerializedName("overview")
    var overview:String?=null,
    @SerializedName("popularity")
    var popularity:Double?=null,
    @SerializedName("poster_path")
    var posterPath:String?=null,
    @SerializedName("production_companies")
    var productionCompanies:ArrayList<TvSeriesProductionCompanies>?,
    @SerializedName("production_countries")
    var productionCountries:ArrayList<TvSeriesProductionCountries>?,
    @SerializedName("seasons")
    var seasons:ArrayList<TvSeriesSeasons>?,
    @SerializedName("spoken_languages")
    var spokenLanguages:ArrayList<TvSeriesSpokenLanguages>?,
    @SerializedName("status")
    var status:String?=null,
    @SerializedName("tagline")
    var tagline:String?=null,
    @SerializedName("type")
    var type:String?=null,
    @SerializedName("vote_average")
    var voteAverage:Double?=null,
    @SerializedName("vote_count")
    var voteCount:Int?=null,

    )
