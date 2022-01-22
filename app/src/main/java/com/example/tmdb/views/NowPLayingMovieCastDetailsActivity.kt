package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityNowPlayingMovieCastDetailsBinding
import com.example.tmdb.utils.Credentials

class NowPLayingMovieCastDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNowPlayingMovieCastDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingMovieCastDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from now playing movie list credits activity
        val nowPlayingMovieCastDetailsIntent = intent
        val nowPlayingMovieListCastName = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie list cast name")
        val nowPlayingMovieListCastGender = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie list cast gender")
        val nowPlayingMovieListCastCharacter = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie list cast character")
        val nowPlayingMovieListCastKnowForDepartment = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie list cast known for department")
        val nowPlayingMovieListCastProfilePath = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie list cast profile path")
        // Receive data from now playing movie details activity
        val nowPlayingMovieCastName = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie cast name")
        val nowPlayingMovieCastGender = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie cast gender")
        val nowPlayingMovieCastCharacter = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie cast character")
        val nowPlayingMovieCastKnowForDepartment = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie cast known for department")
        val nowPlayingMovieCastProfilePath = nowPlayingMovieCastDetailsIntent
            .getStringExtra("Now playing movie cast profile path")
        // Display data
        if(nowPlayingMovieListCastProfilePath !=null) {
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + nowPlayingMovieListCastProfilePath)
                .into(binding.imageViewProfileCastNPM)
        }else{
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + nowPlayingMovieCastProfilePath)
                .into(binding.imageViewProfileCastNPM)
        }
        if(nowPlayingMovieListCastProfilePath == null && nowPlayingMovieCastProfilePath == null){
            if(nowPlayingMovieListCastGender == "2" || nowPlayingMovieCastGender == "2"){
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCastNPM)
            }else if(nowPlayingMovieListCastGender == "1" || nowPlayingMovieCastGender == "1"){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCastNPM)
            }else if((nowPlayingMovieListCastGender == "0" || nowPlayingMovieCastGender == "0")||
                (nowPlayingMovieListCastGender == "3" || nowPlayingMovieCastGender == "3")){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCastNPM)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCastNPM)
            }
        }
        if(nowPlayingMovieListCastName !=null){
            if(nowPlayingMovieListCastName.isNotEmpty()){
                binding.textViewNameCastNPM.text = nowPlayingMovieListCastName
            }else{
                binding.textViewNameCastNPM.text = "Unknown name of cast"
            }
        }else{
            if(nowPlayingMovieCastName?.isNotEmpty() == true){
                binding.textViewNameCastNPM.text = nowPlayingMovieCastName
            }else{
                binding.textViewNameCastNPM.text = "Unknown name of cast"
            }
        }
        if(nowPlayingMovieListCastGender !=null ){
            when (nowPlayingMovieListCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastNPM.text = "Unidentified"
                }
                "1" -> {
                    binding.textViewGenderCastNPM.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastNPM.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastNPM.text = "Unknown gender of cast"
                }
            }
        }else{
            when (nowPlayingMovieCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastNPM.text = "Other"
                }
                "1" -> {
                    binding.textViewGenderCastNPM.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastNPM.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastNPM.text = "Unknown gender of cast"
                }
            }
        }
        if(nowPlayingMovieListCastCharacter!=null){
            if(nowPlayingMovieListCastCharacter.isNotEmpty()){
                binding.textViewCharacterCastNPM.text = nowPlayingMovieListCastCharacter
            }else{
                binding.textViewCharacterCastNPM.text = "Unknown character of cast "
            }
        }else{
            if(nowPlayingMovieCastCharacter?.isNotEmpty() == true){
                binding.textViewCharacterCastNPM.text = nowPlayingMovieCastCharacter
            }else {
                binding.textViewCharacterCastNPM.text = "Unknown character of cast "
            }
        }
        if(nowPlayingMovieListCastKnowForDepartment!=null){
            if (nowPlayingMovieListCastKnowForDepartment.isNotEmpty()){
                binding.textViewKnownForDepartmentCastNPM.text = nowPlayingMovieListCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastNPM.text = "Unknown department of cast"
            }
        }else{
            if (nowPlayingMovieCastKnowForDepartment?.isNotEmpty() == true){
                binding.textViewKnownForDepartmentCastNPM.text = nowPlayingMovieCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastNPM.text = "Unknown department of cast"
            }
        }
        binding.imageViewBackButtonCastDetailsNPM.setOnClickListener {
            onBackPressed()
        }

    }
}