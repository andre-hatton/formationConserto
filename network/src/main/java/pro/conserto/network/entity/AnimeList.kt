package pro.conserto.network.entity

import com.squareup.moshi.Json


data class AnimeList(
    val results: List<Anime>,
    @Json(name = "last_page")
    val lastPage: Int
)