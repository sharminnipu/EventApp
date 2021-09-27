package com.sharminnipu.calenderviewapp.repository

import android.telecom.Conference
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sharminnipu.calenderviewapp.roomDB.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirestoreRepository {

    companion object{
        val firebaseDB=FirebaseFirestore.getInstance()

        fun saveEventItem(event:Event) {

        val result = firebaseDB.collection("event_info").document(event.id)

        CoroutineScope(Dispatchers.IO).launch {
            result.set(event)
        }

    }

        fun deleteEventItem(event: Event){
            val result = firebaseDB.collection("event_info").document(event.id)

            CoroutineScope(Dispatchers.IO).launch {
                result.delete()
            }

        }

        //get data

        fun getEventItem():CollectionReference{

            var eventList= firebaseDB.collection("event_info")

            return eventList

        }








    }

}