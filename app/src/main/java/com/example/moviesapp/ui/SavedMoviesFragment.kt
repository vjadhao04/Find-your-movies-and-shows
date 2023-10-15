package com.example.moviesapp.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.databinding.FragmentSavedMoviesBinding
import com.example.moviesapp.databinding.FragmentSearchmovieBinding
import com.example.moviesapp.db.MovieDB
import com.example.moviesapp.utils.SavedMoviesRVAdapter
import com.google.android.material.snackbar.Snackbar


class SavedMoviesFragment : Fragment() {
    private var _binding: FragmentSavedMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedMoviesRVAdapter: SavedMoviesRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentSavedMoviesBinding.inflate(inflater, container, false)
        binding.savedRV.layoutManager = LinearLayoutManager(requireContext())
        val db = MovieDB.getIntance(requireContext()).userDao()
        var result = db.selectAll()
        savedMoviesRVAdapter = SavedMoviesRVAdapter(requireContext(), result)
        binding.savedRV.adapter = savedMoviesRVAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = result[position]
                db.deletMovie(movie)

                result = db.selectAll()
                savedMoviesRVAdapter= SavedMoviesRVAdapter(requireContext(),result)
                binding.savedRV.adapter=savedMoviesRVAdapter
                Snackbar.make(requireView(), "Successfully Deleted the article", Snackbar.LENGTH_LONG)

                    .apply {
                        setAction("Undo") {
                            db.insertMovie(movie)


                        }
                        this.addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(snackbar: Snackbar, event: Int) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    fragmentManager?.beginTransaction()?.detach(this@SavedMoviesFragment)?.commitNow();
                                    fragmentManager?.beginTransaction()?.attach(this@SavedMoviesFragment)?.commitNow();
                                } else {
                                    fragmentManager?.beginTransaction()?.detach(this@SavedMoviesFragment)?.attach(this@SavedMoviesFragment)?.commit();
                                }

                            }

                            override fun onShown(snackbar: Snackbar) {

                            }
                        })
                            show()

                    }



            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedRV)
        }








        return binding.root
    }


}