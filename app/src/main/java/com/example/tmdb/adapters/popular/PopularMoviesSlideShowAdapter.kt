package com.example.tmdb.adapters.popular

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemPopularMovieLayoutBinding
import com.example.tmdb.models.movies.PopularMovie
import com.example.tmdb.utils.Credentials
import com.example.tmdb.views.NowPlayingMovieDetailsActivity


class PopularMoviesSlideShowAdapter(private var context: Context,
                                    private var arrayListPopularMovies:ArrayList<PopularMovie>?)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularMovieVH(ItemPopularMovieLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PopularMovieVH){
            var popularMovie:PopularMovie = arrayListPopularMovies!![position]
            if(popularMovie.posterPath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + popularMovie.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.itemPopularMovieLayoutBinding.imageViewPosterPopularMovie)
            }else{
                Glide.with(holder.itemView.context)
                    .load(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.itemPopularMovieLayoutBinding.imageViewPosterPopularMovie)
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context,NowPlayingMovieDetailsActivity::class.java)
                intent.putExtra("Now playing movie id",popularMovie.id)
                intent.putExtra("Now playing movie vote average",popularMovie.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListPopularMovies?.size!!
    }
    class PopularMovieVH(var itemPopularMovieLayoutBinding: ItemPopularMovieLayoutBinding):
        RecyclerView.ViewHolder(itemPopularMovieLayoutBinding.root)
}