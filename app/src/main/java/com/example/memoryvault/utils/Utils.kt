package com.example.memoryvault.utils

import android.content.Context
import android.widget.Toast
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {

    fun convertLongToDateString(timeInMillis: Long, formatPattern : String): String{
        // 1. Convert the Long (milliseconds since epoch) to an Instant
        val instant = Instant.ofEpochMilli(timeInMillis)

        // 2. Define the time zone (use the system default or a specific one)
        val zoneId = ZoneId.systemDefault()

        // 3. Convert the Instant to a ZonedDateTime in the specified time zone
        val zonedDateTime = instant.atZone(zoneId)

        // 4. Define the desired output format using DateTimeFormatter
        // Use Locale.getDefault() to respect the user's regional settings
        val formatter = DateTimeFormatter.ofPattern(formatPattern, Locale.getDefault())

        // 5. Format the ZonedDateTime to a String
        return zonedDateTime.format(formatter)
    }

    fun Context.showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}