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

class NowPlayingMovieDetailsViewModel:ViewModel() {
    var mutableNowPlayingMovieDetailsLiveData: MutableLiveData<MovieDetailsResponse?> = MutableLiveData()
    var mutableNowPlayingMovieVideoLiveData:MutableLiveData<MovieVideoResponse?> = MutableLiveData()
    var mutableNowPlayingMovieCreditsLiveData:MutableLiveData<MovieCreditsResponse?> =MutableLiveData()
    var mutableNowPlayingMoviesRelatedLiveData:MutableLiveData<RelatedMoviesResponse?> =MutableLiveData()
    private var repository:Repository = Repository()

    private fun setNowPlayingMovieDetailsData(movieDetailsResponse:MovieDetailsResponse?){
        mutableNowPlayingMovieDetailsLiveData.value = movieDetailsResponse
    }
    fun getNowPLayingMovieDetails(movieId:Int, language:String){
        repository.getNowPlayingMovieDetails(movieId,Credentials.API_KEY,language,this::setNowPlayingMovieDetailsData,this::onLoadNowPlayingMovieDetailsError)
    }

    private fun setNowPlayingMovieVideoData(movieVideoResponse:MovieVideoResponse?){
        mutableNowPlayingMovieVideoLiveData.value = movieVideoResponse
    }
    fun getNowPlayingMovieVideo(movieId:Int, language:String){
        repository.getNowPlayingMovieVideo(movieId,Credentials.API_KEY,language,this::setNowPlayingMovieVideoData,this::onLoadNowPlayingMovieVideoError)
    }
    private fun setNowPlayingMovieCreditsData(movieCreditsResponse:MovieCreditsResponse?){
        mutableNowPlayingMovieCreditsLiveData.value = movieCreditsResponse
    }
    fun getNowPlayingMovieCredits(movieId: Int,language: String){
        repository.getNowPlayingMovieCredits(movieId,Credentials.API_KEY,language,
            this::setNowPlayingMovieCreditsData,this::onLoadNowPlayingMovieCreditsError)
    }
    private fun setNowPlayingMoviesRelatedData(relatedMoviesResponse:RelatedMoviesResponse?){
        mutableNowPlayingMoviesRelatedLiveData.value = relatedMoviesResponse
    }
    fun getNowPlayingMoviesRelated(movieId: Int,language: String,page:Int){
        repository.getNowPlayingMoviesRelated(movieId,Credentials.API_KEY,language,page,
            this::setNowPlayingMoviesRelatedData,this::onLoadNowPlayingMovieRelatedError)
    }

    private fun onLoadNowPlayingMovieDetailsError(){
        Log.d("Error","Failed to get now playing movie details data from View Model")
    }
    private fun onLoadNowPlayingMovieVideoError(){
        Log.d("Error","Failed to get now playing movie video data from View Model")
    }
    private fun onLoadNowPlayingMovieCreditsError(){
        Log.d("Error","Failed to get now playing movie credits data from View Model")
    }
    private fun onLoadNowPlayingMovieRelatedError(){
        Log.d("Error","Failed to get now playing movie related data from View Model")
    }
}