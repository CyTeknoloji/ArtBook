package com.caneryildirim.artbook.viewModel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.caneryildirim.artbook.room.Art
import com.caneryildirim.artbook.room.ArtDatabase
import com.caneryildirim.artbook.util.snackbar
import kotlinx.coroutines.*

class FeedViewModel:ViewModel() {
    val artListLive=MutableLiveData<ArrayList<Art>>()
    val uploadDataLive= MutableLiveData<Boolean>(false)
    val errorDataLive= MutableLiveData<Boolean>(false)
    private var job: Job?=null

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        viewModelScope.launch(Dispatchers.Main) {
            errorDataLive.value=true
            uploadDataLive.value=false
        }
    }



    fun getArtFromRoom(fragment:Fragment){
        uploadDataLive.value=true
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val dao=ArtDatabase(fragment.requireContext().applicationContext).artDao()
            val artList=dao.observeArts()
            withContext(Dispatchers.Main){
                artListLive.value= ArrayList(artList)
                uploadDataLive.value=false
            }
        }
    }

    fun deleteArtFromRoom(fragment: Fragment,art: Art){
        uploadDataLive.value=true
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val dao=ArtDatabase(fragment.requireContext().applicationContext).artDao()
            dao.deleteArt(art)
            withContext(Dispatchers.Main){
                fragment.requireView().snackbar("Silindi")
                uploadDataLive.value=false
                getArtFromRoom(fragment)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}