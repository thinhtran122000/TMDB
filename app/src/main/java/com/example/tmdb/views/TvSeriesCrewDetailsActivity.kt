package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTvSeriesCrewDetailsBinding
import com.example.tmdb.utils.Credentials

class TvSeriesCrewDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesCrewDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesCrewDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from tv series list credits activity
        val tvSeriesCrewDetailsIntent = intent
        val tvSeriesListCrewName = tvSeriesCrewDetailsIntent.getStringExtra("Tv series list crew name")
        val tvSeriesListCrewGender = tvSeriesCrewDetailsIntent.getIntExtra("Tv series list crew gender",0)
        val tvSeriesListCrewJob = tvSeriesCrewDetailsIntent.getStringExtra("Tv series list crew job")
        val tvSeriesListCrewKnowForDepartment = tvSeriesCrewDetailsIntent.getStringExtra("Tv series list crew known for department")
        val tvSeriesListCrewProfilePath = tvSeriesCrewDetailsIntent.getStringExtra("Tv series list crew profile path")
        // Display data
        if(tvSeriesListCrewProfilePath?.isNotEmpty()==true){
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CREW + tvSeriesListCrewProfilePath)
                .into(binding.imageViewProfileCrewTS)
        }else{
            if(tvSeriesListCrewGender == 2) {
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCrewTS)
            }
            else if(tvSeriesListCrewGender == 1){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCrewTS)
            }else if(tvSeriesListCrewGender == 0 || tvSeriesListCrewGender == 3){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCrewTS)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCrewTS)
            }
        }
        if(tvSeriesListCrewName?.isNotEmpty() == true){
            binding.textViewNameCrewTS.text = tvSeriesListCrewName
        }else{
            binding.textViewNameCrewTS.text = "Unknown name of crew"
        }
        when (tvSeriesListCrewGender) {
            0,3 -> {
                binding.textViewGenderCrewTS.text = "Unidentified"
            }
            1 -> {
                binding.textViewGenderCrewTS.text = "Female"
            }
            2 -> {
                binding.textViewGenderCrewTS.text = "Male"
            }
            else -> {
                binding.textViewGenderCrewTS.text = "Unknown gender of crew"
            }
        }
        if(tvSeriesListCrewJob?.isNotEmpty() == true){
            binding.textViewJobCrewTS.text = tvSeriesListCrewJob
        }else{
            binding.textViewJobCrewTS.text = "Unknown job of crew "
        }
        if (tvSeriesListCrewKnowForDepartment?.isNotEmpty() == true){
            binding.textViewKnownForDepartmentCrewTS.text = tvSeriesListCrewKnowForDepartment
        }else{
            binding.textViewKnownForDepartmentCrewTS.text = "Unknown department of crew"
        }
        binding.imageViewBackButtonCrewDetailsTS.setOnClickListener {
            onBackPressed()
        }
    }
}