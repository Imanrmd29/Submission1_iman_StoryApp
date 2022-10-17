package com.iman.submission1_iman_storyapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iman.submission1_iman_storyapp.Model.ListStoryItem
import com.iman.submission1_iman_storyapp.Model.StoryRespon
import com.iman.submission1_iman_storyapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel: ViewModel() {

    private var _listStoryItem = MutableLiveData<List<ListStoryItem>>()


    fun getListStoryItem(token: String) {
        RetrofitInstance.apiService
            .getAllListStories(token)
            .enqueue(object: Callback<StoryRespon>{
                override fun onResponse(
                    call: Call<StoryRespon>,
                    response: Response<StoryRespon>
                ) {
                    if(response.isSuccessful) {
                        _listStoryItem.postValue(response.body()?.listStory)
                    }
                }

                override fun onFailure(call: Call<StoryRespon>, t: Throwable) {
                    Log.e("FETCHINGSTORIES", t.message.toString())
                }

            })
    }

    fun getListStoryItem(): LiveData<List<ListStoryItem>> {
        return _listStoryItem
    }
}