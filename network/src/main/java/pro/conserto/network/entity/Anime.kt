package pro.conserto.network.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime(
    @Json(name = "mal_id")
    val id: Int,
    @Json(name = "image_url")
    val image: String,
    val title: String,
    val airing: Boolean,
    val score: Float
) : Parcelable