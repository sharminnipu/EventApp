package com.sharminnipu.calenderviewapp.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FirebaseEvent (
    var id:String,

    var eventTile:String,

     var eventDate:String,

     var eventTime:String,

     var eventDetails:String
 ): Serializable{

     constructor():this("","","","","")

    fun  getEvet():String{
        var event="$id,$eventTile,$eventDate,$eventTime,$eventDetails"
        return event
    }

 }