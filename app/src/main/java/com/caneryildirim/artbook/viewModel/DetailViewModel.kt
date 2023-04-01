package com.caneryildirim.artbook.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caneryildirim.artbook.room.Art
import com.caneryildirim.artbook.room.ArtDao
import com.caneryildirim.artbook.room.ArtDatabase
import com.caneryildirim.artbook.util.snackbar
import com.caneryildirim.artbook.util.toast
import kotlinx.coroutines.*

class DetailViewModel:ViewModel() {
    val uploadDataLive= MutableLiveData<Boolean>(false)
    val errorDataLive=MutableLiveData<Boolean>(false)
    private var job: Job?=null

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        viewModelScope.launch(Dispatchers.Main) {
            errorDataLive.value=true
            uploadDataLive.value=false
        }
    }


    fun saveArt(art: Art, fragment:Fragment){
        if (art.artName.isEmpty() || art.artistName.isEmpty() || art.imageUrl.isEmpty()){
            fragment.toast("Boş yer olmamalı")
        }else{
            uploadDataLive.value=true
            errorDataLive.value=false
            job=viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val dao=ArtDatabase(fragment.requireContext().applicationContext).artDao()
                dao.insertAll(art)
                withContext(Dispatchers.Main){
                    uploadDataLive.value=false
                    errorDataLive.value=false
                    fragment.requireView().snackbar("Yüklendi")
                    fragment.requireActivity().onBackPressed()
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}