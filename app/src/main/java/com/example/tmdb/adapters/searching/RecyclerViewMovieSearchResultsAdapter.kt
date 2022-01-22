package com.example.tmdb.adapters.searching

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemLoadingSearchMovieLayoutBinding
import com.example.tmdb.databinding.ItemMovieSearchLayoutBinding
import com.example.tmdb.databinding.ItemPopularMovieSearchLayoutBinding
import com.example.tmdb.models.moviesearch.MovieSearch
import com.example.tmdb.utils.Credentials
import com.example.tmdb.views.NowPlayingMovieDetailsActivity

class RecyclerViewMovieSearchResultsAdapter(private var context: Context,
                                            private var arrayListSearchMovieResults:ArrayList<MovieSearch>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object{
        const val VIEW_TYPE_LOADING_SEARCH_MOVIE = 0
        const val VIEW_TYPE_POPULAR_MOVIE_SEARCH = 1
        const val VIEW_TYPE_MOVIE_SEARCH = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_LOADING_SEARCH_MOVIE){
            LoadingSearchMovieVH(ItemLoadingSearchMovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
        }else{
            if(viewType==VIEW_TYPE_POPULAR_MOVIE_SEARCH){
                PopularMovieSearchVH(ItemPopularMovieSearchLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                ))
            }else{
                MovieSearchVH(ItemMovieSearchLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                ))
            }
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
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, NowPlayingMovieDetailsActivity::class.java)
                intent.putExtra("Now playing movie id", movieSearch.id)
                intent.putExtra("Now playing movie vote average", movieSearch.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
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
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, NowPlayingMovieDetailsActivity::class.java)
                intent.putExtra("Now playing movie id",movieSearch.id)
                intent.putExtra("Now playing movie vote average",movieSearch.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if (holder is LoadingSearchMovieVH){
            val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            holder.itemLoadingSearchMovieLayoutBinding.progressBarLoadingMovieSearch.visibility =
                View.VISIBLE
            Handler().postDelayed({
                holder.itemLoadingSearchMovieLayoutBinding.progressBarLoadingMovieSearch.visibility =
                    View.INVISIBLE
            },2000)
        }
    }

    override fun getItemCount(): Int {
        return arrayListSearchMovieResults?.size!!+1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == arrayListSearchMovieResults!!.size){
            VIEW_TYPE_LOADING_SEARCH_MOVIE
        }else {
            if (arrayListSearchMovieResults!![position].voteAverage!! > 4.0) {
                VIEW_TYPE_POPULAR_MOVIE_SEARCH
            } else {
                VIEW_TYPE_MOVIE_SEARCH
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class MovieSearchVH(var itemMovieSearchLayoutBinding: ItemMovieSearchLayoutBinding):
        RecyclerView.ViewHolder(itemMovieSearchLayoutBinding.root)
    class PopularMovieSearchVH(var itemPopularMovieSearchLayoutBinding: ItemPopularMovieSearchLayoutBinding):
        RecyclerView.ViewHolder(itemPopularMovieSearchLayoutBinding.root)
    class LoadingSearchMovieVH(var itemLoadingSearchMovieLayoutBinding: ItemLoadingSearchMovieLayoutBinding):
        RecyclerView.ViewHolder(itemLoadingSearchMovieLayoutBinding.root)
}