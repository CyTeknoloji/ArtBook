package com.caneryildirim.artbook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase:RoomDatabase() {
    abstract fun artDao():ArtDao

    companion object{
        @Volatile private var instance:ArtDatabase?=null

        private val lock=Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it
            }
        }

        private fun makeDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,ArtDatabase::class.java,"artdatabase").build()

    }
}