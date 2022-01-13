package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.movies.NowPlayingMoviesResponse
import com.example.tmdb.models.tvseries.TvSeriesResponse
import com.example.tmdb.models.movies.TrendingMoviesResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class MovieViewModel :ViewModel(){
    var mutableTrendingMoviesLiveData:MutableLiveData<TrendingMoviesResponse?> = MutableLiveData()
    var mutableNowPlayingMoviesLiveData:MutableLiveData<NowPlayingMoviesResponse?> = MutableLiveData()
    var mutableTvSeriesLiveData:MutableLiveData<TvSeriesResponse?> = MutableLiveData()
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


    private fun onLoadTrendingMoviesError(){
        Log.d("Error","Failed to get trending movies data from View Model")
    }
    private fun onLoadNowPlayingMoviesError(){
        Log.d("Error","Failed to get now playing movies data from View Model")
    }
    private fun onLoadPopularTvSeriesError(){
        Log.d("Error","Failed to get popular tv series data from View Model")
    }

}