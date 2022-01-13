package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.adapters.tvseries.RecyclerViewListTvSeriesCastAdapter
import com.example.tmdb.adapters.tvseries.RecyclerViewListTvSeriesCrewAdapter
import com.example.tmdb.databinding.ActivityTvSeriesListCreditsBinding
import com.example.tmdb.models.tvseriescredits.TvSeriesCast
import com.example.tmdb.models.tvseriescredits.TvSeriesCrew

class TvSeriesListCreditsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesListCreditsBinding
    private lateinit var recyclerViewListTvSeriesCastAdapter: RecyclerViewListTvSeriesCastAdapter
    private lateinit var recyclerViewListTvSeriesCrewAdapter: RecyclerViewListTvSeriesCrewAdapter
    private lateinit var recyclerViewTvSeriesCreditsConcatAdapter: ConcatAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesListCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var tvSeriesCreditsIntent = intent
        val listTvSeriesCast = tvSeriesCreditsIntent.getParcelableArrayListExtra<TvSeriesCast>("List tv series cast")
        val listTvSeriesCrew = tvSeriesCreditsIntent.getParcelableArrayListExtra<TvSeriesCrew>("List tv series crew")
        recyclerViewListTvSeriesCastAdapter = RecyclerViewListTvSeriesCastAdapter(this,listTvSeriesCast)
        recyclerViewListTvSeriesCrewAdapter = RecyclerViewListTvSeriesCrewAdapter(this,listTvSeriesCrew)
        recyclerViewTvSeriesCreditsConcatAdapter = ConcatAdapter(recyclerViewListTvSeriesCastAdapter,recyclerViewListTvSeriesCrewAdapter)
        gridLayoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewListCreditsTS.setHasFixedSize(true)
        binding.recyclerViewListCreditsTS.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewListCreditsTS.layoutManager = gridLayoutManager
        binding.recyclerViewListCreditsTS.adapter = recyclerViewTvSeriesCreditsConcatAdapter
        recyclerViewListTvSeriesCastAdapter.notifyDataSetChanged()
        recyclerViewListTvSeriesCrewAdapter.notifyDataSetChanged()
        binding.imageViewBackButtonCreditsTS.setOnClickListener {
            onBackPressed()
        }
    }
}