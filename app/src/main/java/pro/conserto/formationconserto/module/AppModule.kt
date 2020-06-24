package pro.conserto.formationconserto.module

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.conserto.formationconserto.repository.AnimeRepository
import pro.conserto.formationconserto.repository.MainRepository
import pro.conserto.formationconserto.viewmodel.AnimeViewModel
import pro.conserto.formationconserto.viewmodel.MainViewModel

val appModule = module {
    single { MainRepository(get()) }
    single { AnimeRepository(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { AnimeViewModel(get()) }
}