package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityListNowPlayingMovieCrewDetailsBinding
import com.example.tmdb.utils.Credentials

class ListNowPlayingMovieCrewDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListNowPlayingMovieCrewDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNowPlayingMovieCrewDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from now playing movie list credits activity
        val nowPlayingMovieCrewDetailsIntent = intent
        val nowPlayingMovieListCrewName = nowPlayingMovieCrewDetailsIntent.getStringExtra("Now playing list crew name")
        val nowPlayingMovieListCrewGender = nowPlayingMovieCrewDetailsIntent.getIntExtra("Now playing movie list crew gender",0)
        val nowPlayingMovieListCrewJob = nowPlayingMovieCrewDetailsIntent.getStringExtra("Now playing movie list crew job")
        val nowPlayingMovieListCrewKnowForDepartment = nowPlayingMovieCrewDetailsIntent.getStringExtra("Now playing movie list crew known for department")
        val nowPlayingMovieListCrewProfilePath = nowPlayingMovieCrewDetailsIntent.getStringExtra("Now playing movie list crew profile path")
        // Display data
        if(nowPlayingMovieListCrewProfilePath?.isNotEmpty()==true){
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CREW + nowPlayingMovieListCrewProfilePath)
                .into(binding.imageViewProfileCrewNPM)
        }else{
            if(nowPlayingMovieListCrewGender == 2) {
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCrewNPM)
            }
            else if(nowPlayingMovieListCrewGender == 1){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCrewNPM)
            }else if(nowPlayingMovieListCrewGender == 0 || nowPlayingMovieListCrewGender == 3){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCrewNPM)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCrewNPM)
            }
        }
        if(nowPlayingMovieListCrewName?.isNotEmpty() == true){
            binding.textViewNameCrewNPM.text = nowPlayingMovieListCrewName
        }else{
            binding.textViewNameCrewNPM.text = "Unknown name of crew"
        }
        when (nowPlayingMovieListCrewGender) {
            0,3 -> {
                binding.textViewGenderCrewNPM.text = "Unidentified"
            }
            1 -> {
                binding.textViewGenderCrewNPM.text = "Female"
            }
            2 -> {
                binding.textViewGenderCrewNPM.text = "Male"
            }
            else -> {
                binding.textViewGenderCrewNPM.text = "Unknown gender of crew"
            }
        }
        if(nowPlayingMovieListCrewJob?.isNotEmpty() == true){
            binding.textViewJobCrewNPM.text = nowPlayingMovieListCrewJob
        }else{
            binding.textViewJobCrewNPM.text = "Unknown job of crew "
        }
        if (nowPlayingMovieListCrewKnowForDepartment?.isNotEmpty() == true){
            binding.textViewKnownForDepartmentCrewNPM.text = nowPlayingMovieListCrewKnowForDepartment
        }else{
            binding.textViewKnownForDepartmentCrewNPM.text = "Unknown department of crew"
        }
        binding.imageViewBackButtonCrewDetailsNPM.setOnClickListener {
            onBackPressed()
        }
    }
}