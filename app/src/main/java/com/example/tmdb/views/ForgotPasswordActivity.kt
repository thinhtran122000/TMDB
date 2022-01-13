package com.example.tmdb.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.tmdb.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.buttonSend.setOnClickListener {
            val emailAddressUser = binding.editEnterEmailAddress.text.toString()
            if(TextUtils.isEmpty(emailAddressUser)){
                binding.editEnterEmailAddress.error = "Please enter your email! ʕ = ᴥ = ʔ"
                binding.editEnterEmailAddress.requestFocus()
                return@setOnClickListener
            }else{
                if(!Patterns.EMAIL_ADDRESS.matcher(emailAddressUser).matches()){
                    binding.editEnterEmailAddress.error = "This is not an email! ʕ = ᴥ = ʔ"
                    binding.editEnterEmailAddress.requestFocus()
                }
            }
            firebaseAuth.sendPasswordResetEmail(emailAddressUser).addOnSuccessListener {
                binding.progressBarForgotPassword.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.progressBarForgotPassword.visibility = View.INVISIBLE
                    Toast.makeText(this,
                        "Reset link is sent to $emailAddressUser.\n Open your gmail to reset password now.",
                        Toast.LENGTH_SHORT).show()
                },2000)
                Handler().postDelayed({
                    finish()
                },3000)
            }.addOnFailureListener {
                Handler().postDelayed({
                    binding.progressBarForgotPassword.visibility = View.INVISIBLE
                    Toast.makeText(this,
                        "$emailAddressUser does not exits. Check your email again. ʕ = ᴥ = ʔ",
                        Toast.LENGTH_SHORT).show()
                },2000)
            }
        }
        binding.buttonCancel.setOnClickListener {
            onBackPressed()
            finish()
        }

    }
}