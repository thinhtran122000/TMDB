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
import androidx.viewpager2.widget.ViewPager2
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlin.collections.ArrayList


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
            .transform(CenterCrop(),RoundedCorners(50))
            .placeholder(R.drawable.placeholder)
            .into(binding.imageViewPoster)
        // Call adapter
        movieSliderAdapter = MovieSliderAdapter(requireContext(),arrayListTrendingMovies)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.mutableTrendingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results !=null){
                arrayListTrendingMovies.clear()
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
                arrayListTrendingMovies.addAll(it.results!!)
            }
            movieSliderAdapter.notifyDataSetChanged()
        })
        // Call api
        movieViewModel.getTrendingMovies("movie","day")
        // Set up adapter and properties of view pager 2 (slider)
        binding.viewPager2.viewTreeObserver.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            binding.viewPager2.setCurrentItem(
                500,
                false
            )
        })
        binding.viewPager2.adapter = movieSliderAdapter
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

//                val currentPage: Int = binding.viewPager2.currentItem
//                if (currentPage == arrayListTrendingMovies.size-1 || currentPage == 0) {
//                    previousState = currentState
//                    currentState = state
//                    if (previousState == 1 && currentState == 0) {
//                        binding.viewPager2.currentItem = if (currentPage == 0) arrayListTrendingMovies.size-1 else 0
//                    }
//                }
            }
        })

        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(60))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.16f
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)
    }


    override fun onResume() {
        super.onResume()

    }

}