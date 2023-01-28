package com.example.loginandsignup.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginandsignup.api.retrofitInstance
import com.example.loginandsignup.data.myResponse
import kotlinx.coroutines.launch

class jwtViewModel(application: Application):AndroidViewModel(application) {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _myevent = MutableLiveData<myResponse>()
    val myevent: LiveData<myResponse> = _myevent




    fun checkLogin(token:String) {


        viewModelScope.launch {
            try {

                _myevent.value =
                    retrofitInstance.api.verifyUser(token)
                _status.value = "SUCCESS"



            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("hitman",_status.value.toString())
            }
        }

    }
}