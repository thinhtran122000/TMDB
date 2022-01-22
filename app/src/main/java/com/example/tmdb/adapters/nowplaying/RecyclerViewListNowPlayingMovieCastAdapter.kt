package com.example.tmdb.adapters.nowplaying

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.NowPLayingMovieCastDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemListNowPlayingMovieCastLayoutBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.utils.Credentials

class RecyclerViewListNowPlayingMovieCastAdapter(private var context: Context,
                                                 private var arrayListNowPlayingMovieCast: ArrayList<MovieCast>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListNowPlayingMovieCastVH(
            ItemListNowPlayingMovieCastLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListNowPlayingMovieCastVH) {
            var movieCast: MovieCast = arrayListNowPlayingMovieCast!![position]
            holder.itemListNowPlayingMovieCastLayoutBinding.textViewListCastNameNPM.text =
                movieCast.name
            if (movieCast.profilePath?.isNotEmpty() == true) {
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CAST + movieCast.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemListNowPlayingMovieCastLayoutBinding.imageViewListCastNPM)
            } else {
                if(movieCast.gender == 2){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCastLayoutBinding.imageViewListCastNPM)
                }
                if(movieCast.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCastLayoutBinding.imageViewListCastNPM)
                }
                if(movieCast.gender == 0 || movieCast.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCastLayoutBinding.imageViewListCastNPM)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, NowPLayingMovieCastDetailsActivity::class.java)
                intent.putExtra("Now playing movie list cast name",movieCast.name)
                intent.putExtra("Now playing movie list cast gender",movieCast.gender.toString())
                intent.putExtra("Now playing movie list cast character",movieCast.character)
                intent.putExtra("Now playing movie list cast known for department",movieCast.knownForDepartment)
                intent.putExtra("Now playing movie list cast profile path",movieCast.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListNowPlayingMovieCast?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class ListNowPlayingMovieCastVH(var itemListNowPlayingMovieCastLayoutBinding: ItemListNowPlayingMovieCastLayoutBinding):
        RecyclerView.ViewHolder(itemListNowPlayingMovieCastLayoutBinding.root)
}