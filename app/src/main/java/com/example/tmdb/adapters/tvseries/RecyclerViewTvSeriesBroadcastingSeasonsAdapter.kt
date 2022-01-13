package com.example.tmdb.adapters.tvseries

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.views.TvSeriesBroadcastingSeasonsDetailsActivity
import com.example.tmdb.databinding.ItemTvSeriesBroadcastingSeasonLayoutBinding
import com.example.tmdb.models.tvseries.TvSeriesSeasons
import com.example.tmdb.utils.Credentials
import java.time.LocalDate
import java.time.format.DateTimeParseException

class RecyclerViewTvSeriesBroadcastingSeasonsAdapter(private var context: Context,
                                                     private var arrayListTvSeriesSeasons:ArrayList<TvSeriesSeasons>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TvSeriesSeasonsVH(ItemTvSeriesBroadcastingSeasonLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }
    @SuppressLint("NewApi","SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorNameSeasons = context.resources.getColor(R.color.white)
        val colorYearSeasons = context.resources.getColor(R.color.gray_100)
        if(holder is TvSeriesSeasonsVH){
            var tvSeriesSeasons:TvSeriesSeasons = arrayListTvSeriesSeasons!![position]
            try {
                if(tvSeriesSeasons.airDate!=null){
                    val tvSeriesSeasonsAirDate = LocalDate.parse(tvSeriesSeasons.airDate)
                    val tvSeriesSeasonsAirYear = tvSeriesSeasonsAirDate.year
                    val tvSeriesSeasonsName = tvSeriesSeasons.name
                    holder.itemTvSeriesBroadcastingSeasonLayoutBinding.textViewNameSeasonTS.text = Html
                        .fromHtml("<font color=$colorNameSeasons>$tvSeriesSeasonsName</font>" +
                                " <font color=$colorYearSeasons>($tvSeriesSeasonsAirYear)</font>")
                }else{
                    val tvSeriesSeasonsName = tvSeriesSeasons.name
                    holder.itemTvSeriesBroadcastingSeasonLayoutBinding.textViewNameSeasonTS.text = Html
                        .fromHtml("<font color=$colorNameSeasons>$tvSeriesSeasonsName</font>" +
                                " <font color=$colorYearSeasons>(Unknown)</font>")
                }
            }catch (e:DateTimeParseException){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            if(tvSeriesSeasons.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(70))
                    .into(holder.itemTvSeriesBroadcastingSeasonLayoutBinding.imageViewPosterSeasonTS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + tvSeriesSeasons.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(70))
                    .into(holder.itemTvSeriesBroadcastingSeasonLayoutBinding.imageViewPosterSeasonTS)
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context,
                    TvSeriesBroadcastingSeasonsDetailsActivity::class.java)
                intent.putExtra("Tv series broadcasting season air date",tvSeriesSeasons.airDate)
                intent.putExtra("Tv series broadcasting season episode count",tvSeriesSeasons.episodeCount.toString())
                intent.putExtra("Tv series broadcasting season name",tvSeriesSeasons.name)
                intent.putExtra("Tv series broadcasting season overview",tvSeriesSeasons.overview)
                intent.putExtra("Tv series broadcasting season poster path",tvSeriesSeasons.posterPath)
//                intent.putExtra("Tv series broadcasting season season number",tvSeriesSeasons.seasonNumber.toString())
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTvSeriesSeasons?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class TvSeriesSeasonsVH(var itemTvSeriesBroadcastingSeasonLayoutBinding: ItemTvSeriesBroadcastingSeasonLayoutBinding):
        RecyclerView.ViewHolder(itemTvSeriesBroadcastingSeasonLayoutBinding.root)
}