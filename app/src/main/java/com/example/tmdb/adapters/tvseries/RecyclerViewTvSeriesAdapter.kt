package com.example.tmdb.adapters.tvseries

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
import com.example.tmdb.views.TvSeriesDetailsActivity
import com.example.tmdb.databinding.ItemLoadingPopularTvSeriesLayoutBinding
import com.example.tmdb.databinding.ItemMorePopularTvSeriesLayoutBinding
import com.example.tmdb.databinding.ItemPopularTvSeriesLayoutBinding
import com.example.tmdb.models.tvseries.TvSeries
import com.example.tmdb.utils.Credentials

class RecyclerViewTvSeriesAdapter(private var context: Context,
                                  private var arrayListTvSeries: ArrayList<TvSeries>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    companion object {
        const val VIEW_TYPE_LOADING_POPULAR_TV_SERIES = 0
        const val VIEW_TYPE_MORE_POPULAR_TV_SERIES = 1
        const val VIEW_TYPE_POPULAR_TV_SERIES = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_LOADING_POPULAR_TV_SERIES){
            LoadingPopularTvSeriesVH(ItemLoadingPopularTvSeriesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }else{
            if(viewType == VIEW_TYPE_MORE_POPULAR_TV_SERIES){
                MorePopularTvSeriesVH(ItemMorePopularTvSeriesLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                )
            }else{
                PopularTvSeriesVH(ItemPopularTvSeriesLayoutBinding.inflate(
                    LayoutInflater.from(parent.context)
                    ,parent,
                    false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MorePopularTvSeriesVH){
            var tvSeries: TvSeries = arrayListTvSeries!![position]
            holder.itemMorePopularTvSeriesLayoutBinding.textViewTitleMPTS.text = tvSeries.name
            if(tvSeries.posterPath==null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMorePopularTvSeriesLayoutBinding.imageViewPosterMPTS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + tvSeries.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemMorePopularTvSeriesLayoutBinding.imageViewPosterMPTS)
            }
            holder.setIsRecyclable(false)
            holder.itemMorePopularTvSeriesLayoutBinding.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, TvSeriesDetailsActivity::class.java)
                intent.putExtra("Tv series id",tvSeries.id)
                intent.putExtra("Tv series vote average",tvSeries.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if (holder is PopularTvSeriesVH){
           var tvSeries: TvSeries = arrayListTvSeries!![position]
            holder.itemPopularTvSeriesLayoutBinding.textViewTitlePTS.text = tvSeries.name
            if(tvSeries.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularTvSeriesLayoutBinding.imageViewPosterPTS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + tvSeries.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemPopularTvSeriesLayoutBinding.imageViewPosterPTS)
            }
            holder.setIsRecyclable(false)
            holder.itemPopularTvSeriesLayoutBinding.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, TvSeriesDetailsActivity::class.java)
                intent.putExtra("Tv series id",tvSeries.id)
                intent.putExtra("Tv series vote average",tvSeries.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if(holder is LoadingPopularTvSeriesVH){
            val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            Handler().postDelayed({
                holder.itemLoadingPopularTvSeriesLayoutBinding.progressBarLoadingTvSeries.visibility =
                    View.INVISIBLE
            },2000)
            holder.itemLoadingPopularTvSeriesLayoutBinding.progressBarLoadingTvSeries.visibility =
                View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return arrayListTvSeries?.size!! + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == arrayListTvSeries!!.size){
            VIEW_TYPE_LOADING_POPULAR_TV_SERIES
        }else{
            if(arrayListTvSeries!![position].voteAverage!! > 4.0){
                VIEW_TYPE_MORE_POPULAR_TV_SERIES
            }else{
                VIEW_TYPE_POPULAR_TV_SERIES
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class MorePopularTvSeriesVH(var itemMorePopularTvSeriesLayoutBinding: ItemMorePopularTvSeriesLayoutBinding):
        RecyclerView.ViewHolder(itemMorePopularTvSeriesLayoutBinding.root)
    class PopularTvSeriesVH(var itemPopularTvSeriesLayoutBinding: ItemPopularTvSeriesLayoutBinding):
        RecyclerView.ViewHolder(itemPopularTvSeriesLayoutBinding.root)
    class LoadingPopularTvSeriesVH(var itemLoadingPopularTvSeriesLayoutBinding: ItemLoadingPopularTvSeriesLayoutBinding):
        RecyclerView.ViewHolder(itemLoadingPopularTvSeriesLayoutBinding.root)

}