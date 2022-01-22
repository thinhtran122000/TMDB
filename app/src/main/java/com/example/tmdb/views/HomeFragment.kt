package com.example.tmdb.views


import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.tmdb.R
import com.example.tmdb.adapters.trending.TrendingMoviesSlideShowAdapter
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.models.movies.TrendingMovie
import com.example.tmdb.viewmodels.MoviesViewModel
import kotlin.math.abs
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdb.adapters.popular.PopularMoviesSlideShowAdapter
import com.example.tmdb.adapters.upcoming.UpComingMoviesSlideShowAdapter
import com.example.tmdb.internet.InternetConnection
import com.example.tmdb.models.movies.PopularMovie
import com.example.tmdb.models.movies.UpComingMovie
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList


@DelicateCoroutinesApi
class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeBinding
    private lateinit var trendingMoviesSlideShowAdapter: TrendingMoviesSlideShowAdapter
    private lateinit var upComingMoviesSlideShowAdapter: UpComingMoviesSlideShowAdapter
    private lateinit var popularMoviesSlideShowAdapter: PopularMoviesSlideShowAdapter
    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var timer:Timer
    private var arrayListTrendingMovies:ArrayList<TrendingMovie> = arrayListOf()
    private var arrayListUpComingMovies: ArrayList<UpComingMovie> = arrayListOf()
    private var arrayListPopularMovies:ArrayList<PopularMovie> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorOrange = resources.getColor(R.color.orange)
        val colorWhite = resources.getColor(R.color.white)
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        binding.textViewStreamEverywhere.text =
            Html.fromHtml("<font color=$colorOrange>Stream</font> " +
                    "<font color=$colorWhite>Everywhere</font>")
        // Setting adapter
        trendingMoviesSlideShowAdapter = TrendingMoviesSlideShowAdapter(requireContext(),arrayListTrendingMovies)
        upComingMoviesSlideShowAdapter = UpComingMoviesSlideShowAdapter(requireContext(),arrayListUpComingMovies)
        popularMoviesSlideShowAdapter = PopularMoviesSlideShowAdapter(requireContext(),arrayListPopularMovies)
        // Set up adapter and properties of view pager 2 (slide show up coming movies)
        binding.slideShowUpComingMovies.adapter = upComingMoviesSlideShowAdapter
        binding.slideShowUpComingMovies.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // Set up adapter and properties of view pager 2 (slide show popular movies)
        binding.slideShowPopularMovies.adapter = popularMoviesSlideShowAdapter
        binding.slideShowPopularMovies.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.circleIndicatorsPM.setViewPager(binding.slideShowPopularMovies)
        popularMoviesSlideShowAdapter.registerAdapterDataObserver(binding.circleIndicatorsPM.adapterDataObserver)
        // Set up adapter and properties of view pager 2 (slide show trending movies)
        binding.slideShowTrendingMovies.adapter = trendingMoviesSlideShowAdapter
        binding.slideShowTrendingMovies.clipToPadding = false
        binding.slideShowTrendingMovies.clipChildren = false
        binding.slideShowTrendingMovies.offscreenPageLimit = 3
        binding.slideShowTrendingMovies.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(50))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.16f
        }
        binding.slideShowTrendingMovies.setPageTransformer(compositePageTransformer)


        moviesViewModel.mutableTrendingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results?.isNotEmpty()==true){
                arrayListTrendingMovies.clear()
                arrayListTrendingMovies.addAll(it.results!!)
                trendingMoviesSlideShowAdapter.notifyDataSetChanged()
            }
        })

        moviesViewModel.mutableUpComingMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListUpComingMovies.addAll(it.results!!)
                upComingMoviesSlideShowAdapter.notifyDataSetChanged()
            }
        })
        moviesViewModel.mutablePopularMoviesLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListPopularMovies.addAll(it.results!!)
                popularMoviesSlideShowAdapter.notifyDataSetChanged()
            }
        })

        binding.buttonLeft.setOnClickListener {
            binding.slideShowUpComingMovies.setCurrentItem(binding.slideShowUpComingMovies.currentItem - 1, true)
        }
        binding.buttonRight.setOnClickListener {
            binding.slideShowUpComingMovies.setCurrentItem(binding.slideShowUpComingMovies.currentItem + 1, true)
        }
        // Call api
        moviesViewModel.getTrendingMovies("movie","day")
        moviesViewModel.getUpComingMovies("en-US",1)
        moviesViewModel.getPopularMovies("en-US",1)
        autoSlidePopularMovie()
    }
    private fun autoSlidePopularMovie(){
        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    var currentItem = binding.slideShowPopularMovies.currentItem
                    val totalItem = arrayListPopularMovies.size - 1
                    if(currentItem < totalItem){
                        currentItem++
                        binding.slideShowPopularMovies.setCurrentItem(currentItem,true)
                    }
                    else{
                        binding.slideShowPopularMovies.setCurrentItem(0,true)
                    }
                }
            }
        },2000,3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}


//        binding.slideShowTrendingMovies.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//
////                val currentPage: Int = binding.viewPager2.currentItem
////                if (currentPage == arrayListTrendingMovies.size-1 || currentPage == 0) {
////                    previousState = currentState
////                    currentState = state
////                    if (previousState == 1 && currentState == 0) {
////                        binding.viewPager2.currentItem = if (currentPage == 0) arrayListTrendingMovies.size-1 else 0
////                    }
////                }
//            }
//        })