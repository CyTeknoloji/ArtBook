package com.caneryildirim.artbook.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caneryildirim.artbook.model.ImageResponse
import com.caneryildirim.artbook.model.ImageResult
import com.caneryildirim.artbook.retrofit.RetrofitApi
import com.caneryildirim.artbook.util.Singleton
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ImageApiViewModel:ViewModel() {
    val uploadDataLive=MutableLiveData<Boolean>(false)
    val errorDataLive= MutableLiveData<Boolean>(false)
    val imageResponseLive=MutableLiveData<ImageResponse>()
    private var job:Job?=null

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        viewModelScope.launch(Dispatchers.Main) {
            errorDataLive.value=true
            uploadDataLive.value=false
        }
    }



    fun getImageFromRetrofit(search:String){
        val retrofit=Retrofit.Builder().baseUrl(Singleton.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitApi::class.java)
            job=viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response=retrofit.imageSearch(search)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {imageResponse->
                        uploadDataLive.value=false
                        errorDataLive.value=false
                        imageResponseLive.value=imageResponse
                    }?:{
                        uploadDataLive.value=false
                        errorDataLive.value=true
                    }
                }else{
                    uploadDataLive.value=false
                    errorDataLive.value=true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}