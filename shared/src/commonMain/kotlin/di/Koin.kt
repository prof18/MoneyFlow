package di

import data.MoneyRepositoryImpl
import data.db.DatabaseSource
import domain.repository.MoneyRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.home.HomeUseCase
import presentation.home.HomeUseCaseImpl

fun initKoin(appModule: Module): KoinApplication {
    return startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }
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

    single<DatabaseSource> {
        DatabaseSource(get(), Dispatchers.Default)
    }

    single<MoneyRepository> {
        MoneyRepositoryImpl(get())
    }

    factory<HomeUseCase> {
        HomeUseCaseImpl(get())
    }
}

expect val platformModule: Module
