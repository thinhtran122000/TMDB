package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.models.tvseries.RelatedTvSeriesResponse
import com.example.tmdb.models.tvseries.TvSeriesDetailsResponse
import com.example.tmdb.models.tvseries.TvSeriesVideoResponse
import com.example.tmdb.models.tvseriescredits.TvSeriesCreditsResponse
import com.example.tmdb.repositories.Repository
import com.example.tmdb.utils.Credentials

class TvSeriesDetailsViewModel:ViewModel() {
    var mutableTvSeriesDetailsLiveData: MutableLiveData<TvSeriesDetailsResponse?> = MutableLiveData()
    var mutableTvSeriesVideoLiveData: MutableLiveData<TvSeriesVideoResponse?> = MutableLiveData()
    var mutableTvSeriesCreditsLiveData:MutableLiveData<TvSeriesCreditsResponse?> = MutableLiveData()
    var mutableTvSeriesRelatedLiveData:MutableLiveData<RelatedTvSeriesResponse?> = MutableLiveData()
    private var repository:Repository = Repository()
    private fun setTvSeriesDetailsData(tvSeriesDetailsResponse:TvSeriesDetailsResponse?){
        mutableTvSeriesDetailsLiveData.value = tvSeriesDetailsResponse
    }
    fun getTvSeriesDetails(tvId:Int,language:String){
        repository.getTvSeriesDetails(tvId,Credentials.API_KEY,language,
            this::setTvSeriesDetailsData,this::onLoadTvSeriesDetailsError)
    }
    private fun setTvSeriesVideoData(tvSeriesVideoResponse:TvSeriesVideoResponse?){
        mutableTvSeriesVideoLiveData.value = tvSeriesVideoResponse
    }
    fun getTvSeriesVideo(tvId:Int,language:String){
        repository.getTvSeriesVideo(tvId,Credentials.API_KEY,language,
            this::setTvSeriesVideoData,this::onLoadTvSeriesVideoError)
    }
    private fun setTvSeriesCreditsData(tvSeriesCreditsResponse:TvSeriesCreditsResponse?){
        mutableTvSeriesCreditsLiveData.value = tvSeriesCreditsResponse
    }
    fun getTvSeriesCredits(tvId: Int,language: String){
        repository.getTvSeriesCredits(tvId,Credentials.API_KEY,language,
            this::setTvSeriesCreditsData,this::onLoadTvSeriesCreditsError)
    }
    private fun setTvSeriesRelatedData(relatedTvSeriesResponse:RelatedTvSeriesResponse?){
        mutableTvSeriesRelatedLiveData.value = relatedTvSeriesResponse
    }
    fun getTvSeriesRelated(tvId: Int,language: String,page:Int){
        repository.getTvSeriesRelated(tvId,Credentials.API_KEY,language,page,
            this::setTvSeriesRelatedData,this::onLoadTvSeriesRelatedError)
    }

    private fun onLoadTvSeriesDetailsError(){
        Log.d("Error","Failed to get tv series details data from View Model")
    }
    private fun onLoadTvSeriesVideoError(){
        Log.d("Error","Failed to get tv series video data from View Model")
    }
    private fun onLoadTvSeriesCreditsError(){
        Log.d("Error","Failed to get tv series credits data from View Model")
    }
    private fun onLoadTvSeriesRelatedError(){
        Log.d("Error","Failed to get tv series related data from View Model")
    }
}