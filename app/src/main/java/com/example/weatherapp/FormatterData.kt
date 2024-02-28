package com.example.weatherapp

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getFormatterDateForTime(date: String, format: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse(date, formatter)
    return dateTime.format(DateTimeFormatter.ofPattern(format))
}

fun getFormatterDateForDay(date: String): String {
    val dateTime = LocalDate.parse(date)
    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    return dateTime.format(formatter)
}

