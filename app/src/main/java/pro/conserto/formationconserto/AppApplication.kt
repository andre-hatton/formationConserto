package pro.conserto.formationconserto

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import pro.conserto.network.module.networkModule

class AppApplication : Application(), ImageLoaderFactory {

    private val _okHttpClient: OkHttpClient by inject()

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                _okHttpClient
            }
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(networkModule))
        }
    }

}