package com.example.moviesapp.paging

import android.content.Context
import android.graphics.drawable.Drawable

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.moviesapp.R
import com.example.moviesapp.api.POSTER_BASE_URL
import com.example.moviesapp.models.ResultX


class  ShowPagingAdapter(val context: Context):PagingDataAdapter<ResultX,ShowPagingAdapter.ShowVH>(comparator) {

    class ShowVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.series_name)
        val image=itemView.findViewById<ImageView>(R.id.series_image)
        val desc=itemView.findViewById<TextView>(R.id.series_description)
        val pbar=itemView.findViewById<ProgressBar>(R.id.series_progressbar)


    }

    override fun onBindViewHolder(holder: ShowVH, position: Int) {
        val item=getItem(position)
        if(item!=null){
            holder.name.text=item.name
            holder.desc.text=item.overview

            Glide
                .with(context)
                .load(POSTER_BASE_URL+ item.poster_path)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.image)
            holder.pbar.visibility=View.GONE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.show_item_card,parent,false)
        return ShowVH(view)
    }

    companion object {
        val comparator = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<ResultX>() {
            override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
                return oldItem == newItem
            }

        }
    }


}