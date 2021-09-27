package com.sharminnipu.calenderviewapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sharminnipu.calenderviewapp.util.CalendarUtils.daysInWeekArray
import com.sharminnipu.calenderviewapp.util.CalendarUtils.monthYearFromDate
import com.sharminnipu.calenderviewapp.util.CalendarUtils.selectedDate
import com.sharminnipu.calenderviewapp.R
import com.sharminnipu.calenderviewapp.repository.FirestoreRepository
import com.sharminnipu.calenderviewapp.util.Util.isUpdateEvent
import com.sharminnipu.calenderviewapp.roomDB.Event
import com.sharminnipu.calenderviewapp.viewModel.EventFireStoreViewModel
import com.sharminnipu.calenderviewapp.viewModel.EventViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.event_details_dialog.view.*
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    lateinit var adapter: CalendarAdapter
    private lateinit var viewModel:EventViewModel
    private lateinit var viewModelFireBase: EventFireStoreViewModel
    private lateinit var eventList:ArrayList<Event>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialView()
        fetchLocalAllEvent()

    }

    private fun initialView(){
       viewModelFireBase= ViewModelProvider(this).get(EventFireStoreViewModel::class.java)
        selectedDate = LocalDate.now()
         eventList=ArrayList<Event>()
        Log.e("selected date", selectedDate.toString())


     //  var event=Event(0,"density","2021-09-24","12:00PM","hello dentise")
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddEventActivity::class.java))
        }


    }

    private fun  setWeekView(eventList:ArrayList<Event>){
        monthYearTV.text=monthYearFromDate(selectedDate!!)
        var days:ArrayList<LocalDate?> =daysInWeekArray(selectedDate!!)

        adapter = CalendarAdapter(days,eventList)
        adapter.notifyDataSetChanged()

        val llm = GridLayoutManager(this, 7)
        llm.orientation = GridLayoutManager.VERTICAL
        calendarRecyclerView.layoutManager = llm
        calendarRecyclerView.adapter = adapter

        adapter.onEventAction={model ->

           OpenDialog(model)

        }

    }
    private fun  OpenDialog(model:Event) {

        val view = LayoutInflater.from(this).inflate(R.layout.event_details_dialog, null)
        // val view=View.inflate(this@DoctorAppointmentActivity,R.layout.payment_ask_alert_dialog,null)
        val builder = android.app.AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.closeBtn.setOnClickListener {
            dialog.dismiss()
        }
        view.tvEventTitle.text=model.eventTile
        view.tvEventDate.text=model.eventDate
        view.tvEventTime.text=model.eventTime
        if(model.eventDetails.isNotEmpty()){

            view.tvEventDes.text=model.eventDetails

        }else{
            view.tvEventDesLayout.visibility=View.GONE
        }
        view.updateBtn.setOnClickListener {
            dialog.dismiss()
            isUpdateEvent=true

            startActivity(Intent(this, AddEventActivity::class.java).putExtra("action",model))
        }
        view.deleteBtn.setOnClickListener {

            dialog.dismiss()
            onDeleteAlertDialog(view,model)


        }



    }

    private fun onDeleteAlertDialog(view: View,model:Event) {
            //Instantiate builder variable
            val builder = AlertDialog.Builder(view.context)

            // set title
            builder.setTitle("Event Delete")

            //set content area
            builder.setMessage("Are you sure?\nYou want to delete the event ")

            //set negative button
            builder.setPositiveButton("Yes") { dialog, id ->
                //delete from roomDb
                var deleteData=viewModel.delete(this,model)
                if(deleteData){
                    //delete from recycleView
                    eventList.remove(model)
                    adapter.notifyDataSetChanged()

                    //delete from firestore
                    viewModelFireBase.deleteFireStore(model)

                    Toast.makeText(this,"your event deleted successfully!!",Toast.LENGTH_SHORT).show()
                }



            }

            //set positive button
            builder.setNegativeButton("No") { dialog, id ->
                // User cancelled the dialog
                dialog.dismiss()

            }

            builder.show()


    }

    private fun fetchLocalAllEvent(){

        viewModel=ViewModelProvider(this).get(EventViewModel::class.java)
        viewModel.getEventAll(applicationContext)?.observe(this, Observer {

            if(it.isNotEmpty()){
                emptyMsg.visibility=View.GONE
                eventList.addAll(it as ArrayList<Event>)
                Log.e("eventList",eventList.toString())
            }
            else
             {
                 emptyMsg.visibility=View.VISIBLE
             }

            setWeekView(eventList)

        })
    }

    fun previousWeekAction(view: View) {

        selectedDate = selectedDate!!.minusWeeks(1)
        setWeekView(eventList)
    }
    fun nextWeekAction(view: View) {
        selectedDate = selectedDate!!.plusWeeks(1)
        setWeekView(eventList)
    }


}