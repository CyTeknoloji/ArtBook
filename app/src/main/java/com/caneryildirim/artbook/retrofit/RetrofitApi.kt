package com.caneryildirim.artbook.retrofit

import android.media.Image
import com.caneryildirim.artbook.model.ImageResponse
import com.caneryildirim.artbook.util.Singleton
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery:String,
        @Query("key") apiKey:String=Singleton.API_KEY
    ):Response<ImageResponse>



}