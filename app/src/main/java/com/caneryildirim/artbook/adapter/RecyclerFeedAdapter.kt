package com.caneryildirim.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.caneryildirim.artbook.databinding.RecyclerFeedRowBinding
import com.caneryildirim.artbook.room.Art
import com.caneryildirim.artbook.util.downloadUrl

class RecyclerFeedAdapter(val artList:ArrayList<Art>,val optionFeed:OptionFeed):RecyclerView.Adapter<RecyclerFeedAdapter.ArtHolder>() {
    interface OptionFeed{
        fun onDelete(art: Art)
    }

    class ArtHolder(val binding:RecyclerFeedRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val binding=RecyclerFeedRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        holder.binding.imageArt.downloadUrl(artList[position].imageUrl)
        holder.binding.textArtName.text=artList[position].artName
        holder.binding.textArtistName.text=artList[position].artistName
        holder.binding.textYear.text=artList[position].year.toString()

        holder.itemView.setOnLongClickListener {
            optionFeed.onDelete(artList[position])
            return@setOnLongClickListener true
        }

    }

    fun updateData(newArtList:List<Art>){
        artList.clear()
        artList.addAll(newArtList)
        notifyDataSetChanged()
    }

}