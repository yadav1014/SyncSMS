package com.shai.syncsms.util

import org.junit.runner.RunWith
import org.junit.runners.Suite

// Runs all unit tests.
@RunWith(Suite::class)
@Suite.SuiteClasses(StringUtilityTest::class,
    DateUtilityTest::class)
class UnitTestSuite
