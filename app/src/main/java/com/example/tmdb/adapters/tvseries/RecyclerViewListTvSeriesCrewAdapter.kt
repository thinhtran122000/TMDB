package com.example.tmdb.adapters.tvseries

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.ListTvSeriesCrewDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemListTvSeriesCrewLayoutBinding
import com.example.tmdb.models.tvseriescredits.TvSeriesCrew
import com.example.tmdb.utils.Credentials

class RecyclerViewListTvSeriesCrewAdapter(private var context: Context,
                                          private var arrayListTvSeriesCrew: ArrayList<TvSeriesCrew>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListTvSeriesCrewVH(
            ItemListTvSeriesCrewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ListTvSeriesCrewVH){
            var tvSeriesCrew:TvSeriesCrew = arrayListTvSeriesCrew!![position]
            holder.itemListTvSeriesCrewLayoutBinding.textViewListCrewNameTS.text = tvSeriesCrew.name
            if(tvSeriesCrew.profilePath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CREW + tvSeriesCrew.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemListTvSeriesCrewLayoutBinding.imageViewListCrewTS)
            }else{
                if(tvSeriesCrew.gender == 2){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTvSeriesCrewLayoutBinding.imageViewListCrewTS)
                }
                if(tvSeriesCrew.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTvSeriesCrewLayoutBinding.imageViewListCrewTS)
                }
                if (tvSeriesCrew.gender == 0 || tvSeriesCrew.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTvSeriesCrewLayoutBinding.imageViewListCrewTS)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ListTvSeriesCrewDetailsActivity::class.java)
                intent.putExtra("Tv series list crew name",tvSeriesCrew.name)
                intent.putExtra("Tv series list crew gender",tvSeriesCrew.gender)
                intent.putExtra("Tv series list crew job",tvSeriesCrew.job)
                intent.putExtra("Tv series list crew known for department",tvSeriesCrew.knownForDepartment)
                intent.putExtra("Tv series list crew profile path",tvSeriesCrew.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTvSeriesCrew?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class ListTvSeriesCrewVH(var itemListTvSeriesCrewLayoutBinding: ItemListTvSeriesCrewLayoutBinding):
        RecyclerView.ViewHolder(itemListTvSeriesCrewLayoutBinding.root)
}