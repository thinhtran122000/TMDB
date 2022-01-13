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
import com.example.tmdb.adapters.tvseries.RecyclerViewTvSeriesBroadcastingSeasonsAdapter
import com.example.tmdb.adapters.tvseries.RecyclerViewTvSeriesCastAdapter
import com.example.tmdb.adapters.tvseries.RecyclerViewTvSeriesGenresAdapter
import com.example.tmdb.adapters.tvseries.RecyclerViewTvSeriesRelatedAdapter
import com.example.tmdb.databinding.ActivityTvSeriesDetailsBinding
import com.example.tmdb.models.tvseries.RelatedTvSeries
import com.example.tmdb.models.tvseries.TvSeriesGenres
import com.example.tmdb.models.tvseries.TvSeriesSeasons
import com.example.tmdb.models.tvseriescredits.TvSeriesCast
import com.example.tmdb.utils.Credentials
import com.example.tmdb.viewmodels.TvSeriesDetailsViewModel
import java.text.SimpleDateFormat

class TvSeriesDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesDetailsBinding
    private lateinit var tvSeriesDetailsViewModel: TvSeriesDetailsViewModel
    private lateinit var recyclerViewTvSeriesGenresAdapter: RecyclerViewTvSeriesGenresAdapter
    private lateinit var recyclerViewTvSeriesCastAdapter: RecyclerViewTvSeriesCastAdapter
    private lateinit var recyclerViewTvSeriesBroadcastingSeasonsAdapter: RecyclerViewTvSeriesBroadcastingSeasonsAdapter
    private lateinit var recyclerViewTvSeriesRelatedAdapter: RecyclerViewTvSeriesRelatedAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var arrayListTvSeriesGenre:ArrayList<TvSeriesGenres> = arrayListOf()
    private var arrayListTvSeriesCast:ArrayList<TvSeriesCast> = arrayListOf()
    private var arrayListTvSeriesSeasons:ArrayList<TvSeriesSeasons> = arrayListOf()
    private var arrayListRelatedTvSeries:ArrayList<RelatedTvSeries> = arrayListOf()

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setting view model for activity
        tvSeriesDetailsViewModel = ViewModelProvider(this)[TvSeriesDetailsViewModel::class.java]
        // Get Tv series data from item recycler view
        var tvSeriesIntent = intent
        val idTvSeries = tvSeriesIntent.getIntExtra("Tv series id",0)
        val voteAverageTS = tvSeriesIntent.getDoubleExtra("Tv series vote average",0.0)
        // Setting recycler view adapter and layout for tv series genre list
        recyclerViewTvSeriesGenresAdapter = RecyclerViewTvSeriesGenresAdapter(this,arrayListTvSeriesGenre)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewTvSeriesGenresAdapter.setHasStableIds(true)
        binding.recyclerViewGenreTS.setHasFixedSize(true)
        binding.recyclerViewGenreTS.itemAnimator = null
        binding.recyclerViewGenreTS.adapter = recyclerViewTvSeriesGenresAdapter
        binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
        // Setting recycler view adapter and layout for tv series credits list
        recyclerViewTvSeriesCastAdapter = RecyclerViewTvSeriesCastAdapter(this,arrayListTvSeriesCast)
        recyclerViewTvSeriesCastAdapter.setHasStableIds(true)
        linearLayoutManager = object :LinearLayoutManager(this,HORIZONTAL,false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
//        binding.recyclerViewCastAndCrewTS.setHasFixedSize(true)
        binding.recyclerViewCastAndCrewTS.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewCastAndCrewTS.layoutManager = linearLayoutManager
        binding.recyclerViewCastAndCrewTS.adapter = recyclerViewTvSeriesCastAdapter
        // Setting recycler view adapter and layout for tv series broadcasting seasons list
        recyclerViewTvSeriesBroadcastingSeasonsAdapter =
            RecyclerViewTvSeriesBroadcastingSeasonsAdapter(this,arrayListTvSeriesSeasons)
//        recyclerViewTvSeriesBroadcastingSeasonsAdapter.setHasStableIds(true)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewBroadcastingSeasonTS.setHasFixedSize(true)
        binding.recyclerViewBroadcastingSeasonTS.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewBroadcastingSeasonTS.layoutManager = linearLayoutManager
        binding.recyclerViewBroadcastingSeasonTS.adapter = recyclerViewTvSeriesBroadcastingSeasonsAdapter
        // Setting recycler view adapter and layout for related tv series list
        recyclerViewTvSeriesRelatedAdapter = RecyclerViewTvSeriesRelatedAdapter(this,arrayListRelatedTvSeries)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewRelatedMovieTS.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewRelatedMovieTS.layoutManager = linearLayoutManager
        binding.recyclerViewRelatedMovieTS.adapter = recyclerViewTvSeriesRelatedAdapter
        // Observe data from tv series details view model
        tvSeriesDetailsViewModel.mutableTvSeriesDetailsLiveData.observe(this, Observer {
            if(it?.name?.isNotEmpty() == true){
                binding.textViewTitleTS.text = it.name
            }else{
                binding.textViewTitleTS.text = "Unknown name"
            }
            if(it?.backdropPath?.isNotEmpty() == true){
                Glide.with(this)
                    .load(Credentials.BACKDROP_PATH + it.backdropPath)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoTS)
            }else{
                Glide.with(this)
                    .load(R.drawable.no_backdrop_available)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imageViewBackdropVideoTS)
            }
            if(it?.voteAverage!=null){
                if(it.voteAverage!! >= voteAverageTS){
                    binding.textViewVoteAverageTS.text = it.voteAverage.toString() + " (IMDb)"
                }else{
                    binding.textViewVoteAverageTS.text = "$voteAverageTS (IMDb)"
                }
            }else{
                binding.textViewVoteAverageTS.text = "Unknown vote average"
            }
            if(it?.numberOfEpisodes!=null){
                binding.textViewEpisodeTS.text = it.numberOfEpisodes.toString() + " episodes"
            }else{
                binding.textViewEpisodeTS.text = "Unknown episode"
            }
            if(it?.numberOfSeasons != null){
                if(it.numberOfSeasons!! <=1){
                    binding.textViewSeasonTS.text = it.numberOfSeasons.toString() + " season"
                }else{
                    binding.textViewSeasonTS.text = it.numberOfSeasons.toString() + " seasons"
                }
            }else{
                binding.textViewSeasonTS.text = "Unknown season"
            }
            if(it?.firstAirDate?.isNotEmpty() == true){
                binding.textViewFirstAirDateTS.text = formatTvSeriesReleaseDate(it.firstAirDate!!)
            }else{
                binding.textViewFirstAirDateTS.text = "Unknown first air date"
            }
            if(it?.lastAirDate?.isNotEmpty() == true){
                binding.textViewLastAirDateTS.text = formatTvSeriesReleaseDate(it.lastAirDate!!)
            }else{
                binding.textViewLastAirDateTS.text = "Unknown last air date"
            }
            if(it?.genres?.isNotEmpty()==true){
                if(it.genres!!.size == 7){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 6){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(6,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 5){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 4){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 3){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 2){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
                if(it.genres!!.size == 1){
                    staggeredGridLayoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
                    binding.recyclerViewGenreTS.layoutManager = staggeredGridLayoutManager
                    arrayListTvSeriesGenre.addAll(it.genres!!)
                    recyclerViewTvSeriesGenresAdapter.notifyDataSetChanged()
                }
            }
            if(it?.overview?.isNotEmpty() == true){
                binding.textViewOverviewTS.text = it.overview + " "
            }else{
                binding.textViewOverviewTS.text = "Unknown overview"
            }
            if(it?.seasons?.isNotEmpty() == true){
                arrayListTvSeriesSeasons.addAll(it.seasons!!)
                recyclerViewTvSeriesBroadcastingSeasonsAdapter.notifyDataSetChanged()
            }
        })
        tvSeriesDetailsViewModel.mutableTvSeriesVideoLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                for(result in it.results!!){
                    binding.buttonPlayTrailerTS.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Credentials.YOUTUBE_PATH + result.key))
                        startActivity(intent)
                    }
                }
            }else{
                binding.buttonPlayTrailerTS.visibility = View.INVISIBLE
                binding.borderButtonPlayTrailerTS.visibility = View.INVISIBLE
            }
        })
        tvSeriesDetailsViewModel.mutableTvSeriesCreditsLiveData.observe(this, Observer {
            if(it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == false){
                binding.textViewMoreTS.visibility = View.INVISIBLE
            }
            if((it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == false && it.crew?.isNotEmpty() == true)||
                (it?.cast?.isNotEmpty() == true && it.crew?.isNotEmpty() == false)){
                val listTvSeriesCast = it.cast
                val listTvSeriesCrew = it.crew
                binding.textViewMoreTS.setOnClickListener {
                    tvSeriesIntent = Intent(this, TvSeriesListCreditsActivity::class.java)
                    tvSeriesIntent.putParcelableArrayListExtra("List tv series cast", listTvSeriesCast)
                    tvSeriesIntent.putParcelableArrayListExtra("List tv series crew", listTvSeriesCrew)
                    startActivity(tvSeriesIntent)
                }
                try {
                    if(it.cast?.size!! >= 3){
                        arrayListTvSeriesCast.add(0,it.cast!![0])
                        arrayListTvSeriesCast.add(1,it.cast!![1])
                        arrayListTvSeriesCast.add(2,it.cast!![2])
                        recyclerViewTvSeriesCastAdapter.notifyDataSetChanged()
                    }else{
                        if(it.cast?.size!! == 2){
                            arrayListTvSeriesCast.add(0,it.cast!![0])
                            arrayListTvSeriesCast.add(1,it.cast!![1])
                            recyclerViewTvSeriesCastAdapter.notifyDataSetChanged()
                        }
                        if(it.cast?.size!! == 1){
                            arrayListTvSeriesCast.add(0,it.cast!![0])
                            recyclerViewTvSeriesCastAdapter.notifyDataSetChanged()
                        }
                    }
                }catch (e:Exception){
                    Log.d("Add TM Cast",e.message.toString())
                }
            }
        })
        tvSeriesDetailsViewModel.mutableTvSeriesRelatedLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListRelatedTvSeries.addAll(it.results!!)
                recyclerViewTvSeriesRelatedAdapter.notifyDataSetChanged()
            }
        })
        // Call api tv series details
        tvSeriesDetailsViewModel.getTvSeriesDetails(idTvSeries,"en-US")
        tvSeriesDetailsViewModel.getTvSeriesVideo(idTvSeries,"en-US")
        tvSeriesDetailsViewModel.getTvSeriesCredits(idTvSeries,"en-US")
        tvSeriesDetailsViewModel.getTvSeriesRelated(idTvSeries,"en-US",1)
        binding.imageViewBackButtonTS.setOnClickListener {
            onBackPressed()
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun formatTvSeriesReleaseDate(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = simpleDateFormat.parse(releaseDate)
        val secondSimpleDateFormat = SimpleDateFormat("MMMM dd, yyyy")
        return secondSimpleDateFormat.format(dateFormat!!)
    }
}