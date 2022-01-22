package com.example.tmdb.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTvSeriesCastDetailsBinding
import com.example.tmdb.utils.Credentials

class TvSeriesCastDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesCastDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesCastDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Receive data from tv series list credits activity
        val tvSeriesCastDetailsIntent = intent
        val tvSeriesListCastName = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series list cast name")
        val tvSeriesListCastGender = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series list cast gender")
        val tvSeriesListCastCharacter = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series list cast character")
        val tvSeriesListCastKnowForDepartment = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series list cast known for department")
        val tvSeriesListCastProfilePath = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series list cast profile path")
        // Receive data from tv series details activity
        val tvSeriesCastName = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series cast name")
        val tvSeriesCastGender = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series cast gender")
        val tvSeriesCastCharacter = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series cast character")
        val tvSeriesCastKnowForDepartment = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series cast known for department")
        val tvSeriesCastProfilePath = tvSeriesCastDetailsIntent
            .getStringExtra("Tv series cast profile path")
        // Display data
        if(tvSeriesListCastProfilePath !=null) {
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + tvSeriesListCastProfilePath)
                .into(binding.imageViewProfileCastTS)
        }else{
            Glide.with(this)
                .load(Credentials.PROFILE_PATH_CAST + tvSeriesCastProfilePath)
                .into(binding.imageViewProfileCastTS)
        }
        if(tvSeriesListCastProfilePath == null && tvSeriesCastProfilePath == null){
            if(tvSeriesListCastGender == "2" || tvSeriesCastGender == "2"){
                Glide.with(this)
                    .load(R.drawable.unknown_man_credits)
                    .into(binding.imageViewProfileCastTS)
            }else if(tvSeriesListCastGender == "1" || tvSeriesCastGender == "1"){
                Glide.with(this)
                    .load(R.drawable.unknown_woman_credits)
                    .into(binding.imageViewProfileCastTS)
            }else if((tvSeriesListCastGender == "0" || tvSeriesCastGender == "0")||
                (tvSeriesListCastGender == "3" || tvSeriesCastGender == "3")){
                Glide.with(this)
                    .load(R.drawable.unknown_credits_gender)
                    .into(binding.imageViewProfileCastTS)
            }else{
                Glide.with(this)
                    .load(R.drawable.placeholder_profile_credits)
                    .into(binding.imageViewProfileCastTS)
            }
        }
        if(tvSeriesListCastName !=null){
            if(tvSeriesListCastName.isNotEmpty()){
                binding.textViewNameCastTS.text = tvSeriesListCastName
            }else{
                binding.textViewNameCastTS.text = "Unknown name of cast"
            }
        }else{
            if(tvSeriesCastName?.isNotEmpty() == true){
                binding.textViewNameCastTS.text = tvSeriesCastName
            }else{
                binding.textViewNameCastTS.text = "Unknown name of cast"
            }
        }
        if(tvSeriesListCastGender !=null ){
            when (tvSeriesListCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastTS.text = "Unidentified"
                }
                "1" -> {
                    binding.textViewGenderCastTS.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastTS.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastTS.text = "Unknown gender of cast"
                }
            }
        }else{
            when (tvSeriesCastGender) {
                "0","3" -> {
                    binding.textViewGenderCastTS.text = "Unidentified"
                }
                "1" -> {
                    binding.textViewGenderCastTS.text = "Female"
                }
                "2" -> {
                    binding.textViewGenderCastTS.text = "Male"
                }
                else -> {
                    binding.textViewGenderCastTS.text = "Unknown gender of cast"
                }
            }
        }
        if(tvSeriesListCastCharacter!=null){
            if(tvSeriesListCastCharacter.isNotEmpty()){
                binding.textViewCharacterCastTS.text = tvSeriesListCastCharacter
            }else{
                binding.textViewCharacterCastTS.text = "Unknown character of cast "
            }
        }else{
            if(tvSeriesCastCharacter?.isNotEmpty() == true){
                binding.textViewCharacterCastTS.text = tvSeriesCastCharacter
            }else {
                binding.textViewCharacterCastTS.text = "Unknown character of cast "
            }
        }
        if(tvSeriesListCastKnowForDepartment!=null){
            if (tvSeriesListCastKnowForDepartment.isNotEmpty()){
                binding.textViewKnownForDepartmentCastTS.text = tvSeriesListCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastTS.text = "Unknown department of cast"
            }
        }else{
            if (tvSeriesCastKnowForDepartment?.isNotEmpty() == true){
                binding.textViewKnownForDepartmentCastTS.text = tvSeriesCastKnowForDepartment
            }else{
                binding.textViewKnownForDepartmentCastTS.text = "Unknown department of cast"
            }
        }
        binding.imageViewBackButtonCastDetailsTS.setOnClickListener {
            onBackPressed()
        }
    }
}