package com.prof18.moneyflow.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import com.prof18.moneyflow.database.DatabaseHelper
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream


class DatabaseImportExport(
    private val context: Context
) {

    fun generateDatabaseFile(): File? {
        val inFileName: String = context.getDatabasePath(DatabaseHelper.DATABASE_NAME).toString()
        try {
            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)
            context.openFileOutput("MoneyFlow.db", MODE_PRIVATE)!!.use { output ->
                // Transfer bytes from the input file to the output file
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }

                // Close the streams
                output.flush()
                output.close()
                fis.close()
                Timber.d("Backup Completed")

                return context.getFileStreamPath("MoneyFlow.db")
            }

        } catch (e: Exception) {
            Timber.e("Error during the com.prof18.moneyflow.database export")
            e.printStackTrace()
            return null
        }
    }

    fun exportToMemory(uri: Uri) {

        //com.prof18.moneyflow.database path
        val inFileName: String = context.getDatabasePath(DatabaseHelper.DATABASE_NAME).toString()
        try {
            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)

            //getExternalFilesDir()

            val destPath: String = context.getExternalFilesDir(null)!!.absolutePath

            val folder = File(destPath + File.pathSeparator + "MoneyFlow")
            if (!folder.exists()) {
                folder.mkdirs()
            }

            context.contentResolver.openOutputStream(uri)!!.use { output ->
                // Transfer bytes from the input file to the output file
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }

                // Close the streams
                output.flush()
                output.close()
                fis.close()
                Timber.d("Backup Completed")
            }
        } catch (e: Exception) {
            Timber.e("Error during the com.prof18.moneyflow.database export")
            e.printStackTrace()
        }
    }

    fun importFromMemory(uri: Uri) {
        val outFileName: String = context.getDatabasePath(DatabaseHelper.DATABASE_NAME).toString()
        try {
            context.contentResolver.openInputStream(uri)!!.use { fis ->
                // Open the empty db as the output stream
                val output: OutputStream = FileOutputStream(outFileName)

                // Transfer bytes from the input file to the output file
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }

                // Close the streams
                output.flush()
                output.close()
                fis.close()
                Timber.d("Database import completed")
            }
        } catch (e: Exception) {
            Timber.e("Unable to import com.prof18.moneyflow.database")
            e.printStackTrace()
        }
    }

}