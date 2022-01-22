package com.example.tmdb.adapters.upcoming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.ItemUpComingMovieLayoutBinding
import com.example.tmdb.models.movies.UpComingMovie
import com.example.tmdb.utils.Credentials
import com.example.tmdb.views.UpComingMovieFragment
import com.example.tmdb.R
import com.example.tmdb.views.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class UpComingMoviesSlideShowAdapter constructor(private var context: Context,
                                                 private var arrayListUpComingMovies: ArrayList<UpComingMovie>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var key:String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UpComingMoviesVH(ItemUpComingMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

//    @SuppressLint("CommitPrefEdits")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UpComingMoviesVH){
            var upComingMovie:UpComingMovie = arrayListUpComingMovies!![position]
            holder.itemUpComingMovieLayoutBinding.textViewTitleUpComingMovie.text = upComingMovie.title
            if(upComingMovie.posterPath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + upComingMovie.posterPath)
                    .into(holder.itemUpComingMovieLayoutBinding.imageViewPosterUpComingMovie)
            }else{
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .into(holder.itemUpComingMovieLayoutBinding.imageViewPosterUpComingMovie)
            }
            holder.itemUpComingMovieLayoutBinding.buttonPlayTrailerUpComingMovie.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("Up coming movie id", upComingMovie.id!!)
                bundle.putString("Up coming movie title",upComingMovie.title)
                bundle.putString("Up coming movie overview",upComingMovie.overview)
                bundle.putString("Up coming movie release date",upComingMovie.releaseDate)
                bundle.putDouble("Up coming movie vote average", upComingMovie.voteAverage!!)
                bundle.putString("Up coming movie backdrop path",upComingMovie.backdropPath)
                val dialogFragment = UpComingMovieFragment()
                dialogFragment.arguments = bundle
                dialogFragment.show((context as MainActivity).supportFragmentManager,"dialogCustom")
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListUpComingMovies?.size!!
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
    class UpComingMoviesVH(var itemUpComingMovieLayoutBinding: ItemUpComingMovieLayoutBinding):
        RecyclerView.ViewHolder(itemUpComingMovieLayoutBinding.root)
}