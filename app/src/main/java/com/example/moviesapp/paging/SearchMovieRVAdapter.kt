package com.example.moviesapp.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesapp.R
import com.example.moviesapp.api.POSTER_BASE_URL
import com.example.moviesapp.models.Result
import com.example.moviesapp.ui.MainInterface

class SearchMovieRVAdapter(private val movielist: List<Result>, val listener: MainInterface, val context: Context) :
    RecyclerView.Adapter<SearchMovieRVAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
        val movieYear: TextView = itemView.findViewById(R.id.movie_year)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieDesc: TextView = itemView.findViewById(R.id.movie_desc)

        init {
            itemView.setOnClickListener {
                var position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {


                    listener.onMovieItemClick(movielist[position].id)
                }


            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.moviecard,parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item=movielist[position]

        if (position % 2 == 1) {
            holder.itemView.rotationY = 180f
            holder.moviePoster.rotationY = 180f
            holder.movieTitle.rotationY = 180f
            holder.movieDesc.rotationY = 180f
            holder.movieYear.rotationY = 180f
        }
        else {
            holder.itemView.rotationY = 0f
            holder.moviePoster.rotationY = 0f
            holder.movieTitle.rotationY = 0f
            holder.movieDesc.rotationY = 0f
            holder.movieYear.rotationY = 0f
        }


        if(item!=null){
            holder.movieDesc.text=item.overview

            holder.movieTitle.text=item.title
            holder.movieYear.text=item.release_date

            Glide
                .with(context)
                .load(POSTER_BASE_URL + item.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.moviePoster)





        }
    }

    override fun getItemCount(): Int {
        return movielist.size
    }
}