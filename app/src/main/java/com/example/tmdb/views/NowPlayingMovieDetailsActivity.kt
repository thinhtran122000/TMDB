package com.example.tmdb.views

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.adapters.nowplaying.RecyclerViewNowPlayingMovieCastAdapter
import com.example.tmdb.adapters.nowplaying.RecyclerViewNowPlayingMovieGenresAdapter
import com.example.tmdb.adapters.nowplaying.RecyclerViewNowPlayingMovieRelatedAdapter
import com.example.tmdb.databinding.ActivityNowPlayingMovieDetailsBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.models.movies.MovieGenres
import com.example.tmdb.models.movies.RelatedMovie
import com.example.tmdb.utils.Credentials
import com.example.tmdb.viewmodels.NowPlayingMovieDetailsViewModel
import java.text.SimpleDateFormat

class NowPlayingMovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNowPlayingMovieDetailsBinding
    private lateinit var nowPlayingMovieDetailsViewModel: NowPlayingMovieDetailsViewModel
    private lateinit var recyclerViewNowPlayingMovieGenresAdapter: RecyclerViewNowPlayingMovieGenresAdapter
    private lateinit var recyclerViewNowPlayingMovieCastAdapter: RecyclerViewNowPlayingMovieCastAdapter
    private lateinit var recyclerViewNowPlayingMovieRelatedAdapter: RecyclerViewNowPlayingMovieRelatedAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var arrayListNowPlayingMovieGenre:ArrayList<MovieGenres> = arrayListOf()
    private var arrayListNowPlayingMovieCast: ArrayList<MovieCast> = arrayListOf()
    private var arrayListRelatedNowPlayingMovies:ArrayList<RelatedMovie> = arrayListOf()

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setting view model for activity
        nowPlayingMovieDetailsViewModel = ViewModelProvider(this)[NowPlayingMovieDetailsViewModel::class.java]
        // Get Now playing movie data from item recyclerview
        var nowPlayingMovieIntent = intent
        val idNowPlayingMovie = nowPlayingMovieIntent.getIntExtra("Now playing movie id",0)
        val voteAverageNPM = nowPlayingMovieIntent.getDoubleExtra("Now playing movie vote average",0.0)
        // Setting recycler view adapter and layout for now playing movie genre list
        recyclerViewNowPlayingMovieGenresAdapter = RecyclerViewNowPlayingMovieGenresAdapter(this,arrayListNowPlayingMovieGenre)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewNowPlayingMovieGenresAdapter.setHasStableIds(true)
        binding.recyclerViewGenreNPM.setHasFixedSize(true)
        binding.recyclerViewGenreNPM.itemAnimator = null
        binding.recyclerViewGenreNPM.adapter = recyclerViewNowPlayingMovieGenresAdapter
        binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
        // Setting recycler view adapter and layout for now playing movie credits list
        recyclerViewNowPlayingMovieCastAdapter = RecyclerViewNowPlayingMovieCastAdapter(this,arrayListNowPlayingMovieCast)
        recyclerViewNowPlayingMovieCastAdapter.setHasStableIds(true)
        linearLayoutManager = object :LinearLayoutManager(this,HORIZONTAL,false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
//        binding.recyclerViewCastAndCrewNPM.setHasFixedSize(true)
        binding.recyclerViewCastAndCrewNPM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewCastAndCrewNPM.layoutManager = linearLayoutManager
        binding.recyclerViewCastAndCrewNPM.adapter = recyclerViewNowPlayingMovieCastAdapter
        // Setting recycler view adapter and layout for related now playing movie list
        recyclerViewNowPlayingMovieRelatedAdapter = RecyclerViewNowPlayingMovieRelatedAdapter(this,arrayListRelatedNowPlayingMovies)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewRelatedMovieNPM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewRelatedMovieNPM.layoutManager = linearLayoutManager
        binding.recyclerViewRelatedMovieNPM.adapter = recyclerViewNowPlayingMovieRelatedAdapter
        // Observe data from now playing movie details view model
        nowPlayingMovieDetailsViewModel.mutableNowPlayingMovieDetailsLiveData.observe(this, Observer {
            if(it?.title?.isNotEmpty() == true){
                binding.textViewTitleNPM.text = it.title
            }else{
                binding.textViewTitleNPM.text = "Unknown title movie"
            }
            if(it?.backdropPath?.isNotEmpty() == true){
                Glide.with(this)
                    .load(Credentials.BACKDROP_PATH + it.backdropPath)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoNPM)
            }else{
                Glide.with(this)
                    .load(R.drawable.no_backdrop_available)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoNPM)
            }
            if(it?.releaseDate?.isNotEmpty() == true){
                binding.textViewReleaseDateNPM.text = formatNowPlayingMovieReleaseDate(it.releaseDate!!)
            }else{
                binding.textViewReleaseDateNPM.text = "Unknown release date"
            }
            if(it?.runtime!=null){
                binding.textViewRunTimeNPM.text = it.runtime.toString() + " minutes"
            }else{
                binding.textViewRunTimeNPM.text = "Unknown runtime movie"
            }
            if(it?.genres?.isNotEmpty() == true){
                if(it.genres!!.size == 5){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
                    arrayListNowPlayingMovieGenre.addAll(it.genres!!)
                    recyclerViewNowPlayingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 4){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
                    arrayListNowPlayingMovieGenre.addAll(it.genres!!)
                    recyclerViewNowPlayingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 3){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
                    arrayListNowPlayingMovieGenre.addAll(it.genres!!)
                    recyclerViewNowPlayingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 2){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
                    arrayListNowPlayingMovieGenre.addAll(it.genres!!)
                    recyclerViewNowPlayingMovieGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 1){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreNPM.layoutManager = staggeredGridLayoutManager
                    arrayListNowPlayingMovieGenre.addAll(it.genres!!)
                    recyclerViewNowPlayingMovieGenresAdapter.notifyDataSetChanged()
                }
            }
            if(it?.voteAverage!=null){
                if(it.voteAverage!! >= voteAverageNPM){
                    binding.textViewVoteAverageNPM.text = it.voteAverage.toString() + " (IMDb)"
                }else{
                    binding.textViewVoteAverageNPM.text = "$voteAverageNPM (IMDb)"
                }
            }else{
                binding.textViewVoteAverageNPM.text = "Unknown vote average"
            }
            if(it?.overview?.isNotEmpty() == true){
                binding.textViewOverviewNPM.text = it.overview + " "
            }else{
                binding.textViewOverviewNPM.text = "Unknown overview"
            }
        })
        nowPlayingMovieDetailsViewModel.mutableNowPlayingMovieVideoLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                for(result in it.results!!){
                    binding.buttonPlayTrailerNPM.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Credentials.YOUTUBE_PATH + result.key))
                        startActivity(intent)
                    }
                }
            }else{
                binding.buttonPlayTrailerNPM.visibility = View.INVISIBLE
                binding.borderButtonPlayTrailerNPM.visibility = View.INVISIBLE
            }
        })
        nowPlayingMovieDetailsViewModel.mutableNowPlayingMovieCreditsLiveData.observe(this, Observer {
            if(it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == false){
                binding.textViewMoreNPM.visibility = View.INVISIBLE
            }
            if((it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == false)) {
                val listNowPlayingMovieCast = it.cast
                val listNowPlayingMovieCrew = it.crew
                binding.textViewMoreNPM.setOnClickListener {
                    nowPlayingMovieIntent = Intent(this, NowPlayingMovieListCreditsActivity::class.java)
                    nowPlayingMovieIntent.putParcelableArrayListExtra("List now playing movie cast", listNowPlayingMovieCast)
                    nowPlayingMovieIntent.putParcelableArrayListExtra("List now playing movie crew", listNowPlayingMovieCrew)
                    startActivity(nowPlayingMovieIntent)
                }
                try {
                    if(it.cast?.size!! >= 3){
                        arrayListNowPlayingMovieCast.add(0,it.cast!![0])
                        arrayListNowPlayingMovieCast.add(1,it.cast!![1])
                        arrayListNowPlayingMovieCast.add(2,it.cast!![2])
                        recyclerViewNowPlayingMovieCastAdapter.notifyDataSetChanged()
                    }else{
                        if(it.cast?.size!! == 2){
                            arrayListNowPlayingMovieCast.add(0,it.cast!![0])
                            arrayListNowPlayingMovieCast.add(1,it.cast!![1])
                            recyclerViewNowPlayingMovieCastAdapter.notifyDataSetChanged()
                        }
                        if(it.cast?.size!! == 1){
                            arrayListNowPlayingMovieCast.add(0,it.cast!![0])
                            recyclerViewNowPlayingMovieCastAdapter.notifyDataSetChanged()
                        }
                    }
                }catch (e:Exception){
                    Log.d("Add NPM Cast",e.message.toString())
                }
            }
        })
        nowPlayingMovieDetailsViewModel.mutableNowPlayingMoviesRelatedLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListRelatedNowPlayingMovies.addAll(it.results!!)
                recyclerViewNowPlayingMovieRelatedAdapter.notifyDataSetChanged()
            }
        })
        // Call api now playing movie details
        nowPlayingMovieDetailsViewModel.getNowPLayingMovieDetails(idNowPlayingMovie,"en-US")
        nowPlayingMovieDetailsViewModel.getNowPlayingMovieVideo(idNowPlayingMovie,"en-US")
        nowPlayingMovieDetailsViewModel.getNowPlayingMovieCredits(idNowPlayingMovie,"en-US")
        nowPlayingMovieDetailsViewModel.getNowPlayingMoviesRelated(idNowPlayingMovie,"en-US",1)
        binding.imageViewBackButtonNPM.setOnClickListener {
            onBackPressed()
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun formatNowPlayingMovieReleaseDate(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = simpleDateFormat.parse(releaseDate)
        val secondSimpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
        return secondSimpleDateFormat.format(dateFormat!!)
    }
}
//            if(it?.cast?.isNotEmpty() ==true && it.crew?.isNotEmpty() == false){
//                binding.textViewMoreNPM.visibility = View.INVISIBLE
//            }