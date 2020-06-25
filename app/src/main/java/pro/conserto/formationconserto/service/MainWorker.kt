package pro.conserto.formationconserto.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import pro.conserto.formationconserto.repository.MainRepository
import pro.conserto.network.tools.Result as Response

/**
 * KoinComponent permet l'injection d'objet dans des classes
 */
class MainWorker(context: Context, parameters: WorkerParameters) : CoroutineWorker(context, parameters), KoinComponent {

    private val _mainRepository: MainRepository by inject()

    override suspend fun doWork(): Result = coroutineScope {
        when (_mainRepository.searchAnime("diablo")) {
            is Response.Success -> {
                // faire des opération
                return@coroutineScope Result.success()
            }
            is Response.ErrorHttp -> return@coroutineScope Result.retry()
            else -> return@coroutineScope Result.failure()
        }
    }
}