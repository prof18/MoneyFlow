package di

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver
import data.db.DatabaseSource
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.ext.getOrCreateScope

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    val koin = koinApplication.koin

    koin.createScope<DatabaseSource>("databaseScope")

    val doOnStartup =
        koin.get<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
    doOnStartup.invoke()

    return koinApplication
}

fun Koin.recreateDatabaseScope() {
    val driverScope = getScope("driverScope")
    val databaseScope = getScope("databaseScope")
    driverScope.close()
    databaseScope.close()

    createScope<DatabaseSource>("databaseScope")
}

private val coreModule = module {

    // TODO: add database helper, repository, etc


    scope(named<DatabaseSource>()) {

//        val scope = getOrCreateScope("databaseScopeID")

        scoped<DatabaseSource> {
            val driverScope = getKoin().getOrCreateScope<SqlDriver>("driverScope")
            DatabaseSource(driverScope.get()) }
    }


//    single {
//
//
////
////        val scopeForA = getKoin().createScope<SqlDriver>("databaseScopre")
////
//////        val scope = getKoin().getScope(named<SqlDriver>().value)
////
////        val driver = scopeForA.get<SqlDriver>()
//
//        DatabaseSource(get())
//    }
//    single<KtorApi> {
//        DogApiImpl(
//            getWith("DogApiImpl")
//        )
//    }
}


internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module