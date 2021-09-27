package com.sharminnipu.calenderviewapp.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Event::class],version = 1,exportSchema = false)
abstract class EventDatabase :RoomDatabase() {

    abstract fun getDao():EventDAO

    companion object{
        private const val DATABASE_NAME="EventDatabase"

        @Volatile
        var instance:EventDatabase?=null

        fun getInstance(context:Context):EventDatabase?{
            if(instance==null){
                synchronized(EventDatabase::class.java){
                    if(instance==null){
                        instance=Room.databaseBuilder(context,EventDatabase::class.java,
                            DATABASE_NAME).build()
                    }
                }

            }

            return instance

        }




    }
}