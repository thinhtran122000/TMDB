package com.example.tmdb.adapters.trending

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
import com.example.tmdb.databinding.ItemTrendingMovieRelatedLayoutBinding
import com.example.tmdb.models.movies.RelatedMovie
import com.example.tmdb.utils.Credentials
import java.time.LocalDate
import java.time.format.DateTimeParseException

class RecyclerViewTrendingMovieRelatedAdapter(private var context: Context,
                                              private var arrayListRelatedTrendingMovies:ArrayList<RelatedMovie>?):
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RelatedTrendingMoviesVH(ItemTrendingMovieRelatedLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorTitle = context.resources.getColor(R.color.white)
        val colorYear = context.resources.getColor(R.color.gray_100)
        if(holder is RelatedTrendingMoviesVH){
            var relatedMovie:RelatedMovie = arrayListRelatedTrendingMovies!![position]
            try {
                val relatedTrendingMoviesReleaseDate = LocalDate.parse(relatedMovie.releaseDate)
                val relatedTrendingMoviesReleaseYear = relatedTrendingMoviesReleaseDate.year
                val relatedTrendingMovieTitle = relatedMovie.title
                holder.itemTrendingMovieRelatedLayoutBinding.textViewTitleRTM.text =
                    Html.fromHtml("<font color=$colorTitle>$relatedTrendingMovieTitle</font>" +
                            " <font color=$colorYear>($relatedTrendingMoviesReleaseYear)</font>")
            }catch (e:DateTimeParseException){
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
            if(relatedMovie.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTrendingMovieRelatedLayoutBinding.imageViewPosterRTM)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + relatedMovie.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemTrendingMovieRelatedLayoutBinding.imageViewPosterRTM)
            }
            holder.setIsRecyclable(false)
        }
    }

    override fun getItemCount(): Int {
        return arrayListRelatedTrendingMovies?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class RelatedTrendingMoviesVH(var itemTrendingMovieRelatedLayoutBinding: ItemTrendingMovieRelatedLayoutBinding)
        :RecyclerView.ViewHolder(itemTrendingMovieRelatedLayoutBinding.root)
}