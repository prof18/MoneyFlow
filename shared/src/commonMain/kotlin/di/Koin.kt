package di

import data.DropboxSyncRepositoryImpl
import data.MoneyRepositoryImpl
import data.db.DatabaseSource
import data.db.DatabaseSourceImpl
import domain.repository.DropboxSyncRepository
import domain.repository.MoneyRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.addtransaction.AddTransactionUseCase
import presentation.addtransaction.AddTransactionUseCaseImpl
import presentation.categories.CategoriesUseCase
import presentation.categories.CategoriesUseCaseImpl
import presentation.dropboxsync.DropboxSyncUseCaseImpl
import presentation.dropboxsync.DropboxSyncUserCase
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

private val coreModule = module {

    single<DatabaseSource> {
        DatabaseSourceImpl(get(), Dispatchers.Main)
    }

    // Repository

    single<MoneyRepository> {
        MoneyRepositoryImpl(get())
    }

    single<DropboxSyncRepository> {
        DropboxSyncRepositoryImpl(get())
    }

    // Use Cases

    factory<HomeUseCase> {
        HomeUseCaseImpl(get())
    }

    factory<AddTransactionUseCase> {
        AddTransactionUseCaseImpl(get())
    }

    factory<CategoriesUseCase> {
        CategoriesUseCaseImpl(get())
    }

    factory<DropboxSyncUserCase> {
        DropboxSyncUseCaseImpl(get())
    }


}

expect val platformModule: Module
