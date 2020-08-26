package com.shai.syncsms.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*


@RunWith(JUnit4::class)
class StringUtilityTest {
    @Test
    fun checkExtractDoublesWithDouble() {
        val fromText = "This string has an amount double value of Rs 100.08"
        val toText = "100.08"

        Assert.assertEquals(fromText.extractDoubles(), toText)
    }

    @Test
    fun checkExtractDoublesWithInt() {
        val fromText = "This string has an amount in value of Rs 100"
        val toText = "100"
        Assert.assertEquals(fromText.extractDoubles(), toText)
    }

    @Test
    fun checkExtractDoublesWithoutNumber() {
        val fromText = "This string doen not have an amount double value."
        Assert.assertTrue(fromText.extractDoubles().isEmpty())
    }

    @Test
    fun checkExtractDoublesWithEmptyString() {
        val fromText = ""
        Assert.assertTrue(fromText.extractDoubles().isEmpty())
    }
}