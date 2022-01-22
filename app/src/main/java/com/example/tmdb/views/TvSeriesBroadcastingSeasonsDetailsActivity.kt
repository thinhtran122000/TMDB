package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTvSeriesBroadcastingSeasonsDetailsBinding
import com.example.tmdb.utils.Credentials
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TvSeriesBroadcastingSeasonsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesBroadcastingSeasonsDetailsBinding
    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesBroadcastingSeasonsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from item list tv series broadcasting seasons
        val tvSeriesBroadcastingSeasonsDetailsIntent = intent
        val tvSeriesBroadcastingSeasonsDetailsAirDate = tvSeriesBroadcastingSeasonsDetailsIntent
            .getStringExtra("Tv series broadcasting season air date")
        val tvSeriesBroadcastingSeasonsDetailsEpisodeCount = tvSeriesBroadcastingSeasonsDetailsIntent
            .getStringExtra("Tv series broadcasting season episode count")
        val tvSeriesBroadcastingSeasonsDetailsName = tvSeriesBroadcastingSeasonsDetailsIntent
            .getStringExtra("Tv series broadcasting season name")
        val tvSeriesBroadcastingSeasonsDetailsOverview = tvSeriesBroadcastingSeasonsDetailsIntent
            .getStringExtra("Tv series broadcasting season overview")
        val tvSeriesBroadcastingSeasonsDetailsPosterPath = tvSeriesBroadcastingSeasonsDetailsIntent
            .getStringExtra("Tv series broadcasting season poster path")
//        val tvSeriesBroadcastingSeasonsDetailsSeasonNumber =tvSeriesBroadcastingSeasonsDetailsIntent
//            .getStringExtra("Tv series broadcasting season season number")
        // Display data
        if(tvSeriesBroadcastingSeasonsDetailsPosterPath?.isNotEmpty() == true){
            Glide.with(this)
                .load(Credentials.POSTER_PATH + tvSeriesBroadcastingSeasonsDetailsPosterPath)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageViewPosterDSTS)
        }else{
            Glide.with(this)
                .load(R.drawable.no_backdrop_available)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageViewPosterDSTS)
        }
        if(tvSeriesBroadcastingSeasonsDetailsName?.isNotEmpty() == true){
            binding.textViewNameDSTS.text = tvSeriesBroadcastingSeasonsDetailsName
        }else{
            binding.textViewNameDSTS.text = "Unknown name of tv series season"
        }
        if(tvSeriesBroadcastingSeasonsDetailsEpisodeCount?.isNotEmpty() == true){
            if(tvSeriesBroadcastingSeasonsDetailsEpisodeCount.toInt()<=1){
                binding.textViewEpisodeDSTS.text =
                    "$tvSeriesBroadcastingSeasonsDetailsEpisodeCount episode"
            }else{
                binding.textViewEpisodeDSTS.text = "$tvSeriesBroadcastingSeasonsDetailsEpisodeCount episodes"
            }
        }else{
            binding.textViewEpisodeDSTS.text = "Unknown episode count"
        }
        if(tvSeriesBroadcastingSeasonsDetailsOverview?.isNotEmpty() == true){
            binding.textViewOverviewDSTS.text = tvSeriesBroadcastingSeasonsDetailsOverview
        }else{
            binding.textViewOverviewDSTS.text = "Unknown overview"
        }
        if(tvSeriesBroadcastingSeasonsDetailsAirDate?.isNotEmpty()==true){
            val tvSeriesBroadcastingSeasonsDetailsAirDateFormat = formatTvSeriesBroadcastingSeasonAirDate(tvSeriesBroadcastingSeasonsDetailsAirDate)
            val tvSeriesBroadcastingSeasonsDetailsAirYear = LocalDate.parse(tvSeriesBroadcastingSeasonsDetailsAirDate).year
            if(tvSeriesBroadcastingSeasonsDetailsAirYear>=2021){
                Glide.with(this)
                    .load(R.drawable.new_season_icon)
                    .circleCrop()
                    .into(binding.imageViewNewAndOldDSTS)
            }else{
                Glide.with(this)
                    .load(R.drawable.old_season_icon)
                    .circleCrop()
                    .into(binding.imageViewNewAndOldDSTS)
            }
            binding.textViewAirDateDSTS.text = tvSeriesBroadcastingSeasonsDetailsAirDateFormat
        }else{
            binding.imageViewNewAndOldDSTS.visibility = View.GONE
            binding.textViewAirDateDSTS.text = "Unknown"
        }
        binding.imageViewBackButtonBroadcastingSeasonsDetailsTS.setOnClickListener {
            onBackPressed()
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun formatTvSeriesBroadcastingSeasonAirDate(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val dateFormat = simpleDateFormat.parse(releaseDate)
        val secondSimpleDateFormat = SimpleDateFormat("MMMM dd, yyyy",Locale.ENGLISH)
        return secondSimpleDateFormat.format(dateFormat!!)
    }

}