package pro.conserto.formationconserto.repository

import pro.conserto.network.entity.AnimeList
import pro.conserto.network.service.JikanApi
import pro.conserto.network.tools.Result
import pro.conserto.network.tools.safeApiCall

class MainRepository(private val _jikanApi: JikanApi) {

    /**
     * Search an anime on my anime list
     * @param search La recherche
     *
     * @return Resultat de la requete api
     *
     */
    suspend fun searchAnime(search: String): Result<AnimeList> = safeApiCall { _jikanApi.searchAnime(search) }

}