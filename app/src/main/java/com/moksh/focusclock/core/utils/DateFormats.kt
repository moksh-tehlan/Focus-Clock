package com.moksh.focusclock.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormats {

    fun formatSecondsToHour(seconds: Long): String {
        val hours = seconds / 3600
        return String.format("%02d", hours)
    }

    fun formatSecondsToMinute(seconds: Long): String {
        val minutes = (seconds % 3600) / 60
        return String.format("%02d", minutes)
    }

    fun formatSecondsToSeconds(seconds: Long): String {
        val remainingSeconds = seconds % 60
        return String.format("%02d", remainingSeconds)
    }

    fun millisToHour(timeMillis: Long): String {
        val formatter = SimpleDateFormat("hh", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }

    fun millisToMinute(timeMillis: Long): String {
        val formatter = SimpleDateFormat("mm", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }

    fun millisToSecond(timeMillis: Long): String {
        val formatter = SimpleDateFormat("ss", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }

    fun millisToAmPm(timeMillis: Long):String{
        val formatter = SimpleDateFormat("a", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }

    fun millisToDay(timeMillis: Long): String {
        val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return formatter.format(Date(timeMillis))
    }

}