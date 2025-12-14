package com.prof18.moneyflow.utilities

import kotlin.test.Test
import kotlin.test.assertNotNull

internal class DatabaseHelperIosTest {

    @Test
    fun shouldInitializeAndCloseDatabaseHelper() {
        createDriver()
        try {
            assertNotNull(getDatabaseHelper())
        } finally {
            closeDriver()
        }
    }
}
