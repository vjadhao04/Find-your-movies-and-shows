package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

import androidx.navigation.Navigation.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.api.Retrofit
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.databinding.FragmentMovieBinding

import com.example.moviesapp.paging.MoviesPagingAdapter
import com.example.moviesapp.repository.MoviesRepository
import com.example.moviesapp.utils.CheckInternet


import com.example.moviesapp.viewmodels.MainViewModel
import com.example.moviesapp.viewmodels.SharedViewModel
import com.example.moviesapp.viewmodels.ViewModelFactory
import kotlin.properties.Delegates


interface MainInterface{

    fun onMovieItemClick( id :Int)
}

class MovieFragment : Fragment(), MainInterface {

private lateinit var model: SharedViewModel
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!


    private lateinit var moviesRepository: MoviesRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        val checkInternet=CheckInternet()
       if( checkInternet.checkForInternet(requireContext())) {
           model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

           val retrofitService = Retrofit.getInstance().create(RetrofitService::class.java)
           moviesRepository = MoviesRepository(retrofitService)

           mainViewModel = ViewModelProvider(
               this,
               ViewModelFactory(moviesRepository)
           )[MainViewModel::class.java]
           recyclerView = binding.rvmovie
           adapter = MoviesPagingAdapter(requireContext(), this)
           recyclerView.layoutManager = LinearLayoutManager(requireContext())
           recyclerView.adapter = adapter
           val movies = mainViewModel.movies
           movies.observe(viewLifecycleOwner, Observer {
               adapter.submitData(lifecycle, it)
           })

           Log.d("internet","connected")
       }
        else{
            Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_LONG).show()
           Log.d("internet","not connected")
       }
        return binding.root
    }


    override fun onMovieItemClick(id: Int) {
        model.setMovieId(id)
//      (activity as MainActivity).loadFragment(MovieDetailsFragment())
        try {
            val view=requireActivity().findViewById<View>(R.id.fragmentContainerView)
            findNavController(view).navigate(R.id.action_global_movieDetailsFragment)

        }
        catch (e:Exception){

            println("Error" + e.toString())

        }

    }





}