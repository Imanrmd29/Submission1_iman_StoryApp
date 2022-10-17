package com.iman.submission1_iman_storyapp.network

import com.iman.submission1_iman_storyapp.Model.StoryRespon
import com.iman.submission1_iman_storyapp.Model.UserLoginRespon
import com.iman.submission1_iman_storyapp.Model.UserRegisterRespon
import retrofit2.Call
import retrofit2.http.*

interface StoryApi{

    @FormUrlEncoded
    @POST("login")
    fun getLoginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserLoginRespon>

    @FormUrlEncoded
    @POST("register")
    fun createAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserRegisterRespon>

    @GET("stories")
    fun getAllListStories(
        @Header("Authorization")
        authHeader: String
    ): Call<StoryRespon>
}