package pro.conserto.network.entity

import com.squareup.moshi.Json

data class AnimeList(
    val result: List<Anime> = emptyList(),
    @Json(name = "last_page")
    val lastPage: Int
)