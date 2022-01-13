package com.example.tmdb.adapters.trending

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemTrendingMovieGenreLayoutBinding
import com.example.tmdb.models.movies.MovieGenres


class RecyclerViewTrendingMovieGenresAdapter(private var context: Context,
                                             private var arrayListTrendingMovieGenre:ArrayList<MovieGenres>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendingMovieGenresVH(ItemTrendingMovieGenreLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TrendingMovieGenresVH){
            var movieGenres: MovieGenres = arrayListTrendingMovieGenre!![position]
            if(movieGenres.name != null){
                holder.itemTrendingMovieGenreLayoutBinding.textViewGenreTM.text = movieGenres.name
            }

//            val layoutParams: StaggeredGridLayoutManager.LayoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
//            layoutParams.isFullSpan = true
//            holder.setIsRecyclable(false)
        }
    }
    override fun getItemCount(): Int {
        return arrayListTrendingMovieGenre?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class TrendingMovieGenresVH(var itemTrendingMovieGenreLayoutBinding: ItemTrendingMovieGenreLayoutBinding):
        RecyclerView.ViewHolder(itemTrendingMovieGenreLayoutBinding.root)


}