package com.prof18.moneyflow.database

import android.content.Context
import android.net.Uri
import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.db.DB_FILE_NAME_WITH_EXTENSION
import com.prof18.moneyflow.data.db.DATABASE_NAME
import com.prof18.moneyflow.data.db.DB_FILE_NAME
import com.prof18.moneyflow.domain.entities.DatabaseExportException
import com.prof18.moneyflow.domain.entities.DatabaseImportException
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream

class DBImportExportImpl(
    private val context: Context,
    private val errorMapper: MoneyFlowErrorMapper,
) : DBImportExport {

    override fun generateDatabaseFile(): File? {
        val inFileName: String = context.getDatabasePath(DATABASE_NAME).toString()
        try {
            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)
            context.openFileOutput(DB_FILE_NAME_WITH_EXTENSION, Context.MODE_PRIVATE)?.use { output ->
                // Transfer bytes from the input file to the output file
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }

                // Close the streams
                output.flush()
                fis.close()
                Logger.d { "Backup Completed" }

                return context.getFileStreamPath(DB_FILE_NAME_WITH_EXTENSION)
            }
            return null
        } catch (e: Exception) {
            Logger.e { "Error during the database export: $e" }
            return null
        }
    }

    override fun exportDatabaseToFileSystem(uri: Uri): MoneyFlowResult<Unit> {
        // Database path
        val inFileName: String = context.getDatabasePath(DATABASE_NAME).toString()
        try {
            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)

            val destPath: String = context.getExternalFilesDir(null)!!.absolutePath

            val folder = File(destPath + File.pathSeparator + DB_FILE_NAME)
            if (!folder.exists()) {
                folder.mkdirs()
            }

            context.contentResolver.openOutputStream(uri)?.use { output ->
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
                Logger.d { "Backup Completed" }
                return MoneyFlowResult.Success(Unit)
            }
            // TODO: check if this is triggered before the use
            val error = MoneyFlowError.DatabaseExport(DatabaseExportException())
            Logger.e { "Failure on opening the output stream" }
            return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        } catch (e: Exception) {
            Logger.e{ "Error during the database export. Error: $e"}
            val error = MoneyFlowError.DatabaseExport(e)
            return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }

    override fun importDatabaseFromFileSystem(uri: Uri): MoneyFlowResult<Unit> {
        val outFileName: String = context.getDatabasePath(DATABASE_NAME).toString()
        try {
            context.contentResolver.openInputStream(uri)?.use { fis ->
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
                Logger.d { "Database import completed" }
                return MoneyFlowResult.Success(Unit)
            }
            // TODO: check if this is called before the call to use
            val error = MoneyFlowError.DatabaseImport(DatabaseImportException())
            Logger.e { "Failure on opening the output stream" }
            return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        } catch (e: Exception) {
            Logger.e{ "Error during the database import. Error: $e"}
            val error = MoneyFlowError.DatabaseImport(e)
            return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }
}