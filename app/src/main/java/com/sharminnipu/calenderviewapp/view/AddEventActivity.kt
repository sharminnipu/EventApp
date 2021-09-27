package com.sharminnipu.calenderviewapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sharminnipu.calenderviewapp.R
import com.sharminnipu.calenderviewapp.repository.FirestoreRepository
import com.sharminnipu.calenderviewapp.util.Util
import com.sharminnipu.calenderviewapp.roomDB.Event
import com.sharminnipu.calenderviewapp.viewModel.EventFireStoreViewModel
import com.sharminnipu.calenderviewapp.viewModel.EventViewModel
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : AppCompatActivity() {
    private lateinit var viewModel: EventViewModel
    private lateinit var viewModelFireBase: EventFireStoreViewModel
    private  var data:Event?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        initialView()


    }

    fun initialView(){
        viewModel= ViewModelProvider(this).get(EventViewModel::class.java)
        viewModelFireBase= ViewModelProvider(this).get(EventFireStoreViewModel::class.java)
        if(Util.isUpdateEvent){

            data=intent.getParcelableExtra<Event>("action")
            Log.e("update",data.toString())
            setupData(data!!)
        }
        backBtn.setOnClickListener {
            finish()
        }
        saveBtn.setOnClickListener {
            if(Util.isUpdateEvent){
                updateDataToLocalDB(data!!)
            }else{
                saveDataToLocalDB()
            }

        }

        dateAndTimePickerDialog()


    }

    private fun updateDataToLocalDB(data:Event) {
        val getDate=etEventDate.text.toString().trim()
        val getTime=etEventTime.text.toString().trim()
        val getTitle=etEventTitle.text.toString().trim()
        val getEventDes=etEventDes.text.toString().trim()

        val updateEvent=Event(data.id,getTitle,getDate,getTime,getEventDes)
        if(!TextUtils.isEmpty(getDate) && !TextUtils.isEmpty(getTime) && !TextUtils.isEmpty(getTitle)){

               //update in roomDB
            var update= viewModel.update(this,updateEvent)
            if (update){
                //for update firebase
                viewModelFireBase.insertFireStore(updateEvent)
                Toast.makeText(this,"Your event updated successfully!!",Toast.LENGTH_LONG).show()
                finish()
            }


        }else
        {
            Toast.makeText(this,"Please fill up the flied",Toast.LENGTH_LONG).show()
        }
    }

    fun setupData(data:Event){
        saveBtn.text="Update"
        titleEventLayout.text="Update Your event"
        etEventDate.setText(data.eventDate)
        etEventTime.setText(data.eventTime)
        etEventTitle.setText(data.eventTile)
        etEventDes.setText(data.eventDetails)


    }

    fun dateAndTimePickerDialog(){

        val myCalendar = Calendar.getInstance()

        val datePickerListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                //  val myFormat = "hh:mm a"
                //  val myFormat = "dd/MM/yyyy" //In which you need put here
                //  val sdf = SimpleDateFormat(myFormat, Locale.getDefault()).format(Date())
                etEventDate.setText(SimpleDateFormat("yyyy-MM-dd").format(myCalendar.time))
            }
        etEventDate.setOnClickListener {
            DatePickerDialog(this, datePickerListener, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]).show()

        }


        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            etEventTime.setText(
                SimpleDateFormat("hh:mm a").format(myCalendar.time)
            )
        }
        etEventTime.setOnClickListener {
            TimePickerDialog(this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(
                Calendar.MINUTE), false).show()


        }

    }

     fun saveDataToLocalDB(){
        val id= FirestoreRepository.firebaseDB.collection("event_info").document().id
        val getDate=etEventDate.text.toString().trim()
        val getTime=etEventTime.text.toString().trim()
        val getTitle=etEventTitle.text.toString().trim()
        val getEventDes=etEventDes.text.toString().trim()

        var sendEvent=Event(id,getTitle,getDate,getTime,getEventDes)

        if(!TextUtils.isEmpty(getDate) && !TextUtils.isEmpty(getTime) && !TextUtils.isEmpty(getTitle)) {

             //insert for roomDB
            var inserted = viewModel.insert(this, sendEvent)
            if (inserted) {
                //insert for firebase
                viewModelFireBase.insertFireStore(sendEvent)
                Toast.makeText(this, "Your event inserted successfully!!", Toast.LENGTH_LONG).show()
                finish()
            }

        } else
        {
            Toast.makeText(this,"Please fill up the flied",Toast.LENGTH_LONG).show()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if(Util.isUpdateEvent){
            Util.isUpdateEvent =false
        }

    }

}