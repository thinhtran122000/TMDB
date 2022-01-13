package com.example.tmdb.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class ProfileFragment : Fragment(){
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        val userName =  firebaseAuth.currentUser?.displayName
        binding.editTextUserName.setText(userName)
        val userEmail = firebaseAuth.currentUser?.email
        binding.textViewUserEmail.text = userEmail
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog_save_profile_layout)
        dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_background_dialog))
        dialog.window?.setLayout(900,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        val areYouSureTextView:AppCompatTextView = dialog.findViewById(R.id.textViewAreYouSure)
        val acceptButton: AppCompatButton = dialog.findViewById(R.id.buttonAccept)
        val cancelButton: AppCompatButton = dialog.findViewById(R.id.buttonReject)

        val currentUser= firebaseAuth.currentUser
        acceptButton.setOnClickListener {
            val userProfileChangeRequest =  UserProfileChangeRequest.Builder()
            currentUser?.updateProfile(userProfileChangeRequest.setDisplayName(binding.editTextUserName.text.toString()).build())
            dialog.dismiss()
            binding.progressBarChangeProfile.visibility = View.VISIBLE
            Handler().postDelayed({
                binding.progressBarChangeProfile.visibility = View.INVISIBLE
                binding.editTextUserName.setText(currentUser?.displayName)
                Toast.makeText(requireContext(),
                    "Your name is changed successfully.\n ＼ʕ •ᴥ•ʔ／ ",Toast.LENGTH_SHORT).show()
            },2000)
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.buttonSave.setOnClickListener {
            if(currentUser?.displayName == binding.editTextUserName.text.toString()){
                dialog.dismiss()
            }else{
                dialog.show()
                areYouSureTextView.text = "Are you sure about changing your name?"
            }
        }
        // Logout
        binding.buttonLogout.setOnClickListener {
            dialog.show()
            areYouSureTextView.text = "Do you want to logout?"
            acceptButton.setOnClickListener {
                dialog.dismiss()
                Handler().postDelayed({
                    binding.progressBarProfile.visibility = View.INVISIBLE
                },3000)
                binding.progressBarProfile.visibility = View.VISIBLE
                Handler().postDelayed({
                    FirebaseAuth.getInstance().signOut()
                },2000)
                Handler().postDelayed({
                    Toast.makeText(requireActivity(),"Logout successfully. ʕ •ᴥ•ʔ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                },3000)
            }
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

        }
        Glide.with(this)
            .load(R.drawable.no_profile_image)
            .circleCrop().into(binding.imageViewProfile)
        binding.buttonChangePasswordProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }

//                photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")



//

        }
//    private fun checkUser(){
//        if(firebaseAuth.currentUser!=null){
//
//        }
//    }

}