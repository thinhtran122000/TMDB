package com.example.tmdb.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.adapters.trending.RecyclerViewTrendingMovieCastAdapter
import com.example.tmdb.adapters.trending.RecyclerViewTrendingMovieGenresAdapter
import com.example.tmdb.adapters.trending.RecyclerViewTrendingMovieRelatedAdapter
import com.example.tmdb.databinding.ActivityTrendingMovieDetailsBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.models.movies.MovieGenres
import com.example.tmdb.models.movies.RelatedMovie
import com.example.tmdb.utils.Credentials
import com.example.tmdb.viewmodels.TrendingMovieDetailsViewModel
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class TrendingMovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingMovieDetailsBinding
    private lateinit var trendingMovieDetailsViewModel: TrendingMovieDetailsViewModel
    private lateinit var recyclerViewTrendingMovieGenresAdapter: RecyclerViewTrendingMovieGenresAdapter
    private lateinit var recyclerViewTrendingMovieCastAdapter: RecyclerViewTrendingMovieCastAdapter
    private lateinit var recyclerViewTrendingMovieRelatedAdapter: RecyclerViewTrendingMovieRelatedAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var arrayListTrendingMovieGenre:ArrayList<MovieGenres> = arrayListOf()
    private var arrayListTrendingMovieCast: ArrayList<MovieCast> = arrayListOf()
    private var arrayListRelatedTrendingMovies:ArrayList<RelatedMovie> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setting view model for activity
        trendingMovieDetailsViewModel = ViewModelProvider(this)[TrendingMovieDetailsViewModel::class.java]
        // Get Trending movie data from item slider view pager
        var trendingMovieIntent = intent
        val idTrendingMovie = trendingMovieIntent.getIntExtra("Trending movie id",0)
        val voteAverageTM = trendingMovieIntent.getDoubleExtra("Trending movie vote average",0.0)
        // Setting recycler view adapter and layout for trending movie genre list
        recyclerViewTrendingMovieGenresAdapter = RecyclerViewTrendingMovieGenresAdapter(this,arrayListTrendingMovieGenre)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewTrendingMovieGenresAdapter.setHasStableIds(true)
        binding.recyclerViewGenreTM.setHasFixedSize(true)
        binding.recyclerViewGenreTM.itemAnimator = null
        binding.recyclerViewGenreTM.adapter = recyclerViewTrendingMovieGenresAdapter
        binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
        // Setting recycler view adapter and layout for trending movie credits list
        recyclerViewTrendingMovieCastAdapter = RecyclerViewTrendingMovieCastAdapter(this,arrayListTrendingMovieCast)
        recyclerViewTrendingMovieCastAdapter.setHasStableIds(true)
        linearLayoutManager = object :LinearLayoutManager(this,HORIZONTAL,false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
//        binding.recyclerViewCastAndCrewTM.setHasFixedSize(true)
        binding.recyclerViewCastAndCrewTM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewCastAndCrewTM.layoutManager = linearLayoutManager
        binding.recyclerViewCastAndCrewTM.adapter = recyclerViewTrendingMovieCastAdapter
        // Setting recycler view adapter and layout for related trending movie list
        recyclerViewTrendingMovieRelatedAdapter = RecyclerViewTrendingMovieRelatedAdapter(this,arrayListRelatedTrendingMovies)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewRelatedMovieTM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewRelatedMovieTM.layoutManager = linearLayoutManager
        binding.recyclerViewRelatedMovieTM.adapter = recyclerViewTrendingMovieRelatedAdapter
        // Observe data from trending movie details view model
        trendingMovieDetailsViewModel.mutableTrendingMovieDetailsLiveData.observe(this, Observer {
            if(it?.title?.isNotEmpty() == true){
                binding.textViewTitleTM.text = it.title
            }else{
                binding.textViewTitleTM.text = "Unknown title movie"
            }
            if(it?.backdropPath?.isNotEmpty() == true){
                Glide.with(this)
                    .load(Credentials.BACKDROP_PATH + it.backdropPath)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoTM)
            }else{
                Glide.with(this)
                    .load(R.drawable.no_backdrop_available)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoTM)
            }
            if(it?.releaseDate?.isNotEmpty() == true){
                binding.textViewReleaseDateTM.text = formatTrendingMovieReleaseDate(it.releaseDate!!)
            }else{
                binding.textViewReleaseDateTM.text = "Unknown release date"
            }
            if(it?.runtime!=null){
                binding.textViewRunTimeTM.text = it.runtime.toString() + " minutes"
            }else{
                binding.textViewRunTimeTM.text = "Unknown runtime movie"
            }
            if(it?.genres?.isNotEmpty() == true){
                if(it.genres!!.size == 5){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
                    arrayListTrendingMovieGenre.addAll(it.genres!!)
                    recyclerViewTrendingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 4){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
                    arrayListTrendingMovieGenre.addAll(it.genres!!)
                    recyclerViewTrendingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 3){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
                    arrayListTrendingMovieGenre.addAll(it.genres!!)
                    recyclerViewTrendingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 2){

                    staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
                    arrayListTrendingMovieGenre.addAll(it.genres!!)
                    recyclerViewTrendingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 1){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTM.layoutManager = staggeredGridLayoutManager
                    arrayListTrendingMovieGenre.addAll(it.genres!!)
                    recyclerViewTrendingMovieGenresAdapter.notifyDataSetChanged()
                }
            }
            if(it?.voteAverage!=null){
                if(it.voteAverage!! >= voteAverageTM){
                    binding.textViewVoteAverageTM.text = it.voteAverage.toString() + " (IMDb)"
                }else{
                    binding.textViewVoteAverageTM.text = "$voteAverageTM (IMDb)"
                }
            }else{
                binding.textViewVoteAverageTM.text = "Unknown vote average"
            }
            if(it?.overview?.isNotEmpty() == true){
                binding.textViewOverviewTM.text = it.overview + " "
            }else{
                binding.textViewOverviewTM.text = "Unknown overview"
            }
        })
        trendingMovieDetailsViewModel.mutableTrendingMovieVideoLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                for(result in it.results!!){
                    binding.buttonPlayTrailerTM.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Credentials.YOUTUBE_PATH + result.key))
                        startActivity(intent)
                    }
                }
            }else{
                binding.buttonPlayTrailerTM.visibility = View.INVISIBLE
                binding.borderButtonPlayTrailerTM.visibility = View.INVISIBLE
            }
        })
        trendingMovieDetailsViewModel.mutableTrendingMovieCreditsLiveData.observe(this, Observer {
            if(it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == false){
                binding.textViewMoreTM.visibility = View.INVISIBLE
            }
            if((it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == false)) {
                val listTrendingMovieCast = it.cast
                val listTrendingMovieCrew = it.crew
                binding.textViewMoreTM.setOnClickListener {
                    trendingMovieIntent = Intent(this, TrendingMovieListCreditsActivity::class.java)
                    trendingMovieIntent.putParcelableArrayListExtra("List trending movie cast", listTrendingMovieCast)
                    trendingMovieIntent.putParcelableArrayListExtra("List trending movie crew", listTrendingMovieCrew)
                    startActivity(trendingMovieIntent)
                }
                try {
                    if(it.cast?.size!! >= 3){
                        arrayListTrendingMovieCast.add(0,it.cast!![0])
                        arrayListTrendingMovieCast.add(1,it.cast!![1])
                        arrayListTrendingMovieCast.add(2,it.cast!![2])
                        recyclerViewTrendingMovieCastAdapter.notifyDataSetChanged()
                    }else{
                        if(it.cast?.size!! == 2){
                            arrayListTrendingMovieCast.add(0,it.cast!![0])
                            arrayListTrendingMovieCast.add(1,it.cast!![1])
                            recyclerViewTrendingMovieCastAdapter.notifyDataSetChanged()
                        }
                        if(it.cast?.size!! == 1){
                            arrayListTrendingMovieCast.add(0,it.cast!![0])
                            recyclerViewTrendingMovieCastAdapter.notifyDataSetChanged()
                        }
                    }
                }catch (e:Exception){
                    Log.d("Add TM Cast",e.message.toString())
                }
            }
        })
        trendingMovieDetailsViewModel.mutableTrendingMoviesRelatedLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListRelatedTrendingMovies.addAll(it.results!!)
                recyclerViewTrendingMovieRelatedAdapter.notifyDataSetChanged()
            }
        })
        // Call api trending movie details
        trendingMovieDetailsViewModel.getTrendingMovieDetails(idTrendingMovie,"en-US")
        trendingMovieDetailsViewModel.getTrendingMovieVideo(idTrendingMovie,"en-US")
        trendingMovieDetailsViewModel.getTrendingMovieCredits(idTrendingMovie,"en-US")
        trendingMovieDetailsViewModel.getTrendingMoviesRelated(idTrendingMovie,"en-US",1)
        binding.imageViewBackButtonTM.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTrendingMovieReleaseDate(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = simpleDateFormat.parse(releaseDate)
        val secondSimpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
        return secondSimpleDateFormat.format(dateFormat!!)
    }
}

//            if(it?.cast?.isNotEmpty() ==true && it.crew?.isNotEmpty() == false){
//                binding.textViewMoreTM.visibility = View.INVISIBLE
//            }
