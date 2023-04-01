package com.caneryildirim.artbook.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    @ColumnInfo("name")
    val artName:String,
    @ColumnInfo("artist")
    val artistName:String,
    @ColumnInfo("year")
    val year:Int,
    @ColumnInfo("url")
    val imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null
)
