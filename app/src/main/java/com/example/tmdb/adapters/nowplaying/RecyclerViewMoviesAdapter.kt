package com.example.tmdb.adapters.nowplaying

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.views.NowPlayingMovieDetailsActivity
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemLoadingNowPlayingMovieLayoutBinding
import com.example.tmdb.databinding.ItemNowPlayingMovieLayoutBinding
import com.example.tmdb.databinding.ItemNowPlayingPopularMovieLayoutBinding
import com.example.tmdb.models.movies.NowPlayingMovie
import com.example.tmdb.utils.Credentials
import java.time.LocalDate
import java.time.format.DateTimeParseException
import kotlin.collections.ArrayList


class RecyclerViewMoviesAdapter(private var context: Context,
                                private var arrayListNowPlayingMovies: ArrayList<NowPlayingMovie>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val VIEW_TYPE_LOADING_NOW_PLAYING_MOVIE = 0
        const val VIEW_TYPE_POPULAR_NOW_PLAYING_MOVIE = 1
        const val VIEW_TYPE_NOW_PLAYING_MOVIE = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== VIEW_TYPE_LOADING_NOW_PLAYING_MOVIE){
            LoadingNowPlayingMovieVH(ItemLoadingNowPlayingMovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            )
        }else{
            if(viewType == VIEW_TYPE_POPULAR_NOW_PLAYING_MOVIE){
                NowPlayingPopularMovieVH(ItemNowPlayingPopularMovieLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                )
            }else{
                NowPlayingMovieVH(ItemNowPlayingMovieLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                )
            }
        }

    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorTitle = context.resources.getColor(R.color.white)
        val colorYear = context.resources.getColor(R.color.gray_100)
        if(holder is NowPlayingPopularMovieVH){
            var nowPlayingMovies: NowPlayingMovie = arrayListNowPlayingMovies!![position]
            try {
                val nowPlayingPopularMoviesReleaseDate = LocalDate.parse(nowPlayingMovies.releaseDate)
                val nowPlayingPopularMoviesReleaseYear = nowPlayingPopularMoviesReleaseDate.year
                val nowPlayingPopularMovieTitle = nowPlayingMovies.title
                holder.itemNowPlayingPopularMovieLayoutBinding
                    .textViewTitleNPPM
                    .text = Html
                    .fromHtml("<font color=$colorTitle>$nowPlayingPopularMovieTitle</font>" +
                            " <font color=$colorYear>($nowPlayingPopularMoviesReleaseYear)</font>")
            }catch (e:DateTimeParseException){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
            if(nowPlayingMovies.posterPath == null){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingPopularMovieLayoutBinding.imageViewPosterNPPM)
            }else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + nowPlayingMovies.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingPopularMovieLayoutBinding.imageViewPosterNPPM)
            }

            holder.setIsRecyclable(false)
            holder.itemNowPlayingPopularMovieLayoutBinding.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, NowPlayingMovieDetailsActivity::class.java)
                intent.putExtra("Now playing movie id",nowPlayingMovies.id)
                intent.putExtra("Now playing movie vote average",nowPlayingMovies.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if(holder is NowPlayingMovieVH){
            var nowPlayingMovies: NowPlayingMovie = arrayListNowPlayingMovies!![position]
            try {
                val stringNowPlayingMoviesReleaseDate = LocalDate.parse(nowPlayingMovies.releaseDate)
                val stringNowPlayingMoviesReleaseYear = stringNowPlayingMoviesReleaseDate.year.toString()
                val nowPlayingMovieTitle = nowPlayingMovies.title
                holder.itemNowPlayingMovieLayoutBinding
                    .textViewTitleNPM
                    .text = Html
                    .fromHtml("<font color=$colorTitle>$nowPlayingMovieTitle</font>" +
                            " <font color=$colorYear>($stringNowPlayingMoviesReleaseYear)</font>")
            }catch (e:DateTimeParseException){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
            if(nowPlayingMovies.posterPath == null) {
                Glide.with(holder.itemView.context)
                    .load(R.drawable.no_poster_available)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingMovieLayoutBinding.imageViewPosterNPM)

            } else{
                Glide.with(holder.itemView.context)
                    .load(Credentials.POSTER_PATH + nowPlayingMovies.posterPath)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(50))
                    .into(holder.itemNowPlayingMovieLayoutBinding.imageViewPosterNPM)
            }

            holder.setIsRecyclable(false)
            holder.itemNowPlayingMovieLayoutBinding.root.setOnClickListener {
                val intent = Intent(holder.itemView.context, NowPlayingMovieDetailsActivity::class.java)
                intent.putExtra("Now playing movie id",nowPlayingMovies.id)
                intent.putExtra("Now playing movie vote average",nowPlayingMovies.voteAverage)
                holder.itemView.context.startActivity(intent)
            }
        }
        if (holder is LoadingNowPlayingMovieVH){
            // Set full span
            val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            holder.itemLoadingNowPlayingMovieLayoutBinding.progressBarLoadingNowPlayingMovie.visibility =
                View.VISIBLE
            Handler().postDelayed({
            holder.itemLoadingNowPlayingMovieLayoutBinding.progressBarLoadingNowPlayingMovie.visibility =
                View.INVISIBLE
            },2000)

        }
    }

    override fun getItemCount(): Int {
        return arrayListNowPlayingMovies?.size!! + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == arrayListNowPlayingMovies!!.size){
            VIEW_TYPE_LOADING_NOW_PLAYING_MOVIE
        } else {
            if(arrayListNowPlayingMovies!![position].voteAverage!! > 4.0){
                VIEW_TYPE_POPULAR_NOW_PLAYING_MOVIE
            }else{
                VIEW_TYPE_NOW_PLAYING_MOVIE
            }
        }
    }

    override fun getItemId(position: Int): Long {

        return super.getItemId(position)
    }
    class NowPlayingPopularMovieVH(var itemNowPlayingPopularMovieLayoutBinding: ItemNowPlayingPopularMovieLayoutBinding) :
        RecyclerView.ViewHolder(itemNowPlayingPopularMovieLayoutBinding.root)
    class NowPlayingMovieVH(var itemNowPlayingMovieLayoutBinding: ItemNowPlayingMovieLayoutBinding):
        RecyclerView.ViewHolder(itemNowPlayingMovieLayoutBinding.root)
    class LoadingNowPlayingMovieVH(var itemLoadingNowPlayingMovieLayoutBinding: ItemLoadingNowPlayingMovieLayoutBinding):
        RecyclerView.ViewHolder(itemLoadingNowPlayingMovieLayoutBinding.root)


}