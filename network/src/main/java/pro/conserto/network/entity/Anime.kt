package pro.conserto.network.entity

import com.squareup.moshi.Json

/**
 *       "mal_id": 11741,
"url": "https://myanimelist.net/anime/11741/Fate_Zero_2nd_Season",
"image_url": "https://cdn.myanimelist.net/images/anime/8/41125.jpg?s=78a6e73a2cd5856b28d8c182cd5a1a22",
"title": "Fate/Zero 2nd Season",
"airing": false,
"synopsis": "As the Fourth Holy Grail War rages on with no clear victor in sight, the remaining Servants and their Masters are called upon by Church supervisor Risei Kotomine, in order to band together and confron...",
"type": "TV",
"episodes": 12,
"score": 8.61,
"start_date": "2012-04-08T00:00:00+00:00",
"end_date": "2012-06-24T00:00:00+00:00",
"members": 696219,
"rated": "R"
 */
data class Anime(
    @Json(name = "mal_id")
    val id: Int,
    @Json(name = "image_url")
    val image: String,
    val title: String,
    val airing: Boolean,
    val score: Float
)