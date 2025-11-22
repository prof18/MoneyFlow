package com.prof18.moneyflow.utilities

import com.prof18.moneyflow.database.DatabaseHelper

/**
 * Init driver for each platform. Should *always* be called to setup test
 */
expect fun createDriver()

/**
 * Close driver for each platform. Should *always* be called to tear down test
 */
expect fun closeDriver()

expect fun getDatabaseHelper(): DatabaseHelper
