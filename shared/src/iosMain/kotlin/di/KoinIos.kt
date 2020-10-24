package di

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver
import di.initKoin
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named

fun initKoinIos(
    doOnStartup: () -> Unit
): KoinApplication = initKoin(
    module {
        single { doOnStartup }
    }
)

actual val platformModule = module {
    scope(named<SqlDriver>()) {
        scoped<SqlDriver> { NativeSqliteDriver(MoneyFlowDB.Schema, "MoneyFlowDB") }
    }
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
