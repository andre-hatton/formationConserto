package pro.conserto.network.tools

import retrofit2.HttpException

/**
 * Result from api data
 */
sealed class Result<out R: Any> {

    /**
     * When response api is success
     */
    data class Success<out T: Any>(val value: T) : Result<T>()

    /**
     * When response api has exception
     */
    data class Error(val error: Throwable? = null) : Result<Nothing>()

    /**
     * When response api has http error
     */
    data class ErrorHttp(val code: Int, val error: HttpException? = null) : Result<Nothing>()

}