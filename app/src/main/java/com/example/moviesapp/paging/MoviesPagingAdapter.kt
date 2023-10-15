package com.example.moviesapp.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesapp.ui.MainInterface

import com.example.moviesapp.R
import com.example.moviesapp.api.POSTER_BASE_URL

import com.example.moviesapp.models.Result

class MoviesPagingAdapter(private val context: Context, val listener: MainInterface):PagingDataAdapter<Result,MoviesPagingAdapter.PagingViewHolder>(comparator) {

    inner class PagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val moviePoster: ImageView =itemView.findViewById(R.id.movie_poster)
        val movieYear: TextView =itemView.findViewById(R.id.movie_year)
        val movieTitle: TextView =itemView.findViewById(R.id.movie_title)
        val movieDesc: TextView =itemView.findViewById(R.id.movie_desc)
        init {
            itemView.setOnClickListener {
                var position=adapterPosition
                if (position!=RecyclerView.NO_POSITION){


                    listener.onMovieItemClick(getItem(position)!!.id)
                }


            }
        }



    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        val item=getItem(position)

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
                .load(POSTER_BASE_URL+ item.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.moviePoster)





        }
       // holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.anim_1))


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.moviecard,parent,false)
        return PagingViewHolder(view)
    }


    companion object {
        val comparator = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }
}