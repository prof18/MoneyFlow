package di

import com.squareup.sqldelight.db.SqlDriver
import data.MoneyRepositoryImpl
import data.db.DatabaseSource
import domain.repository.MoneyRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import presentation.home.HomeUseCase
import presentation.home.HomeUseCaseImpl

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


    // If we want to don't want to use scopes
/*    single<DatabaseSource> {
        DatabaseSource(get(), Dispatchers.Default)
    }

    single<MoneyRepository> {
        MoneyRepositoryImpl(get())
    }

    single<HomeUseCase> {
        HomeUseCaseImpl(get())
    }*/

    // For closing the database at runtime
    scope(named<DatabaseSource>()) {

        scoped<DatabaseSource> {
            val driverScope = getKoin().getOrCreateScope<SqlDriver>("driverScope")
            DatabaseSource(driverScope.get(), Dispatchers.Default)
        }

        scoped<MoneyRepository> {
            MoneyRepositoryImpl(get())
        }

        scoped<HomeUseCase> {
            HomeUseCaseImpl(get())
        }
    }
}

expect val platformModule: Module