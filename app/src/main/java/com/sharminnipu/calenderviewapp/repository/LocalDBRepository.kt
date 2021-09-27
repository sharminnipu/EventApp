package com.sharminnipu.calenderviewapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.sharminnipu.calenderviewapp.roomDB.Event
import com.sharminnipu.calenderviewapp.roomDB.EventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LocalDBRepository {

    companion object{

        private  var eventDatabase:EventDatabase?=null

        fun initialDB(context: Context):EventDatabase?{

            return EventDatabase.getInstance(context)
        }

        fun insert(context: Context,event: Event){

            eventDatabase= initialDB(context)
            CoroutineScope(IO).launch {
                eventDatabase?.getDao()?.insert(event)
            }
        }

        fun update(context: Context,event: Event){

            eventDatabase= initialDB(context)
            CoroutineScope(IO).launch {
                eventDatabase?.getDao()?.update(event)
            }
        }

        fun delete(context: Context,event: Event){

            eventDatabase= initialDB(context)
            CoroutineScope(IO).launch {
                eventDatabase?.getDao()?.delete(event)
            }
        }

        fun deleteAll(context: Context){

            eventDatabase= initialDB(context)
            CoroutineScope(IO).launch {
                eventDatabase?.getDao()?.deleteAllEvent()
            }
        }

        fun getEventAll(context: Context): LiveData<List<Event>>? {
            eventDatabase= initialDB(context)
            return eventDatabase?.getDao()?.getAllEvent()

        }


    }
}