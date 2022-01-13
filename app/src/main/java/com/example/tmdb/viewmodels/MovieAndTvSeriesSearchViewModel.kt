package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.moviesearch.MoviesSearchResponse
import com.example.tmdb.models.tvseriessearch.TvSeriesSearchResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class MovieAndTvSeriesSearchViewModel:ViewModel() {
    var mutableMoviesSearchLiveData:MutableLiveData<MoviesSearchResponse?> = MutableLiveData()
    var mutableTvSeriesSearchLiveData:MutableLiveData<TvSeriesSearchResponse?> = MutableLiveData()
    private var repository:Repository = Repository()

    private fun setMoviesSearchData(moviesSearchResponse:MoviesSearchResponse?){
        mutableMoviesSearchLiveData.value = moviesSearchResponse
    }

    fun getMoviesSearch(language:String, query:String, page:Int, includeAdult:Boolean){
        repository.searchMovies(Credentials.API_KEY,language,query,page,includeAdult,this::setMoviesSearchData,this::onLoadMoviesSearchError)
    }
    private fun setTvSeriesSearchData(tvSeriesSearchResponse: TvSeriesSearchResponse?){
        mutableTvSeriesSearchLiveData.value = tvSeriesSearchResponse
    }
    fun getTvSeriesSearch(language:String,page:Int,query:String,includeAdult:Boolean){
        repository.searchTvSeries(Credentials.API_KEY,language,page,query,includeAdult,this::setTvSeriesSearchData,this::onLoadTvSeriesSearchError)
    }

    private fun onLoadMoviesSearchError(){
        Log.d("Error","Failed to get movies search data from View Model")
    }
    private fun onLoadTvSeriesSearchError(){
        Log.d("Error","Failed to get tv series search data from View Model")
    }
}