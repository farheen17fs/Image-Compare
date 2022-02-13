package com.demo.imgcompare.retrofit

import com.demo.imgcompare.model.ImageModel
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET(".")
    fun getImageList(): Call<List<ImageModel>>
}