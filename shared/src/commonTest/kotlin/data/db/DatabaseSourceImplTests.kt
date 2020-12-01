package data.db

import app.cash.turbine.test
import com.prof18.moneyflow.db.MoneyFlowDB
import kotlinx.coroutines.Dispatchers
import utilities.closeDriver
import utilities.createDriver
import utilities.getDb
import utilities.BaseTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DatabaseSourceImplTests: BaseTest()  {

    lateinit var database: MoneyFlowDB
    lateinit var databaseSource: DatabaseSource

    @BeforeTest
    fun setup() {
        createDriver()
        database = getDb()
        databaseSource = DatabaseSourceImpl(dbRef = database, dispatcher = Dispatchers.Main)
    }

    @AfterTest
    fun tearDown() {
        closeDriver()
    }

    @Test
    fun fakeTest() = runTest {


//        databaseSource.inser

        databaseSource.selectAllTransaction().test {
            print(this)
            assertEquals(true, true)
        }


    }


}