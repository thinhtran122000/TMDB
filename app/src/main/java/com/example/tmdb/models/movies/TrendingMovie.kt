package com.example.tmdb.models.movies

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName

data class TrendingMovie(
    @SerializedName("title")
    var title:String?=null,
    @SerializedName("id")
    var id:Int?=null,
    @SerializedName("backdrop_path")
    var backdropPath:String?=null,
    @SerializedName("genre_ids")
    var genreIds:ArrayList<Int>?=null,
    @SerializedName("original_language")
    var originalLanguage:String?=null,
    @SerializedName("original_title")
    var originalTitle:String?=null,
    @SerializedName("poster_path")
    var posterPath:String?=null,
    @SerializedName("adult")
    var adult:Boolean?=null,
    @SerializedName("video")
    var video:Boolean?=null,
    @SerializedName("vote_average")
    var voteAverage:Double?=null,
    @SerializedName("release_date")
    var releaseDate:String?=null,
    @SerializedName("vote_count")
    var voteCount:Int?=null,
    @SerializedName("overview")
    var overview:String?=null,
    @SerializedName("popularity")
    var popularity:Double?=null,
    @SerializedName("media_type")
    var mediaType:String?=null,
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("first_air_date")
    var firstAirDate:String?=null
)
