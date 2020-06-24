package pro.conserto.network.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import pro.conserto.common.BuildConfig
import pro.conserto.network.service.JikanApi
import pro.conserto.network.tools.ResponseApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkTestModule = module {
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
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
        .addInterceptor {
            Response.Builder()
                .message("{}")
                .body(getBodyTest(it.request()))
                .code(200)
                .request(it.request())
                .protocol(Protocol.HTTP_1_1)
                .build()
        }
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
        .baseUrl("https://api.jikan.moe/v3/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

/**
 * La réponse mocké à retourner
 * @return Le corps de la réponse
 */
private fun getBodyTest(request: Request) : ResponseBody {
    val json = ResponseApi.getJsonFromRequest(request)
    return json.toResponseBody("application/json".toMediaType())
}
