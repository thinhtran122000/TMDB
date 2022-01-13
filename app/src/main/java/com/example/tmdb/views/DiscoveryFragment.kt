package com.example.tmdb.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tmdb.databinding.FragmentDiscoveryBinding

import com.example.tmdb.adapters.viewpager.ViewPagerDiscoveryAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DiscoveryFragment : Fragment() {
      private lateinit var binding: FragmentDiscoveryBinding
      private lateinit var viewPagerDiscoveryAdapter: ViewPagerDiscoveryAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiscoveryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerDiscoveryAdapter = ViewPagerDiscoveryAdapter(childFragmentManager,lifecycle)
        binding.viewPager2.adapter = viewPagerDiscoveryAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position->
            when(position){
                0 -> {
                    tab.text = "Movies"
                }
                1 -> {
                    tab.text = "Tv Series"
                }
                2 -> {
                    tab.text = "Documentary"
                }
                3 -> {
                    tab.text = "Sports"
                }
            }
        }.attach()
        binding.editTextSearchMoviesAndTvSeries.setOnClickListener {
            val intent = Intent(requireContext(), SearchMoviesAndTvSeriesActivity::class.java)
            startActivity(intent)
        }
    }
}
//        binding.viewPager2.setPageTransformer { page, position ->
//            updatePagerHeightForChild(page, binding.viewPager2)
//        }

//    private fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
//        view.post {
//            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
//            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            view.measure(wMeasureSpec, hMeasureSpec)
//            pager.layoutParams = (pager.layoutParams).also { lp -> lp.height = view.measuredHeight }
//            pager.invalidate()
//        }
//    }
//    override fun onResume() {
//        super.onResume()
//        binding.root.requestLayout()
//    }