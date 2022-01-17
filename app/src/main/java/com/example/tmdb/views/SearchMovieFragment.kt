package com.example.tmdb.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapters.searching.RecyclerViewMovieSearchResultsAdapter
import com.example.tmdb.databinding.FragmentSearchMovieBinding
import com.example.tmdb.models.moviesearch.MovieSearch
import com.example.tmdb.viewmodels.MovieAndTvSeriesSearchViewModel
import java.util.*
import kotlin.collections.ArrayList


class SearchMovieFragment : Fragment() {

    private lateinit var binding: FragmentSearchMovieBinding
    private lateinit var movieAndTvSeriesSearchViewModel: MovieAndTvSeriesSearchViewModel
    private lateinit var recyclerViewMovieSearchResultsAdapter: RecyclerViewMovieSearchResultsAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private var arrayListSearchMovieResults:ArrayList<MovieSearch> = arrayListOf()
    private var pageMovieSearch:Int = 1
    private var totalPagesMovieSearch:Int = 0
    private lateinit var string:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAndTvSeriesSearchViewModel = ViewModelProvider(this)[MovieAndTvSeriesSearchViewModel::class.java]
        // Setting recycler view adapter and layout for list search movie results
        recyclerViewMovieSearchResultsAdapter = RecyclerViewMovieSearchResultsAdapter(requireContext(),arrayListSearchMovieResults)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewMovieSearchResultsAdapter.setHasStableIds(true)
        binding.recyclerViewResultsMovie.adapter = recyclerViewMovieSearchResultsAdapter
        binding.recyclerViewResultsMovie.layoutManager = staggeredGridLayoutManager
        binding.recyclerViewResultsMovie.setHasFixedSize(true)
        binding.recyclerViewResultsMovie.itemAnimator = null
        movieAndTvSeriesSearchViewModel.mutableMoviesSearchLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results !=null){
                arrayListSearchMovieResults.addAll(it.results!!)
                recyclerViewMovieSearchResultsAdapter.notifyDataSetChanged()
            }
            totalPagesMovieSearch = it?.totalPages!!
        })

        binding.editTextSearchMovie.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                if(s!= null){
                    binding.recyclerViewResultsMovie.visibility = View.VISIBLE
                    arrayListSearchMovieResults.clear()
                    recyclerViewMovieSearchResultsAdapter.notifyDataSetChanged()

                    Handler().postDelayed({
                        binding.progressBarLoadingSearchMovies.visibility = View.INVISIBLE
                    },2000)
                    binding.progressBarLoadingSearchMovies.visibility = View.VISIBLE
                    string = s.toString()
                    Handler().postDelayed({
                        pageMovieSearch = 1
                        movieAndTvSeriesSearchViewModel.getMoviesSearch("en-US",string, pageMovieSearch,false)
                        Log.d("Load search","$pageMovieSearch")
                    },2000)
                }
                if(s == null){
                    binding.recyclerViewResultsMovie.visibility = View.INVISIBLE
                }
            }
        })
        binding.recyclerViewResultsMovie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    if(pageMovieSearch < totalPagesMovieSearch){
                        Handler().postDelayed({
                            pageMovieSearch+=1
                            movieAndTvSeriesSearchViewModel.getMoviesSearch("en-US",string,pageMovieSearch,false)
                        },2000)
                    }else {
                        Toast.makeText(requireContext(),"All of Movies is displayed",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        binding.imageViewButtonChangePage.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentSearch, SearchTvSeriesFragment())
                .commit()
        }



        binding.imageViewBackButtonSearchMovies.setOnClickListener {
            activity?.finish()
        }
    }
}