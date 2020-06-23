package pro.conserto.formationconserto.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.conserto.formationconserto.viewmodel.MainViewModel

val appModule = module {
    viewModel { MainViewModel() }
}