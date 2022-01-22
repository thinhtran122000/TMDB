package com.example.tmdb.adapters.tvseries

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.TvSeriesCastDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemTvSeriesCastLayoutBinding
import com.example.tmdb.models.tvseriescredits.TvSeriesCast
import com.example.tmdb.utils.Credentials

class RecyclerViewTvSeriesCastAdapter(private var context: Context,
                                      private var arrayListTvSeriesCast: ArrayList<TvSeriesCast>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TvSeriesCastVH(ItemTvSeriesCastLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TvSeriesCastVH){
            var tvSeriesCast: TvSeriesCast = arrayListTvSeriesCast!![position]
            holder.itemTvSeriesCastLayoutBinding.textViewCastNameTS.text = tvSeriesCast.name
            if(tvSeriesCast.profilePath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CAST + tvSeriesCast.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTvSeriesCastLayoutBinding.imageViewCastTS)
            }else{
                if(tvSeriesCast.gender == 2)
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemTvSeriesCastLayoutBinding.imageViewCastTS)
                if(tvSeriesCast.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemTvSeriesCastLayoutBinding.imageViewCastTS)
                }
                if (tvSeriesCast.gender == 0 || tvSeriesCast.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemTvSeriesCastLayoutBinding.imageViewCastTS)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, TvSeriesCastDetailsActivity::class.java)
                intent.putExtra("Tv series cast name",tvSeriesCast.name)
                intent.putExtra("Tv series cast gender",tvSeriesCast.gender.toString())
                intent.putExtra("Tv series cast character",tvSeriesCast.character)
                intent.putExtra("Tv series cast known for department",tvSeriesCast.knownForDepartment)
                intent.putExtra("Tv series cast profile path",tvSeriesCast.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTvSeriesCast?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class TvSeriesCastVH(var itemTvSeriesCastLayoutBinding: ItemTvSeriesCastLayoutBinding)
        :RecyclerView.ViewHolder(itemTvSeriesCastLayoutBinding.root)
}