package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.movies.*
import com.example.tmdb.models.tvseries.TvSeriesResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class MoviesViewModel :ViewModel(){
    var mutableTrendingMoviesLiveData:MutableLiveData<TrendingMoviesResponse?> = MutableLiveData()
    var mutableNowPlayingMoviesLiveData:MutableLiveData<NowPlayingMoviesResponse?> = MutableLiveData()
    var mutableTvSeriesLiveData:MutableLiveData<TvSeriesResponse?> = MutableLiveData()
    var mutableUpComingMoviesLiveData:MutableLiveData<UpComingMoviesResponse?> = MutableLiveData()
    var mutableUpComingMovieVideoData:MutableLiveData<MovieVideoResponse?> = MutableLiveData()
    var mutablePopularMoviesLiveData:MutableLiveData<PopularMoviesResponse?> = MutableLiveData()
    private var repository:Repository = Repository()

    private fun setTrendingMoviesData(trendingMoviesResponse: TrendingMoviesResponse?){
        mutableTrendingMoviesLiveData.value = trendingMoviesResponse
    }
    fun getTrendingMovies(mediaType:String, timeWindow:String){
        repository.getTrendingMovies(mediaType,timeWindow,Credentials.API_KEY,this::setTrendingMoviesData,this::onLoadTrendingMoviesError)
    }

    private fun setNowPlayingMoviesData(nowPlayingMoviesResponse: NowPlayingMoviesResponse?){
        mutableNowPlayingMoviesLiveData.value = nowPlayingMoviesResponse
    }
    fun getNowPlayingMovies(language:String, page:Int){
        repository.getNowPlayingMovies(Credentials.API_KEY,language,page,this::setNowPlayingMoviesData,this::onLoadNowPlayingMoviesError)
    }
    private fun setTvSeriesData(tvSeriesResponse: TvSeriesResponse?){
        mutableTvSeriesLiveData.value = tvSeriesResponse
    }
    fun getTvSeries(language: String,page: Int){
        repository.getTvSeries(Credentials.API_KEY,language,page,this::setTvSeriesData,this::onLoadPopularTvSeriesError)
    }
    private fun setUpComingMoviesData(upComingMoviesResponse: UpComingMoviesResponse?){
        mutableUpComingMoviesLiveData.value = upComingMoviesResponse
    }
    fun getUpComingMovies(language: String,page: Int){
        repository.getUpComingMovies(Credentials.API_KEY,language,page,this::setUpComingMoviesData,this::onLoadUpComingMoviesError)
    }
    private fun setUpComingMovieVideoData(movieVideoResponse:MovieVideoResponse?){
        mutableUpComingMovieVideoData.value = movieVideoResponse
    }
    fun getUpComingMovieVideo(movieId:Int, language:String){
        repository.getUpComingMovieVideo(movieId,Credentials.API_KEY,language,this::setUpComingMovieVideoData,this::onLoadUpComingMovieVideoError)
    }
    private fun setPopularMoviesData(popularMoviesResponse:PopularMoviesResponse?){
        mutablePopularMoviesLiveData.value = popularMoviesResponse
    }
    fun getPopularMovies(language: String,page: Int){
        repository.getPopularMovies(Credentials.API_KEY,language,page,this::setPopularMoviesData,this::onLoadPopularTvSeriesError)
    }

    private fun onLoadTrendingMoviesError(){
        Log.d("Error","Failed to get trending movies data from View Model")
    }
    private fun onLoadNowPlayingMoviesError(){
        Log.d("Error","Failed to get now playing movies data from View Model")
    }
    private fun onLoadPopularTvSeriesError(){
        Log.d("Error","Failed to get popular tv series data from View Model")
    }
    private fun onLoadUpComingMoviesError(){
        Log.d("Error","Failed to get up coming movies data from View Model")
    }
    private fun onLoadUpComingMovieVideoError(){
        Log.d("Error","Failed to get up coming movie video data from View Model")
    }
    private fun onLoadPopularMoviesError(){
        Log.d("Error","Failed to get popular movies data from View Model")
    }


}