package com.prof18.moneyflow.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {

    @Test
    fun generateCurrentMonthIdWorksCorrectly() {

        // That is Sun Nov 01 2020 20:57:54
        val millis: MillisSinceEpoch = 1604264274888L
        val monthID = millis.generateCurrentMonthId()

        assertEquals(monthID, 202011)
    }
}
