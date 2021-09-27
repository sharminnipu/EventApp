package com.sharminnipu.calenderviewapp.roomDB

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "event_info")
@Parcelize
data class Event(
        @PrimaryKey
        @ColumnInfo(name="id")
         val id:String,
        @ColumnInfo(name="eventTile")
        val eventTile:String,
        @ColumnInfo(name="eventDate")
        val eventDate:String,
        @ColumnInfo(name="eventTime")
        val eventTime:String,
        @ColumnInfo(name="eventDetails")
        val eventDetails:String
        ): Parcelable
