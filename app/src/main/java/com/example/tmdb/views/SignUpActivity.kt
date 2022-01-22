package com.example.tmdb.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.tmdb.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@DelicateCoroutinesApi
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonRegister.setOnClickListener {
            it.hideKeyboard()
            val fullName:String = binding.editTextFullNameRegister.text.toString().trim()
            val email:String = binding.editTextEmailRegister.text.toString().trim()
            val password:String = binding.editTextPasswordRegister.text.toString().trim()
            val confirmPassword:String = binding.editTextConfirmPasswordRegister.text.toString().trim()
            if(TextUtils.isEmpty(fullName)){
                binding.editTextFullNameRegister.error = "Full name is required ʕ = ᴥ = ʔ"
                binding.editTextFullNameRegister.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(email)){
                binding.editTextEmailRegister.error = "Email is required ʕ = ᴥ = ʔ"
                binding.editTextEmailRegister.requestFocus()
                return@setOnClickListener
            }else{
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.editTextEmailRegister.error = "Invalid email format ʕ = ᴥ = ʔ"
                    binding.editTextEmailRegister.requestFocus()
                }
            }
            if(TextUtils.isEmpty(password)){
                binding.editTextPasswordRegister.error = "Password is required ʕ = ᴥ = ʔ"
                binding.editTextPasswordRegister.requestFocus()
                return@setOnClickListener
            }else{
                if(password.length < 8){
                    binding.editTextPasswordRegister.error = "Password is too short, must be 8 - 16 characters ʕ = ᴥ = ʔ"
                    binding.editTextPasswordRegister.requestFocus()
                    return@setOnClickListener
                }else{
                    if(password.length > 16){
                        binding.editTextPasswordRegister.error = "Password is too long ʕ = ᴥ = ʔ"
                        binding.editTextPasswordRegister.requestFocus()
                        return@setOnClickListener
                    }
                    if(!isValidPassword(password)){
                        binding.editTextPasswordRegister.error =
                            "Password must include at least one lower case letter (a), one uppercase letter (A) and one special character (@#/$%^&+=*!?|~<>(){}_;.,\\[\\]\"')"
                        binding.editTextPasswordRegister.requestFocus()
                        return@setOnClickListener
                    }
                }
            }

            if(TextUtils.isEmpty(confirmPassword)){
                binding.editTextConfirmPasswordRegister.error = "Please re-enter your password to confirm ʕ = ᴥ = ʔ"
                binding.editTextConfirmPasswordRegister.requestFocus()
                return@setOnClickListener
            }else{
                if (confirmPassword != password){
                    binding.editTextConfirmPasswordRegister.error = "Confirm password is not match ʕ = ᴥ = ʔ"
                    binding.editTextConfirmPasswordRegister.requestFocus()
                    return@setOnClickListener
                }else{
                    showProgressSignUp()
                    try {
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                            GlobalScope.launch {
                                suspend {
                                    val currentUser= firebaseAuth.currentUser
                                    val userProfileChangeRequest =  UserProfileChangeRequest.Builder()
                                    currentUser?.updateProfile(userProfileChangeRequest.setDisplayName(binding.editTextFullNameRegister.text.toString()).build())
                                    Log.d("User sign up","${currentUser?.displayName} ${currentUser?.email} ${currentUser?.photoUrl}")
                                    delay(2000)
                                }.invoke()
                            }
                            Handler().postDelayed({
                                Toast.makeText(this,"Create account successfully ＼ʕ •ᴥ•ʔ／",Toast.LENGTH_SHORT).show()
                            },2000)
                            Handler().postDelayed({
                                val intent = Intent(this, SplashScreenActivity::class.java)
                                startActivity(intent)
                                finish()
                            }, 3000)
                        }.addOnFailureListener {
                            val emailUser = binding.editTextEmailRegister.text.toString()
                            Handler().postDelayed({
                                Toast.makeText(this,"An account with email $emailUser already exists ʕ•ᴥ•ʔ",Toast.LENGTH_SHORT).show()
                            }, 2000)
                        }
                    }catch (e:Exception){
                        Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.textViewSignIn.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun isValidPassword(password:String):Boolean{
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#/$%^&+=*!?|~<>(){}_;.,\\[\\]\"\'])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern);
        val matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private fun showProgressSignUp() {
        binding.progressBarRegister.visibility = View.VISIBLE
        Handler().postDelayed({
            binding.progressBarRegister.visibility = View.INVISIBLE
        }, 2000)
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
//            onBackPressed()
//            finish()

//            val intent = Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//            finish()
}