package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTrendingMovieCrewDetailsBinding
import com.example.tmdb.utils.Credentials

class TrendingMovieCrewDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingMovieCrewDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingMovieCrewDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from trending movie list credits activity
        val trendingMovieCrewDetailsIntent = intent
        val trendingMovieListCrewName = trendingMovieCrewDetailsIntent.getStringExtra("Trending movie list crew name")
        val trendingMovieListCrewGender = trendingMovieCrewDetailsIntent.getIntExtra("Trending movie list crew gender",0)
        val trendingMovieListCrewJob = trendingMovieCrewDetailsIntent.getStringExtra("Trending movie list crew job")
        val trendingMovieListCrewKnowForDepartment = trendingMovieCrewDetailsIntent.getStringExtra("Trending movie list crew known for department")
        val trendingMovieListCrewProfilePath = trendingMovieCrewDetailsIntent.getStringExtra("Trending movie list crew profile path")
        // Display data
        if(trendingMovieListCrewProfilePath?.isNotEmpty()==true){
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CREW + trendingMovieListCrewProfilePath)
                .into(binding.imageViewProfileCrewTM)
        }else{
            if(trendingMovieListCrewGender == 2) {
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCrewTM)
            }
            else if(trendingMovieListCrewGender == 1){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCrewTM)
            }else if(trendingMovieListCrewGender == 0 || trendingMovieListCrewGender == 3){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCrewTM)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCrewTM)
            }
        }
        if(trendingMovieListCrewName?.isNotEmpty() == true){
            binding.textViewNameCrewTM.text = trendingMovieListCrewName
        }else{
            binding.textViewNameCrewTM.text = "Unknown name of crew"
        }
        when (trendingMovieListCrewGender) {
            0,3 -> {
                binding.textViewGenderCrewTM.text = "Unidentified"
            }
            1 -> {
                binding.textViewGenderCrewTM.text = "Female"
            }
            2 -> {
                binding.textViewGenderCrewTM.text = "Male"
            }
            else -> {
                binding.textViewGenderCrewTM.text = "Unknown gender of crew"
            }
        }
        if(trendingMovieListCrewJob?.isNotEmpty() == true){
            binding.textViewJobCrewTM.text = trendingMovieListCrewJob
        }else{
            binding.textViewJobCrewTM.text = "Unknown job of crew "
        }
        if (trendingMovieListCrewKnowForDepartment?.isNotEmpty() == true){
            binding.textViewKnownForDepartmentCrewTM.text = trendingMovieListCrewKnowForDepartment
        }else{
            binding.textViewKnownForDepartmentCrewTM.text = "Unknown department of crew"
        }
        binding.imageViewBackButtonCrewDetailsTM.setOnClickListener {
            onBackPressed()
        }
    }
}