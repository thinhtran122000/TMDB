package com.example.tmdb.adapters.searching

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemMorePopularTvSeriesSearchLayoutBinding
import com.example.tmdb.databinding.ItemPopularTvSeriesSearchLayoutBinding
import com.example.tmdb.models.tvseriessearch.TvSeriesSearch
import com.example.tmdb.utils.Credentials

class RecyclerViewTvSeriesSearchResultsAdapter(private var context: Context,
                                               private var arrayListSearchTvSeriesResults:ArrayList<TvSeriesSearch>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        //        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH = 1
        const val VIEW_TYPE_POPULAR_TV_SERIES_SEARCH = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH){
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
        }
    }

    override fun getItemCount(): Int {
        return arrayListSearchTvSeriesResults?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if(arrayListSearchTvSeriesResults!![position].voteAverage!! > 4.0){
            VIEW_TYPE_MORE_POPULAR_TV_SERIES_SEARCH
        }else{
            VIEW_TYPE_POPULAR_TV_SERIES_SEARCH
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class PopularTvSeriesSearchVH(var itemPopularTvSeriesSearchLayoutBinding: ItemPopularTvSeriesSearchLayoutBinding):
        RecyclerView.ViewHolder(itemPopularTvSeriesSearchLayoutBinding.root)
    class MorePopularTvSeriesSearchVH(var itemMorePopularTvSeriesSearchLayoutBinding: ItemMorePopularTvSeriesSearchLayoutBinding):
        RecyclerView.ViewHolder(itemMorePopularTvSeriesSearchLayoutBinding.root)
}