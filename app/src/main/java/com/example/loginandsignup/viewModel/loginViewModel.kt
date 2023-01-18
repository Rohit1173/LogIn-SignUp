package com.example.loginandsignup.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.loginandsignup.api.retrofitInstance
import com.example.loginandsignup.data.loginData
import com.example.loginandsignup.data.myResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class loginViewModel(application: Application):AndroidViewModel(application) {

    var logResponse: MutableLiveData<Response<myResponse>> = MutableLiveData()


    fun checkUser(loginData: loginData) {
        retrofitInstance.api.loginUser(
            loginData.userName,
            loginData.userPassword
        ).enqueue(object : Callback<myResponse> {
            override fun onResponse(
                call: Call<myResponse>,
                response: Response<myResponse>
            ) {
                logResponse.value = response
            }

            override fun onFailure(call: Call<myResponse>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}