package com.example.tmdb.adapters.nowplaying

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemNowPlayingMovieGenreLayoutBinding
import com.example.tmdb.models.movies.MovieGenres

class RecyclerViewNowPlayingMovieGenresAdapter(private var context: Context,
                                               private var arrayListNowPlayingMovieGenre:ArrayList<MovieGenres>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NowPlayingMovieGenresVH(ItemNowPlayingMovieGenreLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NowPlayingMovieGenresVH){
            var movieGenres:MovieGenres = arrayListNowPlayingMovieGenre!![position]
            if(movieGenres.name != null){
                holder.itemNowPlayingMovieGenreLayoutBinding.textViewGenreNPM.text = movieGenres.name
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListNowPlayingMovieGenre?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class NowPlayingMovieGenresVH(var itemNowPlayingMovieGenreLayoutBinding: ItemNowPlayingMovieGenreLayoutBinding):
        RecyclerView.ViewHolder(itemNowPlayingMovieGenreLayoutBinding.root)
}