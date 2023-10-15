package com.example.moviesapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesapp.R
import com.example.moviesapp.api.POSTER_BASE_URL
import com.example.moviesapp.api.Retrofit
import com.example.moviesapp.api.RetrofitService
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.db.FavoriteMovie
import com.example.moviesapp.db.MovieDB
import com.example.moviesapp.models.SingleMovie
import com.example.moviesapp.repository.MoviesRepository
import com.example.moviesapp.viewmodels.MovieDetailsViewModel
import com.example.moviesapp.viewmodels.MovieDetailsViewModelFactory
import com.example.moviesapp.viewmodels.SharedViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetailsFragment : Fragment() {

    private lateinit var singleMovie: SingleMovie

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var title:String


    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var moviesRepository: MoviesRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()




        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val retrofitService = Retrofit.getInstance().create(RetrofitService::class.java)
        moviesRepository = MoviesRepository(retrofitService)
        movieDetailsViewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(moviesRepository)
        ).get(MovieDetailsViewModel::class.java)



        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
         model.MovieIdLiveData.observe(viewLifecycleOwner, Observer {
             movieDetailsViewModel.getMovie(it)
         })
        movieDetailsViewModel.movie.observe(requireActivity(), Observer {

            singleMovie=it
            title=it.title
            binding.movie=it
            Glide
                .with(this)
                .load(POSTER_BASE_URL + it.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivMoviePoster)


            if(MovieDB.getIntance(requireContext()).userDao().getMFavoriteMovie(singleMovie.id)){
                binding.likeBt.setClickable(false)
            }
        })





        binding.ivMoviePoster.setOnClickListener{
            model.setYtTitle(title+"Movie Trailer")

            Navigation.findNavController(it).navigate(R.id.action_movieDetailsFragment_to_webFragment)
        }


        binding.movieTitle.setOnClickListener {

            model.setUrl(title)
            //(activity as MainActivity).loadFragment(WebFragment())
            Navigation.findNavController(it).navigate(R.id.action_movieDetailsFragment_to_webFragment)
        }

        binding.likeBt.setOnClickListener {
            binding.likeBt.setImageResource(R.drawable.ic_like_star_24)
            binding.likeBt.setBackgroundColor(Color.parseColor("#F70D1A"))

            val saveMovie=FavoriteMovie(singleMovie.budget,singleMovie.homepage,singleMovie.id,
            singleMovie.original_language,singleMovie.title,singleMovie.overview,singleMovie.popularity
            ,singleMovie.poster_path,singleMovie.release_date,singleMovie.revenue,singleMovie.runtime
            ,singleMovie.tagline,singleMovie.title)

            moviesRepository.saveFavoriteMovie(requireContext(),saveMovie)
            binding.likeBt.setClickable(false)

        }


    return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}

