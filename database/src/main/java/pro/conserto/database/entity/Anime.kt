package pro.conserto.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Table pour les animes
 */
@Entity(tableName = "anime", indices = [
    Index(value = ["name"], name = "anime_name_index", unique = false)
])
@Parcelize
data class Anime(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val description: String
) : Parcelable {
    @IgnoredOnParcel
    @Ignore
    var animeName: String = name
}