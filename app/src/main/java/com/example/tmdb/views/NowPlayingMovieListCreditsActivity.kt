package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.adapters.nowplaying.RecyclerViewListNowPlayingMovieCastAdapter
import com.example.tmdb.adapters.nowplaying.RecyclerViewListNowPlayingMovieCrewAdapter
import com.example.tmdb.adapters.trending.RecyclerViewListTrendingMovieCastAdapter
import com.example.tmdb.adapters.trending.RecyclerViewListTrendingMovieCrewAdapter
import com.example.tmdb.databinding.ActivityNowPlayingMovieListCreditsBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.models.moviecredits.MovieCrew

class NowPlayingMovieListCreditsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNowPlayingMovieListCreditsBinding
    private lateinit var recyclerViewListNowPlayingMovieCastAdapter: RecyclerViewListNowPlayingMovieCastAdapter
    private lateinit var recyclerViewListNowPlayingMovieCrewAdapter: RecyclerViewListNowPlayingMovieCrewAdapter
    private lateinit var recyclerViewNowPlayingMovieCreditsConcatAdapter: ConcatAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingMovieListCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var nowPlayingMovieCreditsIntent = intent
        val listNowPlayingMovieCast = nowPlayingMovieCreditsIntent.getParcelableArrayListExtra<MovieCast>("List now playing movie cast")
        val lisNowPlayingMovieCrew = nowPlayingMovieCreditsIntent.getParcelableArrayListExtra<MovieCrew>("List now playing movie crew")
        recyclerViewListNowPlayingMovieCastAdapter = RecyclerViewListNowPlayingMovieCastAdapter(this,listNowPlayingMovieCast)
        recyclerViewListNowPlayingMovieCrewAdapter = RecyclerViewListNowPlayingMovieCrewAdapter(this,lisNowPlayingMovieCrew)
        recyclerViewNowPlayingMovieCreditsConcatAdapter = ConcatAdapter(recyclerViewListNowPlayingMovieCastAdapter,recyclerViewListNowPlayingMovieCrewAdapter)
        gridLayoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewListCreditsNPM.setHasFixedSize(true)
        binding.recyclerViewListCreditsNPM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewListCreditsNPM.layoutManager = gridLayoutManager
        binding.recyclerViewListCreditsNPM.adapter = recyclerViewNowPlayingMovieCreditsConcatAdapter
        recyclerViewListNowPlayingMovieCastAdapter.notifyDataSetChanged()
        recyclerViewListNowPlayingMovieCrewAdapter.notifyDataSetChanged()
        binding.swipeRefreshLayoutCreditsNPM.setOnRefreshListener {
            Handler().postDelayed({
                recyclerViewListNowPlayingMovieCastAdapter.notifyDataSetChanged()
                recyclerViewListNowPlayingMovieCrewAdapter.notifyDataSetChanged()
            },2000)
            Handler().postDelayed({
                binding.swipeRefreshLayoutCreditsNPM.isRefreshing = false
            },3000)
        }
        binding.imageViewBackButtonCreditsNPM.setOnClickListener {
            onBackPressed()
        }
    }
}