package com.caneryildirim.artbook.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.caneryildirim.artbook.R
import com.caneryildirim.artbook.adapter.RecyclerImageApiAdapter
import com.caneryildirim.artbook.databinding.FragmentImageApiBinding
import com.caneryildirim.artbook.model.ImageResponse
import com.caneryildirim.artbook.model.ImageResult
import com.caneryildirim.artbook.util.gone
import com.caneryildirim.artbook.util.visible
import com.caneryildirim.artbook.viewModel.ImageApiViewModel


class ImageApiFragment : Fragment() {
    private var _binding:FragmentImageApiBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:ImageApiViewModel
    private var imageAdapter:RecyclerImageApiAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentImageApiBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(ImageApiViewModel::class.java)
        viewModel.getImageFromRetrofit("")
        observeLiveData()

        binding.recyclerImage.layoutManager=GridLayoutManager(requireContext(),3)

        binding.editSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.getImageFromRetrofit(s.toString())
            }

        })

    }

    private fun observeLiveData() {
        viewModel.imageResponseLive.observe(viewLifecycleOwner, Observer {imageResponse->
            imageAdapter= RecyclerImageApiAdapter(ArrayList(imageResponse.hits))
            binding.recyclerImage.adapter=imageAdapter
        })

        viewModel.uploadDataLive.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.progressBarImageApi.visible()
            }else{
                binding.progressBarImageApi.gone()
            }
        })

    }


}