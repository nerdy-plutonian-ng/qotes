package com.plutoapps.qotes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date
import java.util.UUID

@Serializable
@Entity(tableName = "qotes")
data class Qote(
    @PrimaryKey
    val id: String,
    val quote: String,
    val author: String,
    val category: String,
    val date: String
) {

    fun shouldUpdateQote(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.YEAR] != Calendar.getInstance()[Calendar.YEAR] ||
                calendar[Calendar.DAY_OF_YEAR] != Calendar.getInstance()[Calendar.DAY_OF_YEAR]
    }
}