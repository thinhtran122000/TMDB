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
import com.example.tmdb.databinding.ItemLoadingSearchTvSeriesLayoutBinding
import com.example.tmdb.databinding.ItemMorePopularTvSeriesSearchLayoutBinding
import com.example.tmdb.databinding.ItemPopularTvSeriesSearchLayoutBinding
import com.example.tmdb.models.tvseriessearch.TvSeriesSearch
import com.example.tmdb.utils.Credentials
import com.example.tmdb.views.NowPlayingMovieDetailsActivity
import com.example.tmdb.views.TvSeriesDetailsActivity

class RecyclerViewTvSeriesSearchResultsAdapter(private var context: Context,
                                               private var arrayListSearchTvSeriesResults:ArrayList<TvSeriesSearch>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val VIEW_TYPE_LOADING_SEARCH_TV_SERIES = 0
        const val VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH = 1
        const val VIEW_TYPE_POPULAR_TV_SERIES_SEARCH = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_LOADING_SEARCH_TV_SERIES){
            LoadingSearchTvSeriesVH(ItemLoadingSearchTvSeriesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }else{
            if(viewType==VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH){
                MorePopularTvSeriesSearchVH(ItemMorePopularTvSeriesSearchLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            }else{
                PopularTvSeriesSearchVH(ItemPopularTvSeriesSearchLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MorePopularTvSeriesSearchVH) {
            var tvSeriesSearch: TvSeriesSearch = arrayListSearchTvSeriesResults!![position]
            holder.itemMorePopularTvSeriesSearchLayoutBinding.textViewTitleMPTSS.text = tvSeriesSearch.name
            if (tvSeriesSearch.posterPath == null) {
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMorePopularTvSeriesSearchLayoutBinding.imageViewPosterMPTSS)
            } else {
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + tvSeriesSearch.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMorePopularTvSeriesSearchLayoutBinding.imageViewPosterMPTSS)
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, TvSeriesDetailsActivity::class.java)
                intent.putExtra("Tv series id",tvSeriesSearch.id)
                intent.putExtra("Tv series vote average",tvSeriesSearch.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if (holder is PopularTvSeriesSearchVH){
            var tvSeriesSearch: TvSeriesSearch = arrayListSearchTvSeriesResults!![position]
            holder.itemPopularTvSeriesSearchLayoutBinding.textViewTitlePTSS.text = tvSeriesSearch.name
            if(tvSeriesSearch.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularTvSeriesSearchLayoutBinding.imageViewPosterPTSS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + tvSeriesSearch.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularTvSeriesSearchLayoutBinding.imageViewPosterPTSS)
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, TvSeriesDetailsActivity::class.java)
                intent.putExtra("Tv series id",tvSeriesSearch.id)
                intent.putExtra("Tv series vote average",tvSeriesSearch.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if(holder is LoadingSearchTvSeriesVH){
            val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            holder.itemLoadingSearchTvSeriesLayoutBinding.progressBarLoadingTvSeriesSearch.visibility =
                View.VISIBLE
            Handler().postDelayed({
                holder.itemLoadingSearchTvSeriesLayoutBinding.progressBarLoadingTvSeriesSearch.visibility =
                    View.INVISIBLE
            },2000)
        }
    }

    override fun getItemCount(): Int {
        return arrayListSearchTvSeriesResults?.size!!+1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == arrayListSearchTvSeriesResults!!.size){
            VIEW_TYPE_LOADING_SEARCH_TV_SERIES
        }else{
            if(arrayListSearchTvSeriesResults!![position].voteAverage!! > 4.0){
                VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH
            }else{
                VIEW_TYPE_POPULAR_TV_SERIES_SEARCH
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class PopularTvSeriesSearchVH(var itemPopularTvSeriesSearchLayoutBinding: ItemPopularTvSeriesSearchLayoutBinding):
        RecyclerView.ViewHolder(itemPopularTvSeriesSearchLayoutBinding.root)
    class MorePopularTvSeriesSearchVH(var itemMorePopularTvSeriesSearchLayoutBinding: ItemMorePopularTvSeriesSearchLayoutBinding):
        RecyclerView.ViewHolder(itemMorePopularTvSeriesSearchLayoutBinding.root)
    class LoadingSearchTvSeriesVH(var itemLoadingSearchTvSeriesLayoutBinding: ItemLoadingSearchTvSeriesLayoutBinding):
        RecyclerView.ViewHolder(itemLoadingSearchTvSeriesLayoutBinding.root)
}