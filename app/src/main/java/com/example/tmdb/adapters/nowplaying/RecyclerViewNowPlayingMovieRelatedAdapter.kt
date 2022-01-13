package com.example.tmdb.adapters.nowplaying

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemNowPlayingMovieRelatedLayoutBinding
import com.example.tmdb.models.movies.RelatedMovie
import com.example.tmdb.utils.Credentials
import java.time.LocalDate
import java.time.format.DateTimeParseException

class RecyclerViewNowPlayingMovieRelatedAdapter(private var context: Context,
                                                private var arrayListRelatedNowPlayingMovies:ArrayList<RelatedMovie>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RelatedNowPlayingMoviesVH(ItemNowPlayingMovieRelatedLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorTitle = context.resources.getColor(R.color.white)
        val colorYear = context.resources.getColor(R.color.gray_100)
        if(holder is RelatedNowPlayingMoviesVH){
            var relatedMovie:RelatedMovie = arrayListRelatedNowPlayingMovies!![position]
            try {
                val relatedNowPLayingMoviesReleaseDate = LocalDate.parse(relatedMovie.releaseDate)
                val relatedNowPLayingMoviesReleaseYear = relatedNowPLayingMoviesReleaseDate.year
                val relatedNowPLayingMovieTitle = relatedMovie.title
                holder.itemNowPlayingMovieRelatedLayoutBinding.textViewTitleRNPM.text =
                    Html.fromHtml("<font color=$colorTitle>$relatedNowPLayingMovieTitle</font>" +
                            " <font color=$colorYear>($relatedNowPLayingMoviesReleaseYear)</font>")
            }catch (e: DateTimeParseException){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            if(relatedMovie.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingMovieRelatedLayoutBinding.imageViewPosterRNPM)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + relatedMovie.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingMovieRelatedLayoutBinding.imageViewPosterRNPM)
            }
            holder.setIsRecyclable(false)
        }
    }

    override fun getItemCount(): Int {
        return arrayListRelatedNowPlayingMovies?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class RelatedNowPlayingMoviesVH(var itemNowPlayingMovieRelatedLayoutBinding: ItemNowPlayingMovieRelatedLayoutBinding):
        RecyclerView.ViewHolder(itemNowPlayingMovieRelatedLayoutBinding.root)
}