package com.example.tmdb.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.views.DocumentaryFragment
import com.example.tmdb.views.MoviesFragment
import com.example.tmdb.views.SportsFragment
import com.example.tmdb.views.TvSeriesFragment

class ViewPagerDiscoveryAdapter(
    private var fragmentManager: FragmentManager,
    private var lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                MoviesFragment()
            }
            1 -> {
                TvSeriesFragment()
            }
            2 -> {
                DocumentaryFragment()
            }
            3 -> {
                SportsFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}