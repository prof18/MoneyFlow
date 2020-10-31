import android.util.Log

actual fun debugLog(tag: String, message: String) {
    Log.d(tag, message)
}