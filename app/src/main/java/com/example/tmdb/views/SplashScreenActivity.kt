package com.example.tmdb.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.ActivitySplashScreenBinding
import com.example.tmdb.utils.Credentials
import com.example.tmdb.viewmodels.TopRatedMoviesViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import android.view.animation.Animation

import android.view.animation.Animation.AnimationListener

import android.view.animation.AlphaAnimation
import android.widget.Toast

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var topRatedMoviesViewModel: TopRatedMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topRatedMoviesViewModel = ViewModelProvider(this)[TopRatedMoviesViewModel::class.java]
        topRatedMoviesViewModel.mutableTopRatedMoviesLiveData.observe(this, Observer {
            if(it?.results?.isNotEmpty() == true){
                if(it.results!![0].posterPath?.isNotEmpty() == true){
                    Glide.with(this)
                        .load(Credentials.POSTER_PATH + it.results!![0].posterPath)
                        .into(binding.imageViewFirstTopRatedMoviePoster)
                    Handler().postDelayed({
                        setAnimationImage(binding.imageViewFirstTopRatedMoviePoster)
                    },4000)
                }
                if(it.results!![1].posterPath?.isNotEmpty() == true){
                    Glide.with(this)
                        .load(Credentials.POSTER_PATH + it.results!![1].posterPath)
                        .into(binding.imageViewSecondTopRatedMoviePoster)
                    Handler().postDelayed({
                        setAnimationImage(binding.imageViewSecondTopRatedMoviePoster)
                    },4200)
                }
                if(it.results!![2].posterPath?.isNotEmpty() == true){
                    Glide.with(this)
                        .load(Credentials.POSTER_PATH + it.results!![2].posterPath)
                        .into(binding.imageViewThirdTopRatedMoviePoster)
                    Handler().postDelayed({
                        setAnimationImage(binding.imageViewThirdTopRatedMoviePoster)
                    },4400)
                }
                if(it.results!![3].posterPath?.isNotEmpty() == true){
                    Glide.with(this)
                        .load(Credentials.POSTER_PATH + it.results!![3].posterPath)
                        .into(binding.imageViewFinalTopRatedMoviePoster)
                    Handler().postDelayed({
                        setAnimationImage(binding.imageViewFinalTopRatedMoviePoster)
                    },4600)
                }
            }
        })
        topRatedMoviesViewModel.getTopRatedMovies("en-US",1)
        Handler().postDelayed({
            setAnimationLogo(binding.imageViewLogo)
        },4000)
        Handler().postDelayed({
            Toast.makeText(this, "Welcome to The Movie DB ＼ʕ •ᴥ•ʔ／", Toast.LENGTH_SHORT).show()
        },10000)
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },10000)
    }

    private fun setAnimationImage(imageView:ImageView){
        val firstAnimation = AlphaAnimation(0.0f, 1.0f)
        firstAnimation.duration = 1000
        firstAnimation.startOffset = 1000
        val secondAnimation = AlphaAnimation(1.0f, 0.0f)
        secondAnimation.duration = 1000
        secondAnimation.startOffset = 1000
        //animation1 AnimationListener
        firstAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                // start animation2 when animation1 ends (continue)
                imageView.startAnimation(secondAnimation)
            }

            override fun onAnimationRepeat(arg0: Animation) {

            }

            override fun onAnimationStart(arg0: Animation) {

            }
        })

        //animation2 AnimationListener
        secondAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                // start animation1 when animation2 ends (repeat)
//                imageView.startAnimation(firstAnimation)
                imageView.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(arg0: Animation) {
            }

            override fun onAnimationStart(arg0: Animation) {
            }
        })
        imageView.startAnimation(secondAnimation)
    }
    private fun setAnimationLogo(imageView:ImageView){
        imageView.visibility = View.VISIBLE
        val firstAnimation = AlphaAnimation(0.0f, 1.0f)
        firstAnimation.duration = 1000
        firstAnimation.startOffset = 1000
        imageView.startAnimation(firstAnimation)
    }
}