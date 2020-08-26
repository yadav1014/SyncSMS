package com.shai.syncsms.util

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilityTest {

    @Test
    fun checkDateFormatting() {
        val fromLong = 1598430990562
        val toText = "02:06 PM, Aug 26"

        assertThat(DateUtility.longToPresentableDate(fromLong), `is`(toText))
    }
}