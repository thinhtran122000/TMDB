package com.example.tmdb.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMainBinding
import com.example.tmdb.internet.InternetConnection
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment:NavHostFragment
    private lateinit var navController: NavController
    private lateinit var internetConnection: InternetConnection
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInternetConnection()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkInternetConnection()
        // Set up Navigation Component with Bottom Navigation View
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            if(navController.currentDestination?.id == it.itemId){
                return@setOnNavigationItemReselectedListener
            }
        }
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
    override fun onStart() {
        super.onStart()
        checkInternetConnection()
    }
    override fun onResume() {
        super.onResume()
        checkInternetConnection()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkInternetConnection(){
        internetConnection = InternetConnection(application)
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_activity_main_dialog_internet_connection_layout)
        dialog.window?.setBackgroundDrawable(this.getDrawable(R.drawable.custom_background_dialog))
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        val tryAgainButton: AppCompatButton = dialog.findViewById(R.id.buttonTryAgain)
        val settingButton: AppCompatButton = dialog.findViewById(R.id.buttonSetting)
        internetConnection.observe(this,{ isConnected ->
            if (isConnected){
                dialog.dismiss()
                Handler().postDelayed({
                    binding.progressBarActivityMain.visibility = View.INVISIBLE
                    binding.navHostFragment.visibility = View.VISIBLE
                    binding.bottomNavigationView.visibility = View.VISIBLE
                },3000)
            }else{
                Handler().postDelayed({
                    binding.progressBarActivityMain.visibility = View.VISIBLE
                },5000)
                Handler().postDelayed({
                    dialog.show()
                    binding.progressBarActivityMain.visibility = View.VISIBLE
                    binding.navHostFragment.visibility = View.INVISIBLE
                    binding.bottomNavigationView.visibility = View.INVISIBLE
                },7000)
            }
        })
        tryAgainButton.setOnClickListener {
            internetConnection.observe(this,{ isConnected ->
                if (isConnected){
                    dialog.dismiss()
                    Handler().postDelayed({
                        binding.progressBarActivityMain.visibility = View.INVISIBLE
                        binding.navHostFragment.visibility = View.VISIBLE
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    },3000)
                }else{
                    dialog.dismiss()
                    Handler().postDelayed({
                        dialog.show()
                    },3000)
                    Handler().postDelayed({
                        binding.progressBarActivityMain.visibility = View.VISIBLE
                        binding.navHostFragment.visibility = View.INVISIBLE
                        binding.bottomNavigationView.visibility = View.INVISIBLE
                    },5000)
                }
            })
        }

        settingButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
            internetConnection.observe(this,{ isConnected ->
                if (isConnected){
                    dialog.dismiss()
                    Handler().postDelayed({
                        binding.progressBarActivityMain.visibility = View.INVISIBLE
                        binding.navHostFragment.visibility = View.VISIBLE
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    },3000)
                }else{
                    dialog.dismiss()
                    Handler().postDelayed({
                        dialog.show()
                    },3000)
                    Handler().postDelayed({
                        binding.progressBarActivityMain.visibility = View.VISIBLE
                        binding.navHostFragment.visibility = View.INVISIBLE
                        binding.bottomNavigationView.visibility = View.INVISIBLE
                    },5000)
                }
            })
        }
    }
    private fun initiateDialogConnection() {

    }

}