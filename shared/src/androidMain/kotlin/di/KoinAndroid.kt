package di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // TODO: add platform specific stuff

//    single<SqlDriver> {
//        AndroidSqliteDriver(
//            KaMPKitDb.Schema,
//            get(),
//            "KampkitDb"
//        )
//    }

//    single<Settings> {
//        AndroidSettings(get())
//    }


}