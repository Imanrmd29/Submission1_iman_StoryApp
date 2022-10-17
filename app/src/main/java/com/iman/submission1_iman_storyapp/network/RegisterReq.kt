package com.iman.submission1_iman_storyapp.network

import com.google.gson.annotations.Expose

class RegisterReq {
    @Expose
    var name: String? = null

    @Expose
    var email: String? = null

    @Expose
    var password: String? = null

}