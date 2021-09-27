package com.sharminnipu.calenderviewapp.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sharminnipu.calenderviewapp.getOrAwaitValue
import com.sharminnipu.calenderviewapp.roomDB.Event
import com.sharminnipu.calenderviewapp.roomDB.EventDatabase
import junit.framework.TestCase
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventViewModelTest :TestCase(){

    private lateinit var viewModel: EventViewModel
    private lateinit var database: EventDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp(){
       super.setUp()
        val context=ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context,EventDatabase::class.java).allowMainThreadQueries().build()

        viewModel= EventViewModel()
    }

    @After
    fun dbClose(){
        database.close()
    }

   @Test
   fun insertEvent(){
      val context=ApplicationProvider.getApplicationContext<Context>()
      var model= Event(0,"Birthday","2021-09-23","10:00 PM","birthday party")
       viewModel.insert(context,model)
     val result=  viewModel.getEventAll(context)?.getOrAwaitValue()?.find {

         it.eventTile=="Birthday"
     }

      assertThat(result!=null).isTrue()

  }

    @Test
    fun updateEvent(){
        val context=ApplicationProvider.getApplicationContext<Context>()
        var model= Event(1,"Birthday","2021-09-23","10:00 PM","birthday party khela")
        viewModel.insert(context,model)
        var modelUpdate=Event(1,"Birthday Party","2021-09-24","10:00 PM","Birthday Party")
        viewModel.update(context,modelUpdate)
        val result=  viewModel.getEventAll(context)?.getOrAwaitValue()

        assertThat(result).contains(modelUpdate)
    }

    @Test
    fun deleteEvent(){
        val context=ApplicationProvider.getApplicationContext<Context>()
        val model= Event(11,"hello","2021-09-30","10:05 PM","kisu kori na")
        viewModel.insert(context,model)
        viewModel.delete(context,model)
        val result=  viewModel.getEventAll(context)?.getOrAwaitValue()

        assertThat(result).doesNotContain(model)

    }









}

