package com.sharminnipu.calenderviewapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sharminnipu.calenderviewapp.repository.LocalDBRepository
import com.sharminnipu.calenderviewapp.roomDB.Event

class EventViewModel : ViewModel()  {

    fun insert(context: Context,event:Event):Boolean{

        LocalDBRepository.insert(context,event)
        return true
    }
    fun update(context: Context,event:Event):Boolean{
        LocalDBRepository.update(context,event)
        return true
    }

    fun delete(context: Context,event:Event):Boolean{
        LocalDBRepository.delete(context,event)
        return true
    }

    fun getEventAll(context: Context): LiveData<List<Event>>? {

        return LocalDBRepository.getEventAll(context)
    }


}