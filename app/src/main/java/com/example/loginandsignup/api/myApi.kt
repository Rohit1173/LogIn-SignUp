package com.example.loginandsignup.api

import com.example.loginandsignup.data.myResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("/signup")
    fun createUser(
        @Field("userName") userName: String,
        @Field("userEmail") userEmail: String,
        @Field("userPassword") userPassword: String
    ): Call<myResponse>

    @FormUrlEncoded
    @POST("/login")
    fun loginUser(
        @Field("userName") userName: String,
        @Field("userPassword") userPassword: String,
    ): Call<myResponse>
}