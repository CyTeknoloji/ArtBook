package com.caneryildirim.artbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.caneryildirim.artbook.R
import com.caneryildirim.artbook.adapter.RecyclerFeedAdapter
import com.caneryildirim.artbook.databinding.FragmentFeedBinding
import com.caneryildirim.artbook.room.Art
import com.caneryildirim.artbook.viewModel.FeedViewModel


class FeedFragment : Fragment(),RecyclerFeedAdapter.OptionFeed {
    private var _binding:FragmentFeedBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:FeedViewModel
    private lateinit var feedAdapter:RecyclerFeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view=binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.getArtFromRoom(this)
        observeLiveData()

        binding.recyclerFeed.layoutManager=LinearLayoutManager(requireContext())

        binding.fabAdd.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToDetailFragment(imageUrl = "")
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeLiveData() {
        viewModel.artListLive.observe(viewLifecycleOwner, Observer {
            feedAdapter= RecyclerFeedAdapter(it,this)
            binding.recyclerFeed.adapter=feedAdapter
        })
    }

    override fun onDelete(art: Art) {
        viewModel.deleteArtFromRoom(this,art)
    }
}