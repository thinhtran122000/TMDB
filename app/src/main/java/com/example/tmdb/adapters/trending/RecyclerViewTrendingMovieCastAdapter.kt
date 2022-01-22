package com.example.tmdb.adapters.trending

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.views.TrendingMovieCastDetailsActivity
import com.example.tmdb.databinding.ItemTrendingMovieCastLayoutBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.utils.Credentials

class RecyclerViewTrendingMovieCastAdapter(private var context: Context,
                                           private var arrayListTrendingMovieCast: ArrayList<MovieCast>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendingMovieCastVH(ItemTrendingMovieCastLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TrendingMovieCastVH){
            var movieCast:MovieCast = arrayListTrendingMovieCast!![position]
            holder.itemTrendingMovieCastLayoutBinding.textViewCastNameTM.text = movieCast.name
            if(movieCast.profilePath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CAST + movieCast.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTrendingMovieCastLayoutBinding.imageViewCastTM)
            }else{
                if(movieCast.gender == 2)
                Glide.with(holder.itemView.context)
                    .load(R.drawable.unknown_man_credits)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTrendingMovieCastLayoutBinding.imageViewCastTM)
                if(movieCast.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemTrendingMovieCastLayoutBinding.imageViewCastTM)
                }
                if (movieCast.gender == 0 || movieCast.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemTrendingMovieCastLayoutBinding.imageViewCastTM)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, TrendingMovieCastDetailsActivity::class.java)
                intent.putExtra("Trending movie cast name",movieCast.name)
                intent.putExtra("Trending movie cast gender",movieCast.gender.toString())
                intent.putExtra("Trending movie cast character",movieCast.character)
                intent.putExtra("Trending movie cast known for department",movieCast.knownForDepartment)
                intent.putExtra("Trending movie cast profile path",movieCast.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int {
        return arrayListTrendingMovieCast?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class TrendingMovieCastVH(var itemTrendingMovieCastLayoutBinding: ItemTrendingMovieCastLayoutBinding):
        RecyclerView.ViewHolder(itemTrendingMovieCastLayoutBinding.root)

}