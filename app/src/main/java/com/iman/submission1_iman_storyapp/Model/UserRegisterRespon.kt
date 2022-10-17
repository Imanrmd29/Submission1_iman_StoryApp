package com.iman.submission1_iman_storyapp.Model

import com.google.gson.annotations.SerializedName

data class UserRegisterRespon(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)