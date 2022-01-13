package com.example.tmdb.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.adapters.trending.MovieSliderAdapter
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.models.movies.TrendingMovie
import com.example.tmdb.viewmodels.MovieViewModel
import kotlin.math.abs






class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieSliderAdapter: MovieSliderAdapter
    private lateinit var movieViewModel:MovieViewModel
    private var arrayListTrendingMovies:ArrayList<TrendingMovie> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val colorOrange = resources.getColor(R.color.orange)
        val colorWhite = resources.getColor(R.color.white)
        binding.textViewStream.text = Html.fromHtml("<font color=$colorWhite>Stream</font> <font color=$colorOrange>Everywhere</font>")
        Glide.with(this)
            .load(R.drawable.eternal)
            .override(327,191)
            .centerCrop()
            .into(binding.imageViewPoster)
        // Call adapter
        movieSliderAdapter = MovieSliderAdapter(requireContext(),arrayListTrendingMovies)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.mutableTrendingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results !=null){
                arrayListTrendingMovies.clear()
                this.arrayListTrendingMovies.addAll(it.results!!)
            }
            movieSliderAdapter.notifyDataSetChanged()
        })
        // Call api
        movieViewModel.getTrendingMovies("movie","day")
        // Set up adapter and properties of view pager 2 (slider)
        binding.viewPager2.adapter = movieSliderAdapter
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(60))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.16f
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)
    }




}