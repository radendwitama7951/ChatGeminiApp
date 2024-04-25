package com.example.chatgeminiapp._common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimeStamp (timestamp: Long): String {
    return SimpleDateFormat(
        "HH:mm MM-dd-yyyy",
        Locale.getDefault()
    ).format(
        Date(
           timestamp
        )
    )
}