package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.ShowRepo

class ShowVMfactory(val showRepo: ShowRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return ShowViewModel(showRepo) as T
    }
}