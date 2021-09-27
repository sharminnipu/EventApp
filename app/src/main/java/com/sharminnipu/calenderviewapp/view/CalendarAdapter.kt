package com.sharminnipu.calenderviewapp.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sharminnipu.calenderviewapp.R

import com.sharminnipu.calenderviewapp.roomDB.Event
import kotlinx.android.synthetic.main.calendar_cell.view.*
import java.time.LocalDate

class CalendarAdapter(val dataList: ArrayList<LocalDate?>,val eventList:ArrayList<Event>): RecyclerView.Adapter<CalendarAdapter.FeedViewHolder>() {

    var onItemAction: ((model: LocalDate, position: Int) -> Unit)? = null
    var onEventAction:((model: Event)-> Unit)?=null

    var context: Context?=null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): FeedViewHolder {
        context=parent.context
        return FeedViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        val date: LocalDate? = dataList[position]
        Log.e("adapert",date.toString())

       // CalendarUtils.selectedDate= LocalDate.now()
      //  val eventData=eventList[position]

        for (event in eventList) {
            if (event.eventDate.equals(date.toString())){
                holder.tvDayText.setBackgroundColor(ContextCompat.getColor(context!!, R.color.teal_200))
               // holder.parentView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.teal_200))
                holder.tvEventText.visibility=View.VISIBLE
                holder.tvEventText.text=event.eventTile
                holder.tvEventText.setOnClickListener {

                    onEventAction?.invoke(event)

                }
            }
        }


       // val model=dataList[position]
        if(date == null)
            holder.tvDayText.text=""
        else
        {
            holder.tvDayText.text=date.dayOfMonth.toString()


            if(date.equals(LocalDate.now())){
               // holder.parentView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.teal_200))
               // holder.tvDayText.setBackgroundResource(R.drawable.bg_iv)
              holder.tvDayText.setBackgroundColor(ContextCompat.getColor(context!!, R.color.teal_200))
            }

        }




    }

    inner class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDayText=view.cellDayText
        val parentView=view.parentView
        val tvEventText=view.eventText



    }




}