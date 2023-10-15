package com.example.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSearchmovieBinding
import com.example.moviesapp.paging.SearchMovieRVAdapter
import com.example.moviesapp.viewmodels.SearchVM
import com.example.moviesapp.viewmodels.SharedViewModel

class SearchmovieFragment : Fragment() , MainInterface{
    private lateinit var _binding:FragmentSearchmovieBinding
    private lateinit var searchVM: SearchVM
    private lateinit var searchMovieRVAdapter: SearchMovieRVAdapter
    private lateinit var sharedViewModel: SharedViewModel

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        _binding=FragmentSearchmovieBinding.inflate(inflater, container, false)
        searchVM= ViewModelProvider(this).get(SearchVM::class.java)
        sharedViewModel=ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.serchRV.layoutManager=LinearLayoutManager(requireContext())





        binding.searchmovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Override onQueryTextSubmit method which is call when submit query is searched
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.length>1) {

                    searchVM.searchMovie(query)
                    searchVM.movies.observe(viewLifecycleOwner, Observer {
                        searchMovieRVAdapter =
                            SearchMovieRVAdapter(it, this@SearchmovieFragment, requireContext())
                        binding.serchRV.adapter = searchMovieRVAdapter
                    })

                }

                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {



                return false
            }
        })


        return binding.root
    }

    override fun onMovieItemClick(id: Int) {
        sharedViewModel.setMovieId(id)
//      (activity as MainActivity).loadFragment(MovieDetailsFragment())
        try {
            val view=requireActivity().findViewById<View>(R.id.fragmentContainerView)
            Navigation.findNavController(view)
                .navigate(R.id.action_global_movieDetailsFragment)

        }
        catch (e:Exception){
            println(e.toString())
        }
    }


}