package com.iman.submission1_iman_storyapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iman.submission1_iman_storyapp.R
import com.iman.submission1_iman_storyapp.databinding.ActivityMainBinding
import com.iman.submission1_iman_storyapp.Model.ListStoryItem
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences
    private lateinit var name: String

    private val storiesAdapter: StoryAdapter by lazy { StoryAdapter() }
    private lateinit var viewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        name = preferences.getString(LoginActivity.NAME, "Anonim").toString()
        val token = preferences.getString(LoginActivity.TOKEN, "").toString()

        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this)[StoryViewModel::class.java]
        searchListItem(token)
        binding.mainRecyclerView.adapter = storiesAdapter


        viewModel.getListStoryItem().observe(this) {
            if(it != null){
                storiesAdapter.setData(it)
                showLoading(false)
            }
        }
        binding.greetMainActivity.text = "Hello, $name"
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            (R.id.logout) -> {
                preferences.edit().apply {
                    clear()
                    apply()
                }
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
                return true
            }
            else -> false
        }
    }

    private fun searchListItem(token: String){
        showLoading(true)
        viewModel.getListStoryItem(token)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.storiesProgressBar.visibility = View.VISIBLE
        else binding.storiesProgressBar.visibility = View.GONE
    }
}