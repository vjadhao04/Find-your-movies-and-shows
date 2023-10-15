package com.example.moviesapp.utils


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesapp.api.POSTER_BASE_URL


@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url:String){
    Glide.with(this.context)
        .load(POSTER_BASE_URL + url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)

}

@BindingAdapter("setTextFromInt")
fun TextView.setTextFromOInt(value:Int){
    this.text=value.toString()

}
@BindingAdapter("setTextFromDouble")
fun TextView.setTextFromDouble(value:Double){
    this.text=value.toString()

}