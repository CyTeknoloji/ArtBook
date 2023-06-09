package com.caneryildirim.artbook.model

import com.google.gson.annotations.SerializedName

data class ImageResult(
    val comments:Int,
    val downloads:Int,
    val id:Int,
    val imageHeight:Int,
    val imageSize:Int,
    val imageWidth:Int,
    val imageURL:String,
    val largeImageURL:String,
    val likes:Int,
    val pageURL:String,
    val previewHeight:Int,
    val previewURL:String,
    val previewWidth:Int,
    val tags:String,
    val type:String,
    val user:String,
    @SerializedName("user_id")
    val userId:Int,
    val userImageURL:String,
    val views:Int,
    val webformatHeight:Int,
    val webformatURL:String,
    val webformatWidth:Int,
    val fullHDURL:String
)
