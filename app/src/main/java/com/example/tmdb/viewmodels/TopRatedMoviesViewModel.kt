package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.movies.TopRatedMoviesResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class TopRatedMoviesViewModel:ViewModel() {
    var mutableTopRatedMoviesLiveData: MutableLiveData<TopRatedMoviesResponse?> = MutableLiveData()
    private var repository: Repository = Repository()
    private fun setTopRatedMoviesData(topRatedMoviesResponse: TopRatedMoviesResponse?){
        mutableTopRatedMoviesLiveData.value = topRatedMoviesResponse
    }
    fun getTopRatedMovies(language: String,page: Int){
        repository.getTopRatedMovies(Credentials.API_KEY,language,page,this::setTopRatedMoviesData,this::onLoadTopRatedMoviesError)
    }
    private fun onLoadTopRatedMoviesError(){
        Log.d("Error","Failed to get top rated movies data from View Model")
    }
}