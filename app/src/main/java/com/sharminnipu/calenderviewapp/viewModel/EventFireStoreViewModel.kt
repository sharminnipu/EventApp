package com.sharminnipu.calenderviewapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.sharminnipu.calenderviewapp.model.FirebaseEvent
import com.sharminnipu.calenderviewapp.repository.FirestoreRepository

import com.sharminnipu.calenderviewapp.roomDB.Event

class EventFireStoreViewModel :ViewModel() {

    var saveItemEvent: MutableLiveData<List<FirebaseEvent>> = MutableLiveData()

    //insert and update
    fun insertFireStore(event: Event){

        FirestoreRepository.saveEventItem(event)

    }

    //getEvent
    fun getAllEvent():LiveData<List<FirebaseEvent>>?{
        FirestoreRepository.getEventItem().addSnapshotListener(EventListener<QuerySnapshot>{value, error ->

            Log.e("hello chekc",value.toString())

            if(error!=null){
                saveItemEvent.value=null
                return@EventListener
            }
            var eventItemList : MutableList<FirebaseEvent> = mutableListOf()

            for (doc in value!!){

                var eventItem=doc.toObject(FirebaseEvent::class.java)
                eventItemList.add(eventItem)
            }
            saveItemEvent.value=eventItemList

        })
       return  saveItemEvent
     }

    //delete
    fun deleteFireStore(event: Event){
       FirestoreRepository.deleteEventItem(event)
    }



}