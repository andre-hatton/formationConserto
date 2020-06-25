package pro.conserto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.conserto.database.dao.AnimeDao
import pro.conserto.database.entity.Anime

@Database(entities = [Anime::class], version = 1, exportSchema = true)
abstract class FormationDatabase : RoomDatabase() {

    abstract fun getAnimeDao(): AnimeDao

}