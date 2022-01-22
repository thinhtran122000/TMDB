package com.example.tmdb.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import com.example.tmdb.adapters.searching.RecyclerViewTvSeriesSearchResultsAdapter
import com.example.tmdb.databinding.FragmentSearchTvSeriesBinding
import com.example.tmdb.models.tvseriessearch.TvSeriesSearch
import com.example.tmdb.viewmodels.MovieAndTvSeriesSearchViewModel


class SearchTvSeriesFragment : Fragment() {
    private lateinit var binding: FragmentSearchTvSeriesBinding
    private lateinit var movieAndTvSeriesSearchViewModel: MovieAndTvSeriesSearchViewModel
    private lateinit var recyclerViewTvSeriesSearchResultsAdapter: RecyclerViewTvSeriesSearchResultsAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private var arrayListSearchTvSeriesResults:ArrayList<TvSeriesSearch> = arrayListOf()
    private var pageTvSeriesSearch:Int = 1
    private var totalPagesTvSeriesSearch:Int = 0
    private lateinit var string:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTvSeriesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAndTvSeriesSearchViewModel = ViewModelProvider(this)[MovieAndTvSeriesSearchViewModel::class.java]
        // Setting recycler view adapter and layout for list search tv series results
        recyclerViewTvSeriesSearchResultsAdapter = RecyclerViewTvSeriesSearchResultsAdapter(requireContext(),arrayListSearchTvSeriesResults)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        staggeredGridLayoutManager.reverseLayout = false
        recyclerViewTvSeriesSearchResultsAdapter.setHasStableIds(true)
        binding.recyclerViewResultsTvSeries.adapter = recyclerViewTvSeriesSearchResultsAdapter
        binding.recyclerViewResultsTvSeries.layoutManager = staggeredGridLayoutManager
        binding.recyclerViewResultsTvSeries.setHasFixedSize(true)
        binding.recyclerViewResultsTvSeries.itemAnimator = null
        movieAndTvSeriesSearchViewModel.mutableTvSeriesSearchLiveData.observe(requireActivity(), Observer {
            if(it?.results?.isNotEmpty() == true){
                arrayListSearchTvSeriesResults.addAll(it.results!!)
                recyclerViewTvSeriesSearchResultsAdapter.notifyDataSetChanged()
            }else{
                binding.textViewStatusSearchTvSeries.visibility = View.VISIBLE
                binding.textViewStatusSearchTvSeries.text = "Can not found your favorite tv series \n ʕ = ᴥ = ʔ"
            }
            totalPagesTvSeriesSearch = it?.totalPages!!
        })
        binding.editTextSearchTvSeries.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    binding.recyclerViewResultsTvSeries.visibility = View.VISIBLE
                    binding.textViewStatusSearchTvSeries.visibility = View.INVISIBLE
                    arrayListSearchTvSeriesResults.clear()
                    recyclerViewTvSeriesSearchResultsAdapter.notifyDataSetChanged()
                    string = s.toString()
                    Handler().postDelayed({
                        pageTvSeriesSearch = 1
                        movieAndTvSeriesSearchViewModel.getTvSeriesSearch("en-US",pageTvSeriesSearch,string,false)
                    },2000)
                }else{
                    binding.recyclerViewResultsTvSeries.visibility = View.INVISIBLE
                    binding.textViewStatusSearchTvSeries.visibility = View.VISIBLE
                }
            }
        })
        binding.recyclerViewResultsTvSeries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    if(pageTvSeriesSearch < totalPagesTvSeriesSearch){
                        Handler().postDelayed({
                            pageTvSeriesSearch+=1
                            movieAndTvSeriesSearchViewModel.getMoviesSearch("en-US",string,pageTvSeriesSearch,false)
                        },2000)
                    }else {
                        Toast.makeText(requireContext(),"All of Movies is displayed",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })




        binding.imageViewBackButtonSearchTvSeries.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentSearch, SearchMovieFragment())
                .commit()
        }

//        binding.recyclerViewResultsMovie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!recyclerView.canScrollVertically(1) && dy > 0) {
//                    if(pageMovieSearch < totalPagesMovieSearch){
//                        Handler().postDelayed({
//                            pageMovieSearch+=1
//                            movieAndTvSeriesSearchViewModel.getMoviesSearch("en-US",string,pageMovieSearch,false)
//                        },2000)
//                    }else {
//                        Toast.makeText(requireContext(),"All of Movies is displayed",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        })
    }


}