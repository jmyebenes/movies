package dev.jmyp.movies.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Formatter {
    fun getYearFromDate(date: String?): String {
        return try {
            val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
            localDate.year.toString()
        } catch (e: DateTimeParseException) {
            date ?: ""
        }
    }

    fun formatVote(vote: Double?): String {
        return String.format("%.1f", vote)
    }
}