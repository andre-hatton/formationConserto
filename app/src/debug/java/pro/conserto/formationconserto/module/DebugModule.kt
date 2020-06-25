package pro.conserto.formationconserto.module

import org.koin.dsl.module
import pro.conserto.formationconserto.tools.DatabaseUtil

val debugModule = module {
    factory { DatabaseUtil(get()) }
}