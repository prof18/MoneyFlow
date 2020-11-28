package utilities

import kotlinx.coroutines.CoroutineScope

expect abstract class BaseTest {
    fun <T> runTest(block: suspend CoroutineScope.() -> T)
}