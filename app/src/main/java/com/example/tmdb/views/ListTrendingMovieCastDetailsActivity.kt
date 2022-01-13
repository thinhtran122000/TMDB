package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityListTrendingMovieCastDetailsBinding
import com.example.tmdb.utils.Credentials

class ListTrendingMovieCastDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListTrendingMovieCastDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTrendingMovieCastDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from trending movie list credits activity
        val trendingMovieCastDetailsIntent = intent
        val trendingMovieListCastName = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie list cast name")
        val trendingMovieListCastGender = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie list cast gender")
        val trendingMovieListCastCharacter = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie list cast character")
        val trendingMovieListCastKnowForDepartment = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie list cast known for department")
        val trendingMovieListCastProfilePath = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie list cast profile path")
        // Receive data from trending movie details activity
        val trendingMovieCastName = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie cast name")
        val trendingMovieCastGender = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie cast gender")
        val trendingMovieCastCharacter = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie cast character")
        val trendingMovieCastKnowForDepartment = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie cast known for department")
        val trendingMovieCastProfilePath = trendingMovieCastDetailsIntent
            .getStringExtra("Trending movie cast profile path")
        // Display data
        if(trendingMovieListCastProfilePath !=null) {
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + trendingMovieListCastProfilePath)
                .into(binding.imageViewProfileCastTM)
        }else{
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + trendingMovieCastProfilePath)
                .into(binding.imageViewProfileCastTM)
        }
        if(trendingMovieListCastProfilePath == null && trendingMovieCastProfilePath == null){
            if(trendingMovieListCastGender == "2" || trendingMovieCastGender == "2"){
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCastTM)
            }else if(trendingMovieListCastGender == "1" || trendingMovieCastGender == "1"){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCastTM)
            }else if((trendingMovieListCastGender == "0" || trendingMovieCastGender == "0")||
                (trendingMovieListCastGender == "3" || trendingMovieCastGender == "3")){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCastTM)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCastTM)
            }
        }
        if(trendingMovieListCastName !=null){
            if(trendingMovieListCastName.isNotEmpty()){
                binding.textViewNameCastTM.text = trendingMovieListCastName
            }else{
                binding.textViewNameCastTM.text = "Unknown name of cast"
            }
        }else{
            if(trendingMovieCastName?.isNotEmpty() == true){
                binding.textViewNameCastTM.text = trendingMovieCastName
            }else{
                binding.textViewNameCastTM.text = "Unknown name of cast"
            }
        }
        if(trendingMovieListCastGender !=null ){
            when (trendingMovieListCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastTM.text = "Unidentified"
                }
                "1" -> {
                    binding.textViewGenderCastTM.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastTM.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastTM.text = "Unknown gender of cast"
                }
            }
        }else{
            when (trendingMovieCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastTM.text = "Unidentified"
                }
                "1" -> {
                    binding.textViewGenderCastTM.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastTM.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastTM.text = "Unknown gender of cast"
                }
            }
        }
        if(trendingMovieListCastCharacter!=null){
            if(trendingMovieListCastCharacter.isNotEmpty()){
                binding.textViewCharacterCastTM.text = trendingMovieListCastCharacter
            }else{
                binding.textViewCharacterCastTM.text = "Unknown character of cast "
            }
        }else{
            if(trendingMovieCastCharacter?.isNotEmpty() == true){
                binding.textViewCharacterCastTM.text = trendingMovieCastCharacter
            }else {
                binding.textViewCharacterCastTM.text = "Unknown character of cast "
            }
        }
        if(trendingMovieListCastKnowForDepartment!=null){
            if (trendingMovieListCastKnowForDepartment.isNotEmpty()){
                binding.textViewKnownForDepartmentCastTM.text = trendingMovieListCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastTM.text = "Unknown department of cast"
            }
        }else{
            if (trendingMovieCastKnowForDepartment?.isNotEmpty() == true){
                binding.textViewKnownForDepartmentCastTM.text = trendingMovieCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastTM.text = "Unknown department of cast"
            }
        }
        binding.imageViewBackButtonCastDetailsTM.setOnClickListener {
            onBackPressed()
        }
    }
}