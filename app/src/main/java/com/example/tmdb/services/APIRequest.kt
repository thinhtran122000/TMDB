package com.example.tmdb.services

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object APIRequest {
    // Call request Retrofit use Higher function
    inline fun <T> callRequest(
        call: Call<T>?,
        crossinline onSuccess:(T?) -> Unit,
        crossinline onError:(String?) ->Unit
    ){
        call?.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful) {
                    Log.d("State",response.toString())
                    onSuccess.invoke(response.body())
                }
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("State",t.message.toString())
                onError(t.message.toString())
            }
        })
    }
}