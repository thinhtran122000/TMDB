package com.example.tmdb.views

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class ProfileFragment : Fragment(){
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageUri:Uri
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Generate firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()
        // Generate firebase storage
        firebaseStorage = FirebaseStorage.getInstance()
        // Check if user profile exist or not
        checkUserImage()
        checkAccountVerified()
        val userName =  firebaseAuth.currentUser?.displayName
        binding.editTextUserName.setText(userName)
        val userEmail = firebaseAuth.currentUser?.email
        binding.textViewUserEmail.text = userEmail
        // Setting for custom dialog
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog_profile_layout)
        dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_background_dialog))
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        val areYouSureTextView:AppCompatTextView = dialog.findViewById(R.id.textViewAreYouSure)
        val acceptButton: AppCompatButton = dialog.findViewById(R.id.buttonAccept)
        val cancelButton: AppCompatButton = dialog.findViewById(R.id.buttonReject)
        // Condition when click save button
        val currentUser= firebaseAuth.currentUser
        binding.buttonSave.setOnClickListener {
            if(currentUser?.displayName == binding.editTextUserName.text.toString() || binding.editTextUserName.text.toString().isEmpty()){
                if(!currentUser?.isEmailVerified!!){
                    dialog.dismiss()
                }
            }else{
                if(!currentUser?.isEmailVerified!!){
                    dialog.dismiss()
                    Toast.makeText(requireContext(),"Please verify your account before changing information",Toast.LENGTH_SHORT).show()
                }else{
                    dialog.show()
                    areYouSureTextView.text = "Are you sure about changing your name?"
                }
            }
        }
        // Update user name when clicked accept button in dialog
        acceptButton.setOnClickListener {
            try {
                if(currentUser?.isEmailVerified!!){
                    val userProfileChangeRequest =  UserProfileChangeRequest.Builder()
                    currentUser.updateProfile(userProfileChangeRequest.setDisplayName(binding.editTextUserName.text.toString()).build())
                    dialog.dismiss()
                    showProgressBarChangeProfile()
                    Handler().postDelayed({
                        binding.editTextUserName.setText(currentUser.displayName)
                        Toast.makeText(requireContext(),
                            "Your name is changed successfully.\n ＼ʕ •ᴥ•ʔ／ ",Toast.LENGTH_SHORT).show()
                    },2000)
                    Log.d("User name","${currentUser.displayName}")
                }else{
                    Toast.makeText(requireContext(),"Please verify your account before changing information",Toast.LENGTH_SHORT).show()
                    Log.d("User name","${currentUser.displayName}")
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
            }
        }
        // Cancel update user name
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        // Logout application
        binding.buttonLogout.setOnClickListener {
            dialog.show()
            areYouSureTextView.text = "Do you want to logout?"
            acceptButton.setOnClickListener {
                dialog.dismiss()
                showProgressBarLogout()
                Handler().postDelayed({
                    firebaseAuth.signOut()
                },2000)
                Handler().postDelayed({
                    Toast.makeText(requireContext(),"Logout successfully. ʕ •ᴥ•ʔ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                },3000)
            }
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

        }
        // Change password profile
        binding.buttonChangePasswordProfile.setOnClickListener {
            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        // Get image from files on device
        binding.textViewEditPhotoProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,200)
        }
        //Verify account
        binding.textViewStatus.setOnClickListener {
            dialog.show()
            areYouSureTextView.text = "Do you want to verify now?"
            acceptButton.text = "Send"
            cancelButton.text = "Cancel"
            acceptButton.setOnClickListener {
                dialog.dismiss()
                showProgressBarChangeProfile()
                binding.textViewStatus.isEnabled = false
                try {
                    if(currentUser!=null){
                        if(!currentUser.isEmailVerified){
                            currentUser.sendEmailVerification().addOnSuccessListener {
                                Handler().postDelayed({
                                    Toast.makeText(requireContext(),
                                        "Link is successfully sent to email ${currentUser.email}. Check your email now!",
                                        Toast.LENGTH_SHORT).show()
                                },2000)
                                binding.textViewStatus.isEnabled = false
                            }.addOnFailureListener {
                                Handler().postDelayed({
                                    Toast.makeText(requireContext(),
                                        "Send link to email ${currentUser.email} failed!",
                                        Toast.LENGTH_SHORT).show()
                                },2000)
                            }
                        }
                    }
                }catch (e:Exception){
                    Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                }
            }
            cancelButton.setOnClickListener {
                dialog.dismiss()
                binding.textViewStatus.isEnabled = true
            }
        }
    }

    // Send back data url image from device to application
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 200 && data !=null && data.data !=null){
            imageUri = data.data!!
            showProgressBarChangeImage()
            Handler().postDelayed({
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.imageViewProfile)
                Toast.makeText(requireContext(),"Edit profile image successfully",Toast.LENGTH_SHORT).show()
            },2000)
            // Upload image to firebase storage
            try {
                val userEmail = firebaseAuth.currentUser?.email
                storageReference = firebaseStorage.reference
                    .child("$userEmail")
                    .child("$imageUri.jpeg")
                storageReference.putFile(imageUri).addOnCompleteListener {
                    Log.d("Upload storage","Upload image successfully")
                    getDownloadUrl(storageReference)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
            }

        }

    }
    // Download image from firebase storage to update user profile image
    private fun getDownloadUrl(storageReference: StorageReference){
        // Download image from firebase storage
        try {
            storageReference.downloadUrl.addOnSuccessListener {
                Log.d("Download from storage", "Successfully downloaded $it")
                val currentUser = firebaseAuth.currentUser!!
                val userProfileChangeRequest = UserProfileChangeRequest.Builder().setPhotoUri(it).build()
                currentUser.updateProfile(userProfileChangeRequest).addOnSuccessListener {
                    Log.d("Status","Edit profile image successfully")
                    Log.d("User","${currentUser.email} ${currentUser.displayName} ${currentUser.photoUrl}")
                }.addOnFailureListener {
                    Log.d("Status","Edit profile image failed")
                }
            }.addOnFailureListener {
                Log.d("Download from storage", it.message.toString())
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
        }

    }
    // Check image of user existed or not, then display user image
    private fun checkUserImage(){
        val currentUser = firebaseAuth.currentUser
        // Set user image if it not null
        if(currentUser !=null){
            if(currentUser.photoUrl !=null){
                Glide.with(this)
                    .load(currentUser.photoUrl)
                    .placeholder(R.drawable.no_profile_image)
                    .into(binding.imageViewProfile)
            }else{
                Glide.with(this)
                    .load(R.drawable.no_profile_image)
                    .into(binding.imageViewProfile)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun checkAccountVerified(){
        val currentUser = firebaseAuth.currentUser
        if(currentUser !=null){
            if(currentUser.isEmailVerified){
                currentUser.reload()
                binding.textViewStatus.visibility = View.INVISIBLE
            }
        }
    }
    // Show progress change profile
    private fun showProgressBarChangeProfile() {
        binding.progressBarChangeProfile.visibility = View.VISIBLE
        Handler().postDelayed({
            binding.progressBarChangeProfile.visibility = View.INVISIBLE
        },2000)
    }
    // Show progress logout
    private fun showProgressBarLogout() {
        Handler().postDelayed({
            binding.progressBarLogout.visibility = View.INVISIBLE
        },3000)
        binding.progressBarLogout.visibility = View.VISIBLE
    }
    // Show progress change profile image
    private fun showProgressBarChangeImage(){
        Handler().postDelayed({
            binding.progressBarChangeImage.visibility = View.INVISIBLE
        },2000)
        binding.progressBarChangeImage.visibility = View.VISIBLE
    }
}