package com.caneryildirim.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.caneryildirim.artbook.databinding.RecyclerImageRowBinding
import com.caneryildirim.artbook.model.ImageResult
import com.caneryildirim.artbook.util.downloadUrl
import com.caneryildirim.artbook.view.ImageApiFragmentDirections

class RecyclerImageApiAdapter(val imageResultList:ArrayList<ImageResult>):RecyclerView.Adapter<RecyclerImageApiAdapter.ImageApiHolder>() {

    class ImageApiHolder(val binding:RecyclerImageRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageApiHolder {
        val binding=RecyclerImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageApiHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageResultList.size
    }

    override fun onBindViewHolder(holder: ImageApiHolder, position: Int) {
        holder.binding.imageFromApi.downloadUrl(imageResultList[position].previewURL)
        holder.itemView.setOnClickListener {
            val action=ImageApiFragmentDirections.actionImageApiFragmentToDetailFragment(imageResultList[position].previewURL)
            Navigation.findNavController(it).navigate(action)
        }
    }
}