package com.caneryildirim.artbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.caneryildirim.artbook.R
import com.caneryildirim.artbook.databinding.FragmentDetailBinding
import com.caneryildirim.artbook.room.Art
import com.caneryildirim.artbook.room.ArtDao
import com.caneryildirim.artbook.room.ArtDatabase
import com.caneryildirim.artbook.util.downloadUrl
import com.caneryildirim.artbook.util.snackbar
import com.caneryildirim.artbook.viewModel.DetailViewModel


class DetailFragment : Fragment() {
    private var _binding:FragmentDetailBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:DetailViewModel
    private var imageUrl:String=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentDetailBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(DetailViewModel::class.java)
        observeLiveData()

        arguments?.let {
            imageUrl=DetailFragmentArgs.fromBundle(it).imageUrl
        }

        if (imageUrl.isNotEmpty()){
            binding.imageArtDetail.downloadUrl(imageUrl)
        }

        binding.buttonSave.setOnClickListener {
            val artName=binding.editArtName.text.toString().trim()
            val artistName=binding.editArtistName.text.toString().trim()
            val year=binding.editYearName.text.toString().toIntOrNull()
            year?.let {
                val art=Art(artName,artistName,it,imageUrl)
                viewModel.saveArt(art,this)
            }

        }

        binding.imageArtDetail.setOnClickListener {
            val action=DetailFragmentDirections.actionDetailFragmentToImageApiFragment()
            Navigation.findNavController(it).navigate(action)
        }


    }

    private fun observeLiveData() {
        viewModel.uploadDataLive.observe(viewLifecycleOwner, Observer {
            if (it){

            }else{

            }
        })

        viewModel.errorDataLive.observe(viewLifecycleOwner, Observer {

        })
    }

}