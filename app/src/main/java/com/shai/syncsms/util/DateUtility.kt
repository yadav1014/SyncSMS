package com.shai.syncsms.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtility {
    fun longToPresentableDate(date: Long): String {
        val presentableTextPattern = "hh:mm a, MMM dd"
        val simpleDateFormat = SimpleDateFormat(presentableTextPattern)
        return simpleDateFormat.format( Date(date))
    }
}