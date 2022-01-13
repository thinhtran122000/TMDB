package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapters.trending.RecyclerViewListTrendingMovieCastAdapter
import com.example.tmdb.adapters.trending.RecyclerViewListTrendingMovieCrewAdapter
import com.example.tmdb.databinding.ActivityTrendingMovieListCreditsBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.models.moviecredits.MovieCrew

class TrendingMovieListCreditsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingMovieListCreditsBinding
    private lateinit var recyclerViewListTrendingMovieCastAdapter: RecyclerViewListTrendingMovieCastAdapter
    private lateinit var recyclerViewListTrendingMovieCrewAdapter: RecyclerViewListTrendingMovieCrewAdapter
    private lateinit var recyclerViewTrendingMovieCreditsConcatAdapter: ConcatAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingMovieListCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val colorQueryHint = resources.getColor(R.color.gray)
        var trendingMovieCreditsIntent = intent
        val listTrendingMovieCast = trendingMovieCreditsIntent.getParcelableArrayListExtra<MovieCast>("List trending movie cast")
        val lisTrendingMovieCrew = trendingMovieCreditsIntent.getParcelableArrayListExtra<MovieCrew>("List trending movie crew")
        recyclerViewListTrendingMovieCastAdapter = RecyclerViewListTrendingMovieCastAdapter(this,listTrendingMovieCast)
        recyclerViewListTrendingMovieCrewAdapter = RecyclerViewListTrendingMovieCrewAdapter(this,lisTrendingMovieCrew)
        recyclerViewTrendingMovieCreditsConcatAdapter = ConcatAdapter(recyclerViewListTrendingMovieCastAdapter,recyclerViewListTrendingMovieCrewAdapter)
        gridLayoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewListCreditsTM.setHasFixedSize(true)
        binding.recyclerViewListCreditsTM.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewListCreditsTM.layoutManager = gridLayoutManager
        binding.recyclerViewListCreditsTM.adapter = recyclerViewTrendingMovieCreditsConcatAdapter
        recyclerViewListTrendingMovieCastAdapter.notifyDataSetChanged()
        recyclerViewListTrendingMovieCrewAdapter.notifyDataSetChanged()
        binding.imageViewBackButtonCreditsTM.setOnClickListener {
            onBackPressed()
        }
//        binding.searchViewCreditsTM.queryHint = Html.fromHtml("<font color=$colorQueryHint>Find cast, crew person...</font>")
//        binding.searchViewCreditsTM.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
////                recyclerViewListTrendingMovieCastAdapter.filter.filter(p0)
////                recyclerViewListTrendingMovieCrewAdapter.filter.filter(p0)
//                return true
//            }
//
//        })
    }

}