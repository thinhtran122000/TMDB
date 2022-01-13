package com.example.tmdb.repositories

import com.example.tmdb.models.moviecredits.MovieCreditsResponse
import com.example.tmdb.models.movies.*
import com.example.tmdb.models.moviesearch.MoviesSearchResponse
import com.example.tmdb.models.tvseries.RelatedTvSeriesResponse
import com.example.tmdb.models.tvseries.TvSeriesResponse
import com.example.tmdb.models.tvseries.TvSeriesDetailsResponse
import com.example.tmdb.models.tvseries.TvSeriesVideoResponse
import com.example.tmdb.models.tvseriescredits.TvSeriesCreditsResponse
import com.example.tmdb.models.tvseriessearch.TvSeriesSearchResponse
import com.example.tmdb.services.APIRequest
import com.example.tmdb.services.APIService
import com.example.tmdb.utils.Credentials

class Repository {
    fun getTrendingMovies(mediaType:String,
                          timeWindow:String,
                          apiKey:String,
                          OnSuccess:(TrendingMoviesResponse?)->Unit,
                          OnError:()->Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getTrendingMovies(mediaType,timeWindow,Credentials.API_KEY),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTrendingMovieDetails(movieId:Int,
                                apiKey:String,
                                language:String,
                                OnSuccess:(MovieDetailsResponse?) ->Unit,
                                OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getDetailsTrendingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTrendingMovieVideo(movieId:Int,
                              apiKey:String,
                              language:String,
                              OnSuccess:(MovieVideoResponse?) -> Unit,
                              OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getVideoTrendingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getNowPlayingMovies(apiKey: String,
                            language:String,
                            page:Int,
                            OnSuccess: (NowPlayingMoviesResponse?) -> Unit,
                            OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getNowPlayingMovies(Credentials.API_KEY,language,page),
            onSuccess =  {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getNowPlayingMovieDetails(movieId:Int,
                                  apiKey:String,
                                  language:String,
                                  OnSuccess: (MovieDetailsResponse?) -> Unit,
                                  OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getDetailsNowPlayingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getNowPlayingMovieVideo(movieId:Int,
                                apiKey:String,
                                language:String,
                                OnSuccess: (MovieVideoResponse?) -> Unit,
                                OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getVideoNowPlayingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTvSeries(apiKey: String,
                    language: String,
                    page: Int,
                    OnSuccess: (TvSeriesResponse?) -> Unit,
                    OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getTvSeries(Credentials.API_KEY,language,page),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTvSeriesDetails(tvId:Int,
                           apiKey:String,
                           language:String,
                           OnSuccess: (TvSeriesDetailsResponse?) -> Unit,
                           OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getDetailsTvSeries(tvId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTvSeriesVideo(tvId:Int,
                         apiKey:String,
                         language:String,
                         OnSuccess: (TvSeriesVideoResponse?) -> Unit,
                         OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getVideoTvSeries(tvId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTrendingMovieCredits(movieId: Int,
                                apiKey: String,
                                language:String,
                                OnSuccess: (MovieCreditsResponse?) -> Unit,
                                OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getCreditsTrendingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getNowPlayingMovieCredits(movieId: Int,
                                  apiKey: String,
                                  language:String,
                                  OnSuccess: (MovieCreditsResponse?) -> Unit,
                                  OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getCreditsNowPlayingMovie(movieId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTvSeriesCredits(tvId: Int,
                           apiKey: String,
                           language: String,
                           OnSuccess: (TvSeriesCreditsResponse?) -> Unit,
                           OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getCreditsTvSeries(tvId,Credentials.API_KEY,language),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTrendingMoviesRelated(movieId: Int,
                                 apiKey: String,
                                 language: String,
                                 page: Int,
                                 OnSuccess: (RelatedMoviesResponse?) -> Unit,
                                 OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getRelatedTrendingMovies(movieId,Credentials.API_KEY,language,page),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getNowPlayingMoviesRelated(movieId: Int,
                                   apiKey: String,
                                   language: String,
                                   page: Int,
                                   OnSuccess: (RelatedMoviesResponse?) -> Unit,
                                   OnError: () -> Unit){
        APIRequest.callRequest(
            call =  APIService.getInstance().getRelatedNowPLayingMovie(movieId,Credentials.API_KEY,language,page),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun getTvSeriesRelated(tvId: Int,
                           apiKey: String,
                           language: String,
                           page: Int,
                           OnSuccess: (RelatedTvSeriesResponse?) -> Unit,
                           OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().getRelatedTvSeries(tvId,Credentials.API_KEY,language,page),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun searchMovies(apiKey: String,
                     language:String,
                     query:String,
                     page:Int,
                     includeAdult:Boolean,
                     OnSuccess: (MoviesSearchResponse?) -> Unit,
                     OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().searchMovies(Credentials.API_KEY,language, query, page, includeAdult),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
    fun searchTvSeries(apiKey: String,
                       language:String,
                       page:Int,
                       query:String,
                       includeAdult:Boolean,
                       OnSuccess: (TvSeriesSearchResponse?) -> Unit,
                       OnError: () -> Unit){
        APIRequest.callRequest(
            call = APIService.getInstance().searchTvSeries(Credentials.API_KEY,language, page, query, includeAdult),
            onSuccess = {results -> OnSuccess.invoke(results!!)},
            onError = {OnError()}
        )
    }
}