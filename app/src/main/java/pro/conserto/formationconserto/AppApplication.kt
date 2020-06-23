package pro.conserto.formationconserto

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import pro.conserto.network.module.networkModule

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(networkModule))
        }
    }

}