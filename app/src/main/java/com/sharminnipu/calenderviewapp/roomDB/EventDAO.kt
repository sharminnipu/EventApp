package com.sharminnipu.calenderviewapp.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Query("SELECT * FROM event_info ORDER BY id DESC")
    fun getAllEvent():LiveData<List<Event>>

    @Query("SELECT * FROM event_info WHERE eventDate= :date")
    fun getEventByDate(date:String):LiveData<List<Event>>




    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("DELETE FROM EVENT_INFO")
    suspend fun deleteAllEvent()
}