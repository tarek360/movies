package com.tarek360.movies.ui.moviedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tarek360.movies.R
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosRecyclerViewAdapter :
    RecyclerView.Adapter<PhotosRecyclerViewAdapter.PhotoViewHolder>() {

    private val items = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(items[position])
            .placeholder(ColorDrawable(Color.GRAY))
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount() = items.size

    fun addItems(items: List<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.photoImageView
    }
}
