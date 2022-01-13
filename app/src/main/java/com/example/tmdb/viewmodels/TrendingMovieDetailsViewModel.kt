package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.moviecredits.MovieCreditsResponse
import com.example.tmdb.models.movies.MovieDetailsResponse
import com.example.tmdb.models.movies.MovieVideoResponse
import com.example.tmdb.models.movies.RelatedMoviesResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class TrendingMovieDetailsViewModel:ViewModel() {
    var mutableTrendingMovieDetailsLiveData:MutableLiveData<MovieDetailsResponse?> = MutableLiveData()
    var mutableTrendingMovieVideoLiveData:MutableLiveData<MovieVideoResponse?> = MutableLiveData()
    var mutableTrendingMovieCreditsLiveData:MutableLiveData<MovieCreditsResponse?> = MutableLiveData()
    var mutableTrendingMoviesRelatedLiveData:MutableLiveData<RelatedMoviesResponse?> =
        MutableLiveData()
    private var repository:Repository = Repository()
    private fun setTrendingMovieDetailsData(movieDetailsResponse: MovieDetailsResponse?){
        mutableTrendingMovieDetailsLiveData.value = movieDetailsResponse
    }
    fun getTrendingMovieDetails(movieId:Int,language:String){
        repository.getTrendingMovieDetails(movieId,Credentials.API_KEY,language,
            this::setTrendingMovieDetailsData,this::onLoadTrendingMovieDetailsError)
    }
    private fun setTrendingMovieVideoData(movieVideoResponse:MovieVideoResponse?){
        mutableTrendingMovieVideoLiveData.value = movieVideoResponse
    }
    fun getTrendingMovieVideo(movieId:Int,language:String){
        repository.getTrendingMovieVideo(movieId,Credentials.API_KEY,language,
            this::setTrendingMovieVideoData,this::onLoadTrendingMovieVideoError)
    }
    private fun setTrendingMovieCreditsData(movieCreditsResponse:MovieCreditsResponse?){
        mutableTrendingMovieCreditsLiveData.value = movieCreditsResponse
    }
    fun getTrendingMovieCredits(movieId: Int,language: String){
        repository.getTrendingMovieCredits(movieId,Credentials.API_KEY,language,
            this::setTrendingMovieCreditsData,this::onLoadTrendingMovieCreditsError)
    }
    private fun setTrendingMoviesRelatedData(relatedMoviesResponse:RelatedMoviesResponse?){
        mutableTrendingMoviesRelatedLiveData.value = relatedMoviesResponse
    }
    fun getTrendingMoviesRelated(movieId: Int,language: String,page:Int){
        repository.getTrendingMoviesRelated(movieId,Credentials.API_KEY,language,page,
            this::setTrendingMoviesRelatedData,this::onLoadTrendingMovieRelatedError)
    }
    private fun onLoadTrendingMovieDetailsError(){
        Log.d("Error","Failed to get trending movie details data from View Model")
    }
    private fun onLoadTrendingMovieVideoError(){
        Log.d("Error","Failed to get trending movie video data from View Model")
    }
    private fun onLoadTrendingMovieCreditsError(){
        Log.d("Error","Failed to get trending movie credits data from View Model")
    }
    private fun onLoadTrendingMovieRelatedError(){
        Log.d("Error","Failed to get trending movie related data from View Model")
    }
}