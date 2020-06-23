package pro.conserto.network.module

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import pro.conserto.common.BuildConfig
import pro.conserto.network.service.JikanApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { Moshi.Builder().build() }
    single { provideJikanApi(get()) }
}

private fun provideJikanApi(moshi: Moshi): JikanApi {
    val retrofit = provideRetrofitClient(provideClient(), moshi)
    return retrofit.create(JikanApi::class.java)
}

/**
 * Provide okhttp client builder
 * @return okhttp client builder with timeout
 */
private fun provideClient(): OkHttpClient {
    val httpLogging = HttpLoggingInterceptor()
    httpLogging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(httpLogging).build()
}

/**
 * Provide the retrofit client
 * @param client Okhttp client
 * @param moshi The parser json
 * @return The retrofit client
 */
private fun provideRetrofitClient(client: OkHttpClient, moshi: Moshi) =
    Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v3")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
