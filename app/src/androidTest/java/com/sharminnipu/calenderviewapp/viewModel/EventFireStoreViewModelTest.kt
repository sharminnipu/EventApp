package com.sharminnipu.calenderviewapp.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import com.sharminnipu.calenderviewapp.getOrAwaitValue
import com.sharminnipu.calenderviewapp.repository.FirestoreRepository
import com.sharminnipu.calenderviewapp.roomDB.Event
import com.sharminnipu.calenderviewapp.roomDB.EventDatabase
import junit.framework.TestCase
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventFireStoreViewModelTest :TestCase() {

    private lateinit var viewModel: EventFireStoreViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    public override fun setUp(){
        super.setUp()
        viewModel= EventFireStoreViewModel()
    }
    @Test
    fun insertFireStore() {
        val id= FirestoreRepository.firebaseDB.collection("event_info").document().id
        var model= Event(id,"Birthday","2021-09-23","10:00 PM","birthday party")
         viewModel.insertFireStore(model)
         val save=viewModel.getAllEvent()?.getOrAwaitValue()?.find {

             it.eventTile=="Birthday"
         }

        // var save= FirestoreRepository.getEventItem()

        assertThat(save!=null).isTrue()


    }

    @Test
    fun deleteFireStore() {
        val id= FirestoreRepository.firebaseDB.collection("event_info").document().id
        var model= Event(id,"Birthday","2021-09-23","10:00 PM","birthday party")
        viewModel.insertFireStore(model)
        viewModel.deleteFireStore(model)
        val result=viewModel.getAllEvent()?.getOrAwaitValue()

        assertThat(result).doesNotContain(model)
    }
}