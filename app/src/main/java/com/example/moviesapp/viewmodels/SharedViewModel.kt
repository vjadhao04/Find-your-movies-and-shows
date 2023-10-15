package com.example.moviesapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel :ViewModel() {

    init {
        Log.d("sharedVM","In sharedVM")
    }

    private var urlMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
    private var movieIdMutableLiveData:MutableLiveData<Int> = MutableLiveData<Int>()
    private var ytTitle:MutableLiveData<String> =MutableLiveData<String>()
    val urlLivedata:LiveData<String>
        get() = urlMutableLiveData

    val MovieIdLiveData:LiveData<Int>
    get() = movieIdMutableLiveData

    val ytTitleLiveData:LiveData<String>
    get() = ytTitle

    fun setYtTitle(title:String){
        ytTitle.postValue(title)
    }
    fun setUrl(url:String){
        urlMutableLiveData.postValue(url)
    }
    fun setMovieId(id:Int){
        movieIdMutableLiveData.postValue(id)
    }

}