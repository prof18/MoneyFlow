package com.prof18.moneyflow.utilities

import com.prof18.moneyflow.db.MoneyFlowDB

/**
 * Init driver for each platform. Should *always* be called to setup test
 */
expect fun createDriver()

/**
 * Close driver for each platform. Should *always* be called to tear down test
 */
expect fun closeDriver()

/**
 * Platform specific access to HockeyDb
 */
expect fun getDb(): MoneyFlowDB
