package com.sharminnipu.calenderviewapp.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDAO {

    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM event_info ORDER BY id DESC")
    fun getAllEvent():LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: ArrayList<Event>)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("DELETE FROM EVENT_INFO")
    suspend fun deleteAllEvent()

}