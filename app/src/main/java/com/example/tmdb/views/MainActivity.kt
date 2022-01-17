package com.example.tmdb.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up Navigation Component with Bottom Navigation View
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            if(navController.currentDestination?.id == it.itemId){
                return@setOnNavigationItemReselectedListener
            }
        }
//        val intent = intent
//        val userName = intent.getStringExtra("user name")
//        val bundle = Bundle()
//        bundle.putString("user name",userName)
//        val profileFragment = ProfileFragment()
//        profileFragment.arguments = bundle
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            navController.popBackStack()
        }else if(!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click back again to exit.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }else{
            super.onBackPressed()
            return
        }
    }

//    override fun onPause() {
//        super.onPause()
//        finish()
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        finish()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        finish()
//    }
}