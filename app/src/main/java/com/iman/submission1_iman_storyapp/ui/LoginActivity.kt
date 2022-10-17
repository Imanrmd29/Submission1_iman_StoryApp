package com.iman.submission1_iman_storyapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.iman.submission1_iman_storyapp.R
import com.iman.submission1_iman_storyapp.Model.LoginResult
import com.iman.submission1_iman_storyapp.Model.UserLoginRespon
import com.iman.submission1_iman_storyapp.databinding.ActivityLoginBinding
import com.iman.submission1_iman_storyapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(CHECKBOX, false)
        hasLogin(isRemembered)

        binding.buttonSignin.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)
    }


    private fun hasLogin(boolean: Boolean) {
        if(boolean) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        showLoading(true)
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etLoginPassword.text.toString().trim()
        RetrofitInstance.apiService
            .getLoginUser(email, password)
            .enqueue(object: Callback<UserLoginRespon>{
                override fun onResponse(
                    call: Call<UserLoginRespon>,
                    response: Response<UserLoginRespon>
                ) {
                    if(response.isSuccessful) {
                        response.body()?.loginResult?.apply {
                            validateLogin(userId, name, token)
                        }
                        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        showLoading(false)
                        startActivity(mainIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<UserLoginRespon>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, "Data yang dimasukan tidak valid", Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.button_signin -> {
                login()
            }
            R.id.btn_sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateLogin(userId: String, name: String, token: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putString(USER_ID, userId)
        editor.putString(TOKEN, token)
        editor.putBoolean(CHECKBOX, binding.rememberMe.isChecked)
        editor.apply()
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.loginProgressBar.visibility = View.VISIBLE
        else binding.loginProgressBar.visibility = View.GONE
    }

    companion object {
        val SHARED_PREFERENCES = "shared_preferences"
        val CHECKBOX = "checkbox"
        val NAME = "name"
        val USER_ID = "user_id"
        val TOKEN = "token"
    }
}