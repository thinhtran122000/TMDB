package com.example.tmdb.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.tmdb.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.regex.Pattern

@DelicateCoroutinesApi
class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonConfirm.setOnClickListener {
            var currentPassword: String = binding.editTextUserCurrentPassword.text.toString().trim()
            var newPassword: String = binding.editTextUserNewPassword.text.toString().trim()
            var confirmPassword: String =
                binding.editTextUserConfirmNewPassword.text.toString().trim()
            if (TextUtils.isEmpty(currentPassword)) {
                binding.editTextUserCurrentPassword.error =
                    "Please enter your current password! ʕ = ᴥ = ʔ"
                binding.editTextUserCurrentPassword.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(newPassword)) {
                binding.editTextUserNewPassword.error =
                    "Please enter your new password! \n ʕ = ᴥ = ʔ"
                binding.editTextUserNewPassword.requestFocus()
                return@setOnClickListener
            } else {
                if (newPassword == currentPassword) {
                    binding.editTextUserNewPassword.error =
                        "Please enter another password! \n ʕ = ᴥ = ʔ"
                    binding.editTextUserNewPassword.requestFocus()
                    return@setOnClickListener
                }
                if (newPassword.length < 8) {
                    binding.editTextUserNewPassword.error =
                        "Password is too short, must be 8 - 16 characters ʕ = ᴥ = ʔ"
                    binding.editTextUserNewPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    if (newPassword.length > 16) {
                        binding.editTextUserNewPassword.error = "Password is too long ʕ = ᴥ = ʔ"
                        binding.editTextUserNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                    if (!isValidPassword(newPassword)) {
                        binding.editTextUserNewPassword.error =
                            "Password must include at least one lower case letter (a), one uppercase letter (A) and one special character (@#/$%^&+=*!?|~<>(){}_;.,\\[\\]\"')"
                        binding.editTextUserNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                }
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                binding.editTextUserConfirmNewPassword.error =
                    "Please re-enter your password to confirm ʕ = ᴥ = ʔ"
                binding.editTextUserConfirmNewPassword.requestFocus()
                return@setOnClickListener
            } else {
                if (confirmPassword != newPassword) {
                    binding.editTextUserConfirmNewPassword.error =
                        "Confirm password is not match ʕ = ᴥ = ʔ"
                    binding.editTextUserConfirmNewPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    binding.progressBarChangePassword.visibility = View.VISIBLE
                    Handler().postDelayed({
                        binding.progressBarChangePassword.visibility = View.INVISIBLE
                    }, 3000)
                    val currentUser = firebaseAuth.currentUser
                    if (currentUser != null && currentUser.email != null) {
                        val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)
                        currentUser.reauthenticate(credential).addOnSuccessListener {
                            currentUser.updatePassword(newPassword).addOnSuccessListener {
                                    Handler().postDelayed({
                                        binding.editTextUserCurrentPassword.text?.clear()
                                        binding.editTextUserNewPassword.text?.clear()
                                        binding.editTextUserConfirmNewPassword.text?.clear()
                                        Toast.makeText(this,
                                            "Password is changed successfully. ＼ʕ •ᴥ•ʔ／",
                                            Toast.LENGTH_SHORT).show()
                                        onBackPressed()
                                        finish()
                                    }, 3000)
                                }.addOnFailureListener {
                                    Handler().postDelayed({
                                        Toast.makeText(this, "Password changed failed! ʕ = ᴥ = ʔ",
                                            Toast.LENGTH_SHORT).show()
                                    }, 3000)
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, " Your password is incorrect! \n Re-authentication failed! ʕ = ᴥ = ʔ",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.imageViewBackButtonChangePassword.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#/$%^&+=*!?|~<>(){}_;.,\\[\\]\"\'])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(passwordPattern);
        val matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
//                            GlobalScope.launch {
//                                suspend {
//                                    currentUser.updatePassword(currentPassword).addOnSuccessListener {
//                                        Log.d("Password","Successful")
//                                    }.addOnFailureListener {
//                                        Log.d("Password","Failed")
//                                    }
//                                    delay(2000)
//                                }.invoke()
//                            }

