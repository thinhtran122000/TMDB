package com.example.tmdb.adapters.nowplaying

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.ListNowPlayingMovieCrewDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemListNowPlayingMovieCrewLayoutBinding
import com.example.tmdb.models.moviecredits.MovieCrew
import com.example.tmdb.utils.Credentials

class RecyclerViewListNowPlayingMovieCrewAdapter(private var context: Context,
                                                 private var arrayListNowPlayingMovieCrew: ArrayList<MovieCrew>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListNowPlayingMovieCrewVH(
            ItemListNowPlayingMovieCrewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ListNowPlayingMovieCrewVH){
            var movieCrew:MovieCrew = arrayListNowPlayingMovieCrew!![position]
            holder.itemListNowPlayingMovieCrewLayoutBinding.textViewListCrewNameNPM.text = movieCrew.name
            if(movieCrew.profilePath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CREW + movieCrew.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemListNowPlayingMovieCrewLayoutBinding.imageViewListCrewNPM)
            }else{
                if(movieCrew.gender == 2){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCrewLayoutBinding.imageViewListCrewNPM)
                }
                if(movieCrew.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCrewLayoutBinding.imageViewListCrewNPM)
                }
                if (movieCrew.gender == 0 ||movieCrew.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListNowPlayingMovieCrewLayoutBinding.imageViewListCrewNPM)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ListNowPlayingMovieCrewDetailsActivity::class.java)
                intent.putExtra("Now playing list crew name",movieCrew.name)
                intent.putExtra("Now playing movie list crew gender",movieCrew.gender)
                intent.putExtra("Now playing movie list crew job",movieCrew.job)
                intent.putExtra("Now playing movie list crew known for department",movieCrew.knownForDepartment)
                intent.putExtra("Now playing movie list crew profile path",movieCrew.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListNowPlayingMovieCrew?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class ListNowPlayingMovieCrewVH(var itemListNowPlayingMovieCrewLayoutBinding: ItemListNowPlayingMovieCrewLayoutBinding):
        RecyclerView.ViewHolder(itemListNowPlayingMovieCrewLayoutBinding.root)
}