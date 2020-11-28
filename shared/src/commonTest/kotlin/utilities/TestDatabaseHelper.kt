package utilities

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver
import data.db.Schema
import data.db.createQueryWrapper




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
