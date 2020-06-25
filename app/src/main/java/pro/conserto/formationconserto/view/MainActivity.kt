package pro.conserto.formationconserto.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import pro.conserto.database.module.databaseModule
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.module.appModule
import pro.conserto.formationconserto.service.MainWorker
import pro.conserto.formationconserto.tools.visible
import pro.conserto.formationconserto.viewmodel.MainViewModel
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val _koinModules = listOf(appModule, databaseModule)

    private val _mainViewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(_koinModules)

        _mainViewModel.loadingLiveData.observe(this) {
            main_loading.visible(it)
        }

        val tag = MainActivity::class.java.simpleName
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val builder = OneTimeWorkRequestBuilder<MainWorker>()
            .setConstraints(constraint)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1L, TimeUnit.MINUTES)
            .addTag(tag)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setInitialDelay(Duration.ofSeconds(15L))
        }
        val worker = builder.build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, worker)
    }

    override fun onDestroy() {
        unloadKoinModules(_koinModules)
        super.onDestroy()
    }
}