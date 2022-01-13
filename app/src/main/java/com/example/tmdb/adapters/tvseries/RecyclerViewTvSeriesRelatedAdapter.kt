package com.example.tmdb.adapters.tvseries

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemTvSeriesRelatedLayoutBinding
import com.example.tmdb.models.tvseries.RelatedTvSeries
import com.example.tmdb.utils.Credentials
import java.time.LocalDate
import java.time.format.DateTimeParseException

class RecyclerViewTvSeriesRelatedAdapter(private var context: Context,
                                         private var arrayListRelatedTvSeries:ArrayList<RelatedTvSeries>?):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RelatedTvSeriesVH(ItemTvSeriesRelatedLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorTitle = context.resources.getColor(R.color.white)
        val colorYear = context.resources.getColor(R.color.gray_100)
        if(holder is RelatedTvSeriesVH){
            var relatedTvSeries: RelatedTvSeries = arrayListRelatedTvSeries!![position]
            try {
                val relatedTvSeriesFirstAirDate = LocalDate.parse(relatedTvSeries.firstAirDate)
                val relatedTvSeriesFirstAirYear = relatedTvSeriesFirstAirDate.year
                val relatedTvSeriesName = relatedTvSeries.name
                holder.itemTvSeriesRelatedLayoutBinding.textViewNameRTS.text =
                    Html.fromHtml("<font color=$colorTitle>$relatedTvSeriesName</font>" +
                            " <font color=$colorYear>($relatedTvSeriesFirstAirYear)</font>")
            }catch (e: DateTimeParseException){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            if(relatedTvSeries.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTvSeriesRelatedLayoutBinding.imageViewPosterRTS)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + relatedTvSeries.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTvSeriesRelatedLayoutBinding.imageViewPosterRTS)
            }
            holder.setIsRecyclable(false)
        }
    }

    override fun getItemCount(): Int {
        return arrayListRelatedTvSeries?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class RelatedTvSeriesVH(var itemTvSeriesRelatedLayoutBinding: ItemTvSeriesRelatedLayoutBinding):
        RecyclerView.ViewHolder(itemTvSeriesRelatedLayoutBinding.root)
}