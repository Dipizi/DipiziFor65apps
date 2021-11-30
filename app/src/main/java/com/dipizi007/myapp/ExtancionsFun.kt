package com.dipizi007.myapp

import java.text.SimpleDateFormat
import java.util.*

fun String?.birthDayToMills(): Long {
    val calendar = GregorianCalendar.getInstance() as GregorianCalendar
    calendar.apply {
        time = SimpleDateFormat("dd-MM-yyyy").parse(this@birthDayToMills)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    if ((calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) && (calendar.get(Calendar.DAY_OF_MONTH) == 29)) {
        val year = calendar.get(Calendar.YEAR)
        if (isLeapYear(year)) {
            calendar.set(
                Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR)
                        + (4 - Calendar.getInstance().get(Calendar.YEAR) % 4)
            )
            calendar.set(Calendar.DAY_OF_MONTH, 29)
            return calendar.timeInMillis
        }
    }
    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
    if (calendar.timeInMillis <= System.currentTimeMillis()) {
        calendar.add(Calendar.YEAR, 1)
    }
    return calendar.timeInMillis
}

fun isLeapYear(year: Int): Boolean {
    var leap = false
    if (year % 4 == 0) {
        if (year % 100 == 0) {
            leap = year % 400 == 0
        } else
            leap = true
    } else
        leap = false
    return leap
}