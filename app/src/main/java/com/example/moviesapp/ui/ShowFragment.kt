package com.example.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.api.Retrofit
import com.example.moviesapp.api.RetrofitService

import com.example.moviesapp.paging.ShowPagingAdapter
import com.example.moviesapp.repository.ShowRepo
import com.example.moviesapp.viewmodels.ShowVMfactory
import com.example.moviesapp.viewmodels.ShowViewModel


class showFragment : Fragment() {

    private lateinit var showViewModel: ShowViewModel
    private lateinit var showRepo: ShowRepo
    private lateinit var retrofitService: RetrofitService
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShowPagingAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_show, container, false)

        retrofitService=Retrofit.getInstance().create(RetrofitService::class.java)

        showRepo=ShowRepo(retrofitService)

        showViewModel=ViewModelProvider(this, ShowVMfactory(showRepo)).get(ShowViewModel::class.java)

        recyclerView=view.findViewById(R.id.showrv)

        adapter= ShowPagingAdapter(requireContext())

        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter=adapter

        val show=showViewModel.showData

        show.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle,it)
        })





        return view
    }


}