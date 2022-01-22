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
import com.example.tmdb.adapters.nowplaying.RecyclerViewMoviesAdapter
import com.example.tmdb.databinding.FragmentMoviesBinding
import com.example.tmdb.models.movies.NowPlayingMovie
import com.example.tmdb.viewmodels.MoviesViewModel

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var recyclerViewMoviesAdapter: RecyclerViewMoviesAdapter
    private var arrayListNowPlayingMovies:ArrayList<NowPlayingMovie> = arrayListOf()
    private lateinit var staggeredGridLayoutManager:StaggeredGridLayoutManager
    private var pageNowPlayingMovie:Int = 1
    private var totalPagesNowPlayingMovie:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        recyclerViewMoviesAdapter = RecyclerViewMoviesAdapter(requireContext(),arrayListNowPlayingMovies)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewMoviesAdapter.setHasStableIds(true)
        binding.recyclerViewMovies.adapter = recyclerViewMoviesAdapter
        binding.recyclerViewMovies.layoutManager = staggeredGridLayoutManager
        binding.recyclerViewMovies.setHasFixedSize(true)
        binding.recyclerViewMovies.itemAnimator = null
        moviesViewModel.mutableNowPlayingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results !=null){
                arrayListNowPlayingMovies.addAll(it.results!!)
            }
            recyclerViewMoviesAdapter.notifyDataSetChanged()
        })
        moviesViewModel.getNowPlayingMovies("en-US",pageNowPlayingMovie)
        moviesViewModel.mutableNowPlayingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            totalPagesNowPlayingMovie = it?.totalPages!!
        })
        binding.recyclerViewMovies.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    if(pageNowPlayingMovie < totalPagesNowPlayingMovie){
                        Handler().postDelayed({
                            pageNowPlayingMovie+=1
                            moviesViewModel.getNowPlayingMovies("en-US",pageNowPlayingMovie)
                        },2000)
                    }else {
                        Toast.makeText(requireContext(),"All of Movies is displayed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}