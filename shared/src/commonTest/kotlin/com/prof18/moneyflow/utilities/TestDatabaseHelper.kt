package com.prof18.moneyflow.utilities

import com.prof18.moneyflow.database.DatabaseHelper

/**
 * Init driver for each platform. Should *always* be called to setup test
 */
internal expect fun createDriver()

/**
 * Close driver for each platform. Should *always* be called to tear down test
 */
internal expect fun closeDriver()

internal expect fun getDatabaseHelper(): DatabaseHelper
