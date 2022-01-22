package com.example.tmdb.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.adapters.tvseries.RecyclerViewTvSeriesAdapter
import com.example.tmdb.databinding.FragmentTvSeriesBinding
import com.example.tmdb.models.tvseries.TvSeries
import com.example.tmdb.viewmodels.MoviesViewModel

class TvSeriesFragment : Fragment() {
    private lateinit var binding: FragmentTvSeriesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var recyclerViewTvSeriesAdapter: RecyclerViewTvSeriesAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private var arrayListTvSeries:ArrayList<TvSeries> = arrayListOf()
    private var pagePopularTvSeries:Int = 1
    private var totalPagesPopularTvSeries:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentTvSeriesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        recyclerViewTvSeriesAdapter = RecyclerViewTvSeriesAdapter(requireContext(),arrayListTvSeries)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewTvSeriesAdapter.setHasStableIds(true)
        binding.recyclerViewTvSeries.adapter = recyclerViewTvSeriesAdapter
        binding.recyclerViewTvSeries.layoutManager = staggeredGridLayoutManager
        binding.recyclerViewTvSeries.setHasFixedSize(true)
        binding.recyclerViewTvSeries.itemAnimator = null;
        moviesViewModel.mutableTvSeriesLiveData.observe(viewLifecycleOwner, Observer {
            if(it?.results !=null){
                this.arrayListTvSeries.addAll(it.results!!)
            }
            recyclerViewTvSeriesAdapter.notifyDataSetChanged()
        })
        moviesViewModel.getTvSeries("en-US",pagePopularTvSeries)
        moviesViewModel.mutableTvSeriesLiveData.observe(viewLifecycleOwner, Observer {
            totalPagesPopularTvSeries = it?.totalPages!!
        })
        binding.recyclerViewTvSeries.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    if(pagePopularTvSeries < totalPagesPopularTvSeries){
                        Handler().postDelayed({
                            pagePopularTvSeries+=1
                            moviesViewModel.getTvSeries("en-US",pagePopularTvSeries)
                        },2000)

                    }else{
                        Toast.makeText(requireContext(),"All of Tv series is displayed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

}