package com.example.chatgeminiapp.data.local.room.converters

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimestampConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(value))
    }

    @TypeConverter
    fun toTimestamp(value: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            sdf.parse(value).time
        } catch (e: ParseException) {
            -1 // Or throw an exception if parsing fails
        }
    }
}