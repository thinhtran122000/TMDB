package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tmdb.R
import com.example.tmdb.adapters.searching.RecyclerViewTvSeriesSearchResultsAdapter
import com.example.tmdb.databinding.ActivitySearchMoviesAndTvSeriesBinding
import com.example.tmdb.models.tvseriessearch.TvSeriesSearch

class SearchMoviesAndTvSeriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesAndTvSeriesBinding
    private lateinit var recyclerViewTvSeriesSearchResultsAdapter: RecyclerViewTvSeriesSearchResultsAdapter
    private var arrayListSearchTvSeriesResults:ArrayList<TvSeriesSearch> = arrayListOf()
    private var pageTvSeriesSearch:Int = 1
    private var totalPagesTvSeriesSearch:Int = 0
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMoviesAndTvSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentSearch,
            SearchMovieFragment()
        ).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed();
    }
}










//        movieAndTvSeriesSearchViewModel.mutableTvSeriesSearchLiveData.observe(this, Observer {
//            if(it?.results?.isNotEmpty() == true){
//                arrayListSearchTvSeriesResults.addAll(it.results!!)
//                recyclerViewTvSeriesSearchResultsAdapter.notifyDataSetChanged()
//            }
//        })
//        movieAndTvSeriesSearchViewModel.mutableTvSeriesSearchLiveData.observe(this, Observer {
//            totalPagesTvSeriesSearch = it?.totalPages!!
//        })

//        movieAndTvSeriesSearchViewModel.getTvSeriesSearch("en-US",pageTvSeriesSearch,"A",false)