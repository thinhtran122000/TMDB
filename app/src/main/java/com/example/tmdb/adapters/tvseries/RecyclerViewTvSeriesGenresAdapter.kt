package com.example.tmdb.adapters.tvseries

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemTvSeriesGenreLayoutBinding
import com.example.tmdb.models.tvseries.TvSeriesGenres

class RecyclerViewTvSeriesGenresAdapter(private var context: Context,
                                        private var arrayListTvSeriesGenre:ArrayList<TvSeriesGenres>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TvSeriesGenresVH(ItemTvSeriesGenreLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TvSeriesGenresVH){
            var tvSeriesGenres:TvSeriesGenres = arrayListTvSeriesGenre!![position]
            if(tvSeriesGenres.name != null){
                holder.itemTvSeriesGenreLayoutBinding.textViewGenreTS.text = tvSeriesGenres.name
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTvSeriesGenre?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class TvSeriesGenresVH(var itemTvSeriesGenreLayoutBinding: ItemTvSeriesGenreLayoutBinding):
        RecyclerView.ViewHolder(itemTvSeriesGenreLayoutBinding.root)
}