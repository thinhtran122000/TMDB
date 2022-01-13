package com.example.tmdb.models.tvseries

import com.google.gson.annotations.SerializedName

data class TvSeriesNetworks(
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("logo_path")
    var logoPath:String?=null,
    @SerializedName("origin_country")
    var originCountry:String?=null,
)
