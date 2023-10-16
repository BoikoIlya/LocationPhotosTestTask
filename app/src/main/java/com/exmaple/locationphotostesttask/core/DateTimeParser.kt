package com.exmaple.locationphotostesttask.core

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
interface DateTimeParser {

    fun parseToDataAndTime(date: Long): Pair<String,String>

    class Base @Inject constructor(): DateTimeParser {

        override fun parseToDataAndTime(date: Long): Pair<String, String> {

            val instant = Instant.ofEpochSecond(date)

            val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())

            val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")

            val formattedDate = zonedDateTime.format(formatterDate)
            val formattedTime = zonedDateTime.format(formatterTime)
            return Pair(formattedDate,formattedTime)
        }
    }
}