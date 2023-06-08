package com.example.casoscovidus.utils

import android.annotation.SuppressLint
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getDateTimeInstance
import java.text.NumberFormat.getNumberInstance
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Long.toDate(): String {
    val date = SimpleDateFormat("yyyyMMdd").parse(this.toString())
    return getDateInstance().format(date!!)
}

fun Long.formatWithSeparator(): String {
    return getNumberInstance().format(this)
}

fun Date.toDateTime(): String {
    return getDateTimeInstance().format(this)
}