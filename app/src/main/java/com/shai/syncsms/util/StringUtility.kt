package com.shai.syncsms.util

import java.util.regex.Matcher
import java.util.regex.Pattern

//Extracts all double from a string and appends to a string
fun String.extractDoubles(): String {
    var result = String()
    val regex = "[0-9]{1,13}(\\.[0-9]*)?"
//    val regex = "([0-9]+).([0-9]+)"
    val matcher: Matcher = Pattern.compile(regex).matcher(this)

    while (matcher.find()) {
        result += matcher.group() + " "
    }
    return result.trim()
}