package com.iman.submission1_iman_storyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.iman.submission1_iman_storyapp.R
import com.iman.submission1_iman_storyapp.databinding.ActivityRegisterBinding
import com.iman.submission1_iman_storyapp.Model.UserRegisterRespon
import com.iman.submission1_iman_storyapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_create_account -> {
                if(validateCreateAccount()) {
                    requestCreateAccount()
                    clearEditText()
                } else {
                    clearEditText()
                }
            }
        }
    }

    private fun requestCreateAccount() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etCreatePassword.text.toString().trim()
        showLoading(true)
        RetrofitInstance.apiService
            .createAccount(name, email, password)
            .enqueue(object: Callback<UserRegisterRespon>{
                override fun onResponse(
                    call: Call<UserRegisterRespon>,
                    response: Response<UserRegisterRespon>
                ) {
                    if(response.isSuccessful) {
                        showLoading(false)
                        Toast.makeText(this@RegisterActivity, "Akun berhasil dibuat silahkan ke halam login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserRegisterRespon>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(this@RegisterActivity, "Akun gagal dibuat", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@RegisterActivity, "bisa jadi karena email telah telah digunakan, masalah koneksi, atau masalah server", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun clearEditText() {
        binding.etName.text.clear()
        binding.etEmail.text!!.clear()
        binding.etCreatePassword.text!!.clear()
    }

    private fun validateCreateAccount(): Boolean {
        return if(binding.etEmail.text!!.isNotEmpty()
            && binding.etCreatePassword.text!!.isNotEmpty()
            && binding.etName.text.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()
            && binding.etCreatePassword.text.toString().length > 6) {
            true
        } else {
            Toast.makeText(this, "Data harus diisi dengan benar", Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }
}