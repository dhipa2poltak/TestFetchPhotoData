package com.dpfht.testfetchphotodata.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testfetchphotodata.R
import com.dpfht.testfetchphotodata.databinding.PhotoItemBinding
import com.dpfht.testfetchphotodata.model.Photo
import com.squareup.picasso.Picasso

class PhotoAdapter(private val photos: MutableList<Photo>, private val picasso: Picasso): RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
    val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return PhotoHolder(binding)
  }

  override fun getItemCount(): Int {
    return photos.size
  }

  override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
    holder.bindData(photos[position])
  }

  fun swapData(photos: List<Photo>) {
    this.photos.clear()
    this.photos.addAll(photos)
    notifyDataSetChanged()
  }

  inner class PhotoHolder(private val binding: PhotoItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(photo: Photo) {
      binding.title.text = photo.title

      picasso.load(photo.thumbnailUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(R.drawable.loading)
        .into(binding.image)
    }
  }
}
