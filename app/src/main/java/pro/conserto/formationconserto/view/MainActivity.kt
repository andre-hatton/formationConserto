package pro.conserto.formationconserto.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import pro.conserto.database.FormationDatabase
import pro.conserto.database.module.databaseModule
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.module.appModule
import pro.conserto.formationconserto.tools.visible
import pro.conserto.formationconserto.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val _koinModules = listOf(appModule, databaseModule)

    private val _mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(_koinModules)

        _mainViewModel.loadingLiveData.observe(this) {
            main_loading.visible(it)
        }
    }

    override fun onDestroy() {
        unloadKoinModules(_koinModules)
        super.onDestroy()
    }
}