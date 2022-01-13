package com.example.tmdb.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.tmdb.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
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
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    val userName = firebaseAuth.currentUser?.displayName
//                Log.d("User","$userName")
                    binding.progressBarLogin.visibility = View.VISIBLE
                    Handler().postDelayed({
                        binding.progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(this, "Welcome to The Movie DB ＼ʕ •ᴥ•ʔ／", Toast.LENGTH_SHORT).show()
                    }, 3000)

                    Handler().postDelayed({
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user name",userName)
                        startActivity(intent)
                        finish()
                    }, 2000)
                }.addOnFailureListener {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    Handler().postDelayed({
                        binding.progressBarLogin.visibility = View.INVISIBLE
                        Toast.makeText(this,"Email or password is incorrect or the account does not exist! ʕノ•ᴥ•ʔノ ︵ ┻━┻", Toast.LENGTH_SHORT).show()
                    },3000)

                }
            }
        }
    }
    private fun checkUser(){
        if(firebaseAuth.currentUser !=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}