package com.demo.imgcompare.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.imgcompare.model.ImageModel
import com.demo.imgcompare.retrofit.RetroInstance
import com.demo.imgcompare.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var liveDataList: MutableLiveData<List<ImageModel>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<ImageModel>> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)
        val call = retroService.getImageList()
        call.enqueue(object : Callback<List<ImageModel>> {
            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ImageModel>>,
                response: Response<List<ImageModel>>
            ) {
                liveDataList.postValue(response.body())
            }
        })
    }
}