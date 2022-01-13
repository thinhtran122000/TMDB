package com.example.tmdb.adapters.trending

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.TrendingMovieDetailsActivity
import com.example.tmdb.databinding.ItemTrendingMovieLayoutBinding
import com.example.tmdb.models.movies.TrendingMovie
import com.example.tmdb.utils.Credentials

class MovieSliderAdapter(private var context: Context,
                         private var arrayListTrendingMovies:ArrayList<TrendingMovie>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendingMovieVH(ItemTrendingMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TrendingMovieVH){
            var trendingMovie: TrendingMovie = arrayListTrendingMovies!![position]
            holder.itemTrendingMovieLayoutBinding
                .textViewTitleTrendingMovie.text = trendingMovie.title
            holder.itemTrendingMovieLayoutBinding
                .textViewVoteAverageTrendingMovie.text = trendingMovie.voteAverage.toString()
            Glide.with(holder.itemView.context)
                .load(Credentials.POSTER_PATH + trendingMovie.posterPath)
                .override(258,350)
                .transform(RoundedCorners(40))
                .into(holder.itemTrendingMovieLayoutBinding.imageViewTrendingMovie)
            holder.itemTrendingMovieLayoutBinding.root.setOnClickListener {
                val intent:Intent = Intent(holder.itemView.context, TrendingMovieDetailsActivity::class.java)
                intent.putExtra("Trending movie id",trendingMovie.id)
                intent.putExtra("Trending movie vote average",trendingMovie.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTrendingMovies?.size!!
    }
    class TrendingMovieVH(var itemTrendingMovieLayoutBinding:ItemTrendingMovieLayoutBinding) :
        RecyclerView.ViewHolder(itemTrendingMovieLayoutBinding.root)
}