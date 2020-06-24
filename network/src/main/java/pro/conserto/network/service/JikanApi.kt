package pro.conserto.network.service

import pro.conserto.network.entity.AnimeList
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanApi {

    @GET("search/anime")
    suspend fun searchAnime(@Query("q") search: String): AnimeList

}