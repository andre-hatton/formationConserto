package pro.conserto.network.tools

import retrofit2.HttpException

/**
 * Gestion des succès et erreurs de l'api
 * @param apiCall Fonction api
 * @return Le resultat
 */
suspend fun <T: Any> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        return Result.Success(apiCall.invoke())
    } catch (httpException: HttpException) {
        Result.ErrorHttp(httpException.code(), httpException)
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        Result.Error(throwable)
    }
}