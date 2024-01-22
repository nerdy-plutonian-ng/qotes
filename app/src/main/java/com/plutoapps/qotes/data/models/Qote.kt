package com.plutoapps.qotes.data.models

import java.util.Calendar
import java.util.Date

data class Qote(val quote: String, val author: String, val category: String, val date: String = Calendar.getInstance().timeInMillis.toString()) {
    fun shouldUpdateQote(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.YEAR] != Calendar.getInstance()[Calendar.YEAR] ||
                calendar[Calendar.DAY_OF_YEAR] != Calendar.getInstance()[Calendar.DAY_OF_YEAR]
    }
}