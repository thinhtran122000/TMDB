package com.example.tmdb.adapters.searching

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemMovieSearchLayoutBinding
import com.example.tmdb.databinding.ItemPopularMovieSearchLayoutBinding
import com.example.tmdb.models.moviesearch.MovieSearch
import com.example.tmdb.utils.Credentials

class RecyclerViewMovieSearchResultsAdapter(private var context: Context,
                                            private var arrayListSearchMovieResults:ArrayList<MovieSearch>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object{
//        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_POPULAR_MOVIE_SEARCH = 1
        const val VIEW_TYPE_MOVIE_SEARCH = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==VIEW_TYPE_POPULAR_MOVIE_SEARCH){
            PopularMovieSearchVH(ItemPopularMovieSearchLayoutBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            ))
        }else{
            MovieSearchVH(ItemMovieSearchLayoutBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PopularMovieSearchVH){
            var movieSearch:MovieSearch = arrayListSearchMovieResults!![position]
            holder.itemPopularMovieSearchLayoutBinding.textViewTitlePMS.text = movieSearch.title
            if(movieSearch.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularMovieSearchLayoutBinding.imageViewPosterPMS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH+movieSearch.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularMovieSearchLayoutBinding.imageViewPosterPMS)
            }
            holder.setIsRecyclable(false)
        }
        if(holder is MovieSearchVH){
            var movieSearch:MovieSearch = arrayListSearchMovieResults!![position]
            holder.itemMovieSearchLayoutBinding.textViewTitleMS.text = movieSearch.title
            if(movieSearch.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMovieSearchLayoutBinding.imageViewPosterMS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH+movieSearch.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMovieSearchLayoutBinding.imageViewPosterMS)
            }
            holder.setIsRecyclable(false)
        }
    }

    override fun getItemCount(): Int {
        return arrayListSearchMovieResults?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if(arrayListSearchMovieResults!![position].voteAverage!! > 4.0){
            VIEW_TYPE_POPULAR_MOVIE_SEARCH
        }else{
            VIEW_TYPE_MOVIE_SEARCH
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class MovieSearchVH(var itemMovieSearchLayoutBinding: ItemMovieSearchLayoutBinding):
        RecyclerView.ViewHolder(itemMovieSearchLayoutBinding.root)
    class PopularMovieSearchVH(var itemPopularMovieSearchLayoutBinding: ItemPopularMovieSearchLayoutBinding):
        RecyclerView.ViewHolder(itemPopularMovieSearchLayoutBinding.root)
}