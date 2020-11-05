package di

import com.prof18.moneyflow.db.MoneyFlowDB
import database.DatabaseHelper
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun initKoinIos(
    doOnStartup: () -> Unit
): KoinApplication = initKoin(
    module {
        single { doOnStartup }
    }
)

actual val platformModule = module {
//    single<MoneyFlowDB> {
//        DatabaseHelper.setupDatabase()
//        DatabaseHelper.instance
//    }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier, null)
}

fun Koin.getFromScope(objCClass: ObjCClass, scopeID: String): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    val scope = getScope(scopeID)
    return scope.get(kClazz)
}
