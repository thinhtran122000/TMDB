package com.example.tmdb.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityLoginBinding
import com.example.tmdb.internet.InternetConnection
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var internetConnection: InternetConnection
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkInternetConnection()
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        Handler().postDelayed({
            setAnimationLogo(binding.imageViewTMDB)
        },200)
        Handler().postDelayed({
            setAnimationInputTextEmail(binding.editTextEmail)
        },400)
        Handler().postDelayed({
            setAnimationInputTextPassword(binding.editTextPassword)
        },600)
        Handler().postDelayed({
            setAnimationButtonLogin(binding.buttonLogin)
        },800)
        Handler().postDelayed({
            setAnimationTextViewSignUp(binding.textViewSignUp)
        },1000)
        Handler().postDelayed({
            setAnimationTextViewForgotPassword(binding.textViewForgotPassword)
        },1200)
        binding.textViewSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            var email:String = binding.editTextEmail.text.toString().trim()
            var password:String = binding.editTextPassword.text.toString().trim()
            if(TextUtils.isEmpty(email)){
                binding.editTextEmail.error = "Please enter your email! ʕ = ᴥ = ʔ"
                binding.editTextEmail.requestFocus()
                return@setOnClickListener
            }else{
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.editTextEmail.error = "This is not an email! ʕ = ᴥ = ʔ"
                    binding.editTextEmail.requestFocus()
                }
            }
            if(TextUtils.isEmpty(password)){
                binding.editTextPassword.error = "Please enter your password! ʕ = ᴥ = ʔ"
                binding.editTextPassword.requestFocus()
                return@setOnClickListener
            }else{
                it.hideKeyboard()
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    val userName = firebaseAuth.currentUser?.displayName
                    Log.d("User login","$userName ${firebaseAuth.currentUser?.email} ${firebaseAuth.currentUser?.photoUrl}")
                    showProgressLogin()
                    Handler().postDelayed({
                        val intent = Intent(this, SplashScreenActivity::class.java)
//                        intent.putExtra("user name",userName)
                        startActivity(intent)
                        finish()
                    }, 2000)
//                    Handler().postDelayed({
//                        Toast.makeText(this, "Welcome to The Movie DB ＼ʕ •ᴥ•ʔ／", Toast.LENGTH_SHORT).show()
//                    }, 2500)
                }.addOnFailureListener {
                    showProgressLogin()
                    Handler().postDelayed({
                        Toast.makeText(this,"Email or password is incorrect or the account does not exist! ʕノ•ᴥ•ʔノ ︵ ┻━┻", Toast.LENGTH_SHORT).show()
                    },3000)
                }
            }
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
    private fun checkInternetConnection() {
        internetConnection = InternetConnection(application)
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_activity_login_dialog_internet_connection_layout)
        dialog.window?.setBackgroundDrawable(this.getDrawable(R.drawable.custom_background_dialog_authentication))
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        val tryAgainButton: AppCompatButton = dialog.findViewById(R.id.buttonTryAgain)
        val settingButton: AppCompatButton = dialog.findViewById(R.id.buttonSetting)
        internetConnection.observe(this,{ isConnected ->
            if (isConnected){
                dialog.dismiss()
                Handler().postDelayed({
                    binding.progressBarLogin.visibility = View.INVISIBLE
                    binding.editTextEmail.visibility = View.VISIBLE
                    binding.editTextPassword.visibility = View.VISIBLE
                    binding.buttonLogin.visibility = View.VISIBLE
                    binding.textViewSignUp.visibility = View.VISIBLE
                    binding.textViewForgotPassword.visibility = View.VISIBLE
                },3000)
            }else{
                Handler().postDelayed({
                    binding.progressBarLogin.visibility = View.VISIBLE
                },5000)
                Handler().postDelayed({
                    dialog.show()
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.editTextEmail.visibility = View.INVISIBLE
                    binding.editTextPassword.visibility = View.INVISIBLE
                    binding.buttonLogin.visibility = View.INVISIBLE
                    binding.textViewSignUp.visibility = View.INVISIBLE
                    binding.textViewForgotPassword.visibility = View.INVISIBLE
                },7000)
            }
        })
        tryAgainButton.setOnClickListener {
            internetConnection.observe(this,{ isConnected ->
                if (isConnected){
                    dialog.dismiss()
                    Handler().postDelayed({
                        binding.progressBarLogin.visibility = View.INVISIBLE
                        binding.editTextEmail.visibility = View.VISIBLE
                        binding.editTextPassword.visibility = View.VISIBLE
                        binding.buttonLogin.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE
                        binding.textViewForgotPassword.visibility = View.VISIBLE
                    },3000)
                }else{
                    dialog.dismiss()
                    Handler().postDelayed({
                        dialog.show()
                    },3000)
                    Handler().postDelayed({
                        binding.progressBarLogin.visibility = View.VISIBLE
                        binding.editTextEmail.visibility = View.INVISIBLE
                        binding.editTextPassword.visibility = View.INVISIBLE
                        binding.buttonLogin.visibility = View.INVISIBLE
                        binding.textViewSignUp.visibility = View.INVISIBLE
                        binding.textViewForgotPassword.visibility = View.INVISIBLE
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
                        binding.progressBarLogin.visibility = View.INVISIBLE
                        binding.editTextEmail.visibility = View.VISIBLE
                        binding.editTextPassword.visibility = View.VISIBLE
                        binding.buttonLogin.visibility = View.VISIBLE
                        binding.textViewSignUp.visibility = View.VISIBLE
                        binding.textViewForgotPassword.visibility = View.VISIBLE
                    },3000)
                }else{
                    dialog.dismiss()
                    Handler().postDelayed({
                        dialog.show()
                    },3000)
                    Handler().postDelayed({
                        binding.progressBarLogin.visibility = View.VISIBLE
                        binding.editTextEmail.visibility = View.INVISIBLE
                        binding.editTextPassword.visibility = View.INVISIBLE
                        binding.buttonLogin.visibility = View.INVISIBLE
                        binding.textViewSignUp.visibility = View.INVISIBLE
                        binding.textViewForgotPassword.visibility = View.INVISIBLE
                    },5000)
                }
            })
        }
    }

    private fun checkUser(){
        if(firebaseAuth.currentUser !=null){
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun showProgressLogin(){
        binding.progressBarLogin.visibility = View.VISIBLE
        Handler().postDelayed({
            binding.progressBarLogin.visibility = View.INVISIBLE
        }, 3000)
    }
    private fun setAnimationLogo(imageView: ImageView){
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        imageView.visibility = View.VISIBLE
        imageView.startAnimation(animation)
    }
    private fun setAnimationInputTextEmail(editTextEmail:TextInputEditText){
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        editTextEmail.visibility = View.VISIBLE
        editTextEmail.startAnimation(animation)
    }
    private fun setAnimationInputTextPassword(editTextPassword:TextInputEditText,){
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        editTextPassword.visibility = View.VISIBLE
        editTextPassword.startAnimation(animation)
    }
    private fun setAnimationButtonLogin(buttonLogin:AppCompatButton){
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        buttonLogin.visibility = View.VISIBLE
        buttonLogin.startAnimation(animation)
    }
    private fun setAnimationTextViewSignUp(textViewSignUp:AppCompatTextView){
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        textViewSignUp.visibility = View.VISIBLE
        textViewSignUp.startAnimation(animation)
    }
    private fun setAnimationTextViewForgotPassword(textViewForgotPassword: AppCompatTextView) {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1000
        animation.startOffset = 1000
        textViewForgotPassword.visibility = View.VISIBLE
        textViewForgotPassword.startAnimation(animation)
    }
}