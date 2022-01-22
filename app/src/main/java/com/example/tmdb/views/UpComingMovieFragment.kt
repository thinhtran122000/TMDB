package com.example.tmdb.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentDialogBinding
import com.example.tmdb.utils.Credentials
import com.example.tmdb.viewmodels.MoviesViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.text.SimpleDateFormat
import java.util.*

class UpComingMovieFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idUpComingMovie = arguments?.getInt("Up coming movie id",0)!!
        val titleUpComingMovie = arguments?.getString("Up coming movie title")
        val overviewUpComingMovie = arguments?.getString("Up coming movie overview")
        val releaseDateUpComingMovie = arguments?.getString("Up coming movie release date")
        val voteAverageUpComingMovie = arguments?.getDouble("Up coming movie vote average",0.0)
        val backdropPathUpComingMovie = arguments?.getString("Up coming movie backdrop path")
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        if(titleUpComingMovie?.isNotEmpty() == true){
            binding.textViewTitleUM.text = titleUpComingMovie
        }else{
            binding.textViewTitleUM.text = "Unknown movie title"
        }

        binding.textViewReleaseDateUM.text = formatUpComingMovieReleaseDate(releaseDateUpComingMovie!!)
        if(voteAverageUpComingMovie != 0.0){
            binding.ratingBarVoteAverageUM.rating = (voteAverageUpComingMovie!!/2).toFloat()
        }else{
            binding.ratingBarVoteAverageUM.rating = 0.0f
        }
        if(overviewUpComingMovie?.isNotEmpty() == true){
            binding.textViewOverviewUM.text = overviewUpComingMovie
        }else{
            binding.textViewOverviewUM.text = "Unknown overview"
        }
        if(backdropPathUpComingMovie?.isNotEmpty() == true){
            Glide.with(this)
                .load(Credentials.BACKDROP_PATH + backdropPathUpComingMovie)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageViewBackdropVideoUM)
        }else{
            Glide.with(this)
                .load(R.drawable.no_backdrop_available)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageViewBackdropVideoUM)
        }
        lifecycle.addObserver(binding.youTubePlayerViewUpComingMovie)
        binding.youTubePlayerViewUpComingMovie.enterFullScreen()
        binding.youTubePlayerViewUpComingMovie.toggleFullScreen()
        binding.youTubePlayerViewUpComingMovie.exitFullScreen()
        binding.youTubePlayerViewUpComingMovie.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                moviesViewModel.mutableUpComingMovieVideoData.observe(viewLifecycleOwner, Observer {
                    if(it?.results?.isNotEmpty() == true) {
                        for(result in it.results!!){
                            if(result.key?.isNotEmpty() == true){
                                binding.buttonPlayTrailerUM.setOnClickListener {
                                    binding.imageViewBackdropVideoUM.visibility = View.INVISIBLE
                                    binding.buttonPlayTrailerUM.visibility = View.INVISIBLE
                                    binding.borderButtonPlayTrailerUM.visibility = View.INVISIBLE
                                    youTubePlayer.loadVideo(result.key!!, 0f)
                                }
                            }
                            else{
                                binding.buttonPlayTrailerUM.visibility = View.INVISIBLE
                                binding.borderButtonPlayTrailerUM.visibility = View.INVISIBLE
                                Toast.makeText(requireContext(),"Not found trailer movie",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else {
                        binding.buttonPlayTrailerUM.visibility = View.INVISIBLE
                        binding.borderButtonPlayTrailerUM.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),"Not found trailer movie",Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                moviesViewModel.mutableUpComingMovieVideoData.observe(viewLifecycleOwner, Observer {
                    if(it?.results?.isNotEmpty() == false) {
                        Toast.makeText(requireContext(),"Not found trailer movie",Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

            }
        })
        moviesViewModel.getUpComingMovieVideo(idUpComingMovie,"en-US")
    }

    override fun onDestroy() {
        binding.youTubePlayerViewUpComingMovie.release()
        super.onDestroy()
    }
    @SuppressLint("SimpleDateFormat")
    private fun formatUpComingMovieReleaseDate(releaseDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val dateFormat = simpleDateFormat.parse(releaseDate)
        val secondSimpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        return secondSimpleDateFormat.format(dateFormat!!)
    }
}