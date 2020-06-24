package pro.conserto.network.tools

import android.util.Log
import okhttp3.Request

/**
 * Mock pour l'api
 */
object ResponseApi {

    /**
     * @param request La requete HTTP
     * @return json du resulat de la requête
     */
    fun getJsonFromRequest(request: Request): String {
        val success = "{}"
        return when  {
            request.url.encodedPath.contains("search/anime") ->
                javaClass.classLoader?.getResourceAsStream("searchAnime.json")?.bufferedReader()?.readText() ?: ""
            else -> success
        }
    }
}