package com.asofttz.logging

import kotlin.test.BeforeTest

class LoggerTest {
    @BeforeTest
    fun init() {
        val Log = Logger("Products")
        Log.d("This is a debug test")
    }
}
