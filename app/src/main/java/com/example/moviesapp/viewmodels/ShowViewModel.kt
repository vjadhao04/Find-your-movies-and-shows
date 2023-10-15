package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesapp.repository.ShowRepo

class ShowViewModel(val showRepo: ShowRepo):ViewModel() {


  val showData=showRepo.getshow().cachedIn(viewModelScope)
}