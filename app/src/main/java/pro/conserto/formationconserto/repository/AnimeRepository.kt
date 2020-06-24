package pro.conserto.formationconserto.repository

import pro.conserto.network.entity.Anime
import pro.conserto.network.service.JikanApi
import pro.conserto.network.tools.safeApiCall

class AnimeRepository(private val _jikanApi: JikanApi) {

    suspend fun getAnimeInfo(anime: Anime) = safeApiCall { _jikanApi.getAnime(anime.id) }
}