package com.hussein.openweather.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// returns a string date of format 'Tuesday'
fun parseDate(date: String): String {
    val localDateTime = LocalDate.parse(date)
    val formatter = DateTimeFormatter.ofPattern("EEEE")

    return try {
        localDateTime.format(formatter)
    } catch (
        e: IllegalArgumentException
    ) {
        e.printStackTrace()
        "Thursday"
    }
}

fun parseToLocalTime(dateTime: String): String {
    val localDateTime = LocalDateTime.parse(dateTime)
    val formatter = DateTimeFormatter.ofPattern("hh a")
    return try {
        localDateTime.format(formatter).replace("^0".toRegex(), "")
    } catch (
        e: IllegalArgumentException
    ) {
        e.printStackTrace()
        "5:00AM"
    }
}

fun parseToLocalTime(dateTime: Long): String{
    val localDateTime = LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(dateTime), ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a , dd/MM/yyyy")
    return try {
        localDateTime.format(formatter)
    } catch (
        e: IllegalArgumentException
    ) {
        e.printStackTrace()
        "Error"
    }
}

fun compareDates(firstDate: String, secondDate: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val time1 = LocalDateTime.parse(firstDate, formatter)
    val time2 = LocalDateTime.parse(secondDate, formatter)
    val instant1 = time1.atZone(ZoneId.systemDefault()).toInstant()
    val instant2 = time2.atZone(ZoneId.systemDefault()).toInstant()
    return instant1.toEpochMilli() > instant2.toEpochMilli()
}