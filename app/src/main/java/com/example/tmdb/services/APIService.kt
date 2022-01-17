package com.example.tmdb.services

import com.example.tmdb.models.moviecredits.MovieCreditsResponse
import com.example.tmdb.models.movies.*
import com.example.tmdb.models.moviesearch.MoviesSearchResponse
import com.example.tmdb.models.tvseries.RelatedTvSeriesResponse
import com.example.tmdb.models.tvseries.TvSeriesDetailsResponse
import com.example.tmdb.models.tvseries.TvSeriesResponse
import com.example.tmdb.models.tvseries.TvSeriesVideoResponse
import com.example.tmdb.models.tvseriescredits.TvSeriesCreditsResponse
import com.example.tmdb.models.tvseriessearch.TvSeriesSearch
import com.example.tmdb.models.tvseriessearch.TvSeriesSearchResponse
import com.example.tmdb.utils.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface APIService {
    companion object{
        private var instance:APIService? = null
        private fun create():APIService{
            val interceptor = Interceptor {
                val originalRequest = it.request()
                val newHttpUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("app_key", Credentials.API_KEY)
                    .build()
                val request = originalRequest.newBuilder()
                    .url(newHttpUrl)
                    .build()
                it.proceed(request)
            }
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addNetworkInterceptor(interceptor)
            return Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
        fun getInstance(): APIService {
            synchronized(APIService::class.java) {
                instance = create()
            }
            return instance!!
        }
    }
    @GET("trending/{media_type}/{time_window}")
    fun getTrendingMovies(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String,
        @Query("api_key") apiKey:String
    ): Call<TrendingMoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetailsTrendingMovie(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<MovieDetailsResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideoTrendingMovie(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<MovieVideoResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ):Call<NowPlayingMoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetailsNowPlayingMovie(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<MovieDetailsResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideoNowPlayingMovie(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<MovieVideoResponse>

    @GET("tv/popular")
    fun getTvSeries(
        @Query("api_key") apiKey:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ):Call<TvSeriesResponse>

    @GET("tv/{tv_id}")
    fun getDetailsTvSeries(
        @Path("tv_id") tvId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<TvSeriesDetailsResponse>

    @GET("tv/{tv_id}/videos")
    fun getVideoTvSeries(
        @Path("tv_id") tvId:Int,
        @Query("api_key") apiKey:String,
        @Query("language") language:String
    ):Call<TvSeriesVideoResponse>

    @GET("movie/{movie_id}/credits")
    fun getCreditsTrendingMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language:String
    ):Call<MovieCreditsResponse>

    @GET("movie/{movie_id}/credits")
    fun getCreditsNowPlayingMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language:String
    ):Call<MovieCreditsResponse>

    @GET("tv/{tv_id}/credits")
    fun getCreditsTvSeries(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ):Call<TvSeriesCreditsResponse>

    @GET("movie/{movie_id}/similar")
    fun getRelatedTrendingMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language:String,
        @Query("page") page: Int
    ):Call<RelatedMoviesResponse>

    @GET("movie/{movie_id}/similar")
    fun getRelatedNowPLayingMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language:String,
        @Query("page") page: Int
    ):Call<RelatedMoviesResponse>

    @GET("tv/{tv_id}/similar")
    fun getRelatedTvSeries(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language:String,
        @Query("page") page: Int
    ):Call<RelatedTvSeriesResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language:String,
        @Query("query") query:String,
        @Query("page") page:Int,
        @Query("include_adult") includeAdult:Boolean
    ):Call<MoviesSearchResponse>

    @GET("search/tv")
    fun searchTvSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language:String,
        @Query("page") page:Int,
        @Query("query") query:String,
        @Query("include_adult") includeAdult:Boolean
    ):Call<TvSeriesSearchResponse>
}